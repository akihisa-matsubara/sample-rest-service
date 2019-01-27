package jp.co.jaxrs.sample;

import jp.co.jaxrs.sample.provider.resource.CustomerResource;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * サンプルアプリケーション.
 */
@ApplicationPath("/api/v1")
public class EntryPoint extends Application {
  // XXX getClasses or getSingletons return not Null

  // 都度インスタンスをNewしたい場合
  // public Set<Class<?>> getClasses() {
  // Set<Class<?>> classes = new HashSet<Class<?>>();
  // classes.add(SampleController.class);
  // return classes;
  // }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<Object> getSingletons() {
    Set<Object> resources = new HashSet<>();
    resources.add(new CustomerResource());
    return resources;
  }
}
