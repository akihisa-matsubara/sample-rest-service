package jp.co.jaxrs.sample.provider.resource;

import jp.co.jaxrs.sample.common.data.entity.TCustomerEntity;
import jp.co.jaxrs.sample.provider.requestdto.CustomerDto;
import jp.co.jaxrs.sample.service.CustomerService;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/customers")
public class CustomerResource {
	@Inject
	private CustomerService customerService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<TCustomerEntity> getCustomers() {
		return customerService.getCustomers();
	}

	@Path("{customerNo}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public TCustomerEntity getCustomer(@PathParam("customerNo") String customerNo) {
	  return customerService.getCustomer(customerNo);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void createCustomer(List<CustomerDto> formList) {
		formList.forEach(form -> customerService.createCustomer(form));
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public int updateCustomer(List<CustomerDto> formList) {
		int updateCount = 0;
		for (CustomerDto form : formList) {
			updateCount = updateCount + customerService.updateCustomer(form);
		}
		return updateCount;
	}

	@Path("{customerNo}")
	@DELETE
	public void deleteCustomer(@PathParam("customerNo") String customerNo) {
		customerService.deleteCustomer(customerNo);
	}
}
