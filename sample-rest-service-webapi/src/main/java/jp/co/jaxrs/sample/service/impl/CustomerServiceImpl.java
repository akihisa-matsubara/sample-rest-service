package jp.co.jaxrs.sample.service.impl;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;

import jp.co.jaxrs.sample.common.data.dao.SequenceGenerateDao;
import jp.co.jaxrs.sample.common.data.dao.TCustomerDao;
import jp.co.jaxrs.sample.common.data.entity.TCustomerEntity;
import jp.co.jaxrs.sample.provider.requestdto.CustomerDto;
import jp.co.jaxrs.sample.service.CustomerService;

@ApplicationScoped
public class CustomerServiceImpl implements CustomerService {
	@Inject
	private TCustomerDao dao;

	@Inject
	private SequenceGenerateDao sequenceDao;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public List<TCustomerEntity> getCustomers() {
		return dao.findAll();
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public TCustomerEntity getCustomer(String customerNo) {
		return dao.findById(customerNo);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void createCustomer(CustomerDto form) {
		TCustomerEntity customer = new TCustomerEntity();
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

	@Override
	@Transactional(rollbackOn = Exception.class)
	public int updateCustomer(CustomerDto form) {
		TCustomerEntity customer = getCustomer(form.getCustomerNo());
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

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteCustomer(String customerNo) {
		dao.deleteById(customerNo);
	}

	private String generateCustomerNo() {
		int sequenceNo = sequenceDao.getCustomerNo();
		return "C" + StringUtils.leftPad(String.valueOf(sequenceNo), 7, "0");
	}
}
