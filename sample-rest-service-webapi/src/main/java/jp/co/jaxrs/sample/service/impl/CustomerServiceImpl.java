package jp.co.jaxrs.sample.service.impl;

import jp.co.jaxrs.sample.data.dao.CustomerDao;
import jp.co.jaxrs.sample.data.dao.SequenceGenerateDao;
import jp.co.jaxrs.sample.data.entity.CustomerEntity;
import jp.co.jaxrs.sample.provider.requestdto.CustomerDto;
import jp.co.jaxrs.sample.service.CustomerService;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;

/**
 * 顧客サービス実装.
 */
@ApplicationScoped
public class CustomerServiceImpl implements CustomerService {

  /** 顧客Dao. */
  @Inject
  private CustomerDao dao;

  /** シーケンス生成Dao. */
  @Inject
  private SequenceGenerateDao sequenceDao;

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional(rollbackOn = Exception.class)
  public List<CustomerEntity> getCustomers() {
    return dao.findAll();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional(rollbackOn = Exception.class)
  public CustomerEntity getCustomer(String customerNo) {
    return dao.findById(customerNo);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional(rollbackOn = Exception.class)
  public void createCustomer(CustomerDto form) {
    CustomerEntity customer = new CustomerEntity();
    customer.setCustomerNo(generateCustomerNo());
    customer.setNameKanji(form.getNameKanji());
    customer.setNameKana(form.getNameKana());
    customer.setGender(form.getGender());
    customer.setBirthday(form.getBirthday());
    customer.setAddressZip(form.getAddressZip());
    customer.setAddress(form.getAddress());

    // common culomn
    customer.setCreationUserId("customers");
    customer.setCreationDate(LocalDateTime.now(Clock.systemDefaultZone()));
    customer.setUpdatedUserId("customers");
    customer.setUpdatedDate(LocalDateTime.now(Clock.systemDefaultZone()));

    dao.create(customer);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional(rollbackOn = Exception.class)
  public int updateCustomer(CustomerDto form) {
    CustomerEntity customer = getCustomer(form.getCustomerNo());
    if (customer == null) {
      return 0;
    }

    customer.setNameKanji(form.getNameKanji());
    customer.setNameKana(form.getNameKana());
    customer.setGender(form.getGender());
    customer.setBirthday(form.getBirthday());
    customer.setAddressZip(form.getAddressZip());
    customer.setAddress(form.getAddress());

    // common culomn
    customer.setUpdatedUserId("customers");
    customer.setUpdatedDate(LocalDateTime.now(Clock.systemDefaultZone()));
    dao.update(customer);

    return 1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional(rollbackOn = Exception.class)
  public void deleteCustomer(String customerNo) {
    dao.deleteById(customerNo);
  }

  /**
   * 顧客番号生成.
   *
   * @return 顧客番号
   */
  private String generateCustomerNo() {
    int sequenceNo = sequenceDao.generateCustomerNo();
    return "C" + StringUtils.leftPad(String.valueOf(sequenceNo), 7, "0");
  }
}
