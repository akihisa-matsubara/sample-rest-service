package jp.co.jaxrs.sample.service;

import java.util.List;

import jp.co.jaxrs.sample.common.data.entity.TCustomerEntity;
import jp.co.jaxrs.sample.provider.requestdto.CustomerDto;

public interface CustomerService {
	List<TCustomerEntity> getCustomers();

	TCustomerEntity getCustomer(String customerNo);

	void createCustomer(CustomerDto form);

	int updateCustomer(CustomerDto form);

	void deleteCustomer(String customerNo);
}
