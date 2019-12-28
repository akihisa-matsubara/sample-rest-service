package jp.co.sample.rest.biz.logic.impl;

import jp.co.sample.framework.core.data.condition.SearchConditionDo;
import jp.co.sample.rest.biz.logic.CustomerService;
import jp.co.sample.rest.common.data.dao.CustomerDao;
import jp.co.sample.rest.common.data.dao.SequenceGenerateDao;
import jp.co.sample.rest.common.data.entity.CustomerEntity;
import jp.co.sample.rest.common.dto.CustomerDto;
import jp.co.sample.rest.common.util.SampleBeanUtils;
import jp.co.sample.rest.integration.service.ExternalCustomerService;
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
    List<CustomerDto> customers = entities.stream().map(entity -> SampleBeanUtils.copyProperties(CustomerDto.class, entity)).collect(Collectors.toList());

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

    return SampleBeanUtils.copyProperties(CustomerDto.class, entity);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createCustomer(CustomerDto dto) {
    CustomerEntity customer = SampleBeanUtils.copyProperties(CustomerEntity.class, dto);

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

    SampleBeanUtils.copyProperties(customer, dto);
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
