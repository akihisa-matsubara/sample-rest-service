package jp.co.jaxrs.sample.common.data.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class SequenceGenerateDao {
	private static final String SELECT_SEQ_CUSTOMER_NO = "SELECT NEXT VALUE FOR SEQ_CUSTOMER_NO FROM SYSIBM.DUAL";

	@PersistenceContext(unitName = "mydb")
	private EntityManager entityManager;

	public int getCustomerNo() {
		return (Integer) entityManager.createNativeQuery(SELECT_SEQ_CUSTOMER_NO)
				.getSingleResult();
	}

}
