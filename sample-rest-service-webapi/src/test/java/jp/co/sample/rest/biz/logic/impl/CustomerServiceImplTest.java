package jp.co.sample.rest.biz.logic.impl;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import jp.co.sample.rest.common.dto.CustomerDto;
import jp.co.sample.rest.framework.constant.PersistenceUnit;
import jp.co.sample.rest.framework.data.condition.SearchConditionDo;
import jp.co.sample.rest.framework.data.entitymanager.MyDb;
import jp.co.sample.rest.framework.util.SearchConditionBuilder;
import jp.co.sample.rest.integration.service.impl.ExternalCustomerServiceImpl;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(CdiRunner.class)
public class CustomerServiceImplTest {

  @Inject
  private CustomerServiceImpl testClass;

  @Mock
  @Produces
  private ExternalCustomerServiceImpl externalService;

  @Inject
  @MyDb
  private EntityManager em;

  private EntityManagerFactory emf;

  @Produces
  @MyDb
  @RequestScoped
  EntityManager createUtEm() {
    return emf.createEntityManager();
  }

  @PostConstruct
  void init() {
    emf = Persistence.createEntityManagerFactory(PersistenceUnit.MYDB + "-test");
  }

  @Before
  @InRequestScope
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @After
  @InRequestScope
  public void tearDown() {
  }

  @Test
  @InRequestScope
  public void test_normal_getCustomers() {
    when(externalService.getCustomers()).thenReturn(Collections.emptyList());

    SearchConditionDo searchCondition = new SearchConditionBuilder().build();
    List<CustomerDto> customers = testClass.getCustomers(searchCondition);

    System.out.println("customers:" + customers);
    assertThat(customers).isNotEmpty();
  }

}
