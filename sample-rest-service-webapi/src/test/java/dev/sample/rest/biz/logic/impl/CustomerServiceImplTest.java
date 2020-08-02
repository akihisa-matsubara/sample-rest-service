package dev.sample.rest.biz.logic.impl;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import dev.sample.framework.core.constant.PersistenceUnit;
import dev.sample.framework.core.data.condition.SearchConditionDo;
import dev.sample.framework.core.data.entitymanager.MyDb;
import dev.sample.framework.rest.util.SearchConditionBuilder;
import dev.sample.rest.common.dto.CustomerDto;
import dev.sample.rest.integration.service.impl.ExternalCustomerServiceImpl;
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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * CustomerService 実装 テストクラス.
 */
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

  @Test
  @InRequestScope
  public void test_normal_getCustomers() {
    // --- setup -----
    when(externalService.getCustomers()).thenReturn(Collections.emptyList());
    SearchConditionDo searchCondition = new SearchConditionBuilder().build();

    // --- execute ---
    List<CustomerDto> customers = testClass.getCustomers(searchCondition);

    // --- verify ----
    assertThat(customers).as("取得結果が1件以上であること").isNotEmpty();
  }

}
