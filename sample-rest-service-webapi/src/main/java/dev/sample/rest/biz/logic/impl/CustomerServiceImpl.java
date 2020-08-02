package dev.sample.rest.biz.logic.impl;

import dev.sample.framework.core.data.condition.SearchConditionDo;
import dev.sample.framework.core.util.BeanUtilsExt;
import dev.sample.rest.biz.logic.CustomerService;
import dev.sample.rest.common.dto.CustomerDto;
import dev.sample.rest.data.dao.CustomerDao;
import dev.sample.rest.data.dao.SequenceGenerateDao;
import dev.sample.rest.data.entity.CustomerEntity;
import dev.sample.rest.integration.service.ExternalCustomerService;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;

/**
 * 顧客サービス実装.
 */
@ApplicationScoped
@Transactional(rollbackOn = Exception.class)
public class CustomerServiceImpl implements CustomerService {

  /** 顧客Dao. */
  @Inject
  private CustomerDao dao;

  /** シーケンス生成Dao. */
  @Inject
  private SequenceGenerateDao sequenceDao;

  @Inject
  private ExternalCustomerService externalService;

  /**
   * {@inheritDoc}
   */
  @Override
  public List<CustomerDto> getCustomers(SearchConditionDo searchCondition) {
    List<CustomerEntity> entities = dao.search(searchCondition);
    List<CustomerDto> customers = entities.stream()
        .map(entity -> BeanUtilsExt.copyProperties(CustomerDto.class, entity))
        .collect(Collectors.toList());

    // 外部サービス呼び出し
    List<CustomerDto> externalCustomers = externalService.getCustomers();
    customers.addAll(externalCustomers);

    return customers;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CustomerDto getCustomer(String customerNo) {
    CustomerEntity entity = dao.find(customerNo);

    if (entity == null) {
      return null;
    }

    return BeanUtilsExt.copyProperties(CustomerDto.class, entity);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createCustomer(CustomerDto dto) {
    CustomerEntity customer = BeanUtilsExt.copyProperties(CustomerEntity.class, dto);

    // generate pk
    customer.setCustomerNo(generateCustomerNo());

    dao.create(customer);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int updateCustomer(CustomerDto dto) {
    CustomerEntity customer = dao.find(dto.getCustomerNo());
    if (customer == null) {
      return 0;
    }

    BeanUtilsExt.copyProperties(customer, dto);
    dao.update(customer);

    return 1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int deleteCustomer(String customerNo) {
    CustomerEntity customer = dao.find(customerNo);
    if (customer == null) {
      return 0;
    }

    dao.deleteById(customerNo);

    return 1;
  }

  /**
   * 顧客番号生成("C" + 左0埋め7桁の連番).
   *
   * @return 顧客番号
   */
  private String generateCustomerNo() {
    int sequenceNo = sequenceDao.generateCustomerNo();
    return "C" + StringUtils.leftPad(String.valueOf(sequenceNo), 7, "0");
  }

}
