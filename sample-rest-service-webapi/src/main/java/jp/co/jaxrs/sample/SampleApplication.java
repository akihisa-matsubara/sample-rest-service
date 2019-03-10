package jp.co.jaxrs.sample;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * サンプルアプリケーション.
 */
@ApplicationPath("/api/v1/sample")
public class SampleApplication extends Application {

//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public Set<Class<?>> getClasses() {
//    Set<Class<?>> classes = new HashSet<>();
//
//    // Resource
//    classes.add(CustomerResource.class);
//
//    // ExceptionMapper
//    classes.add(ConstraintViolationExceptionMapper.class);
//
//    return classes;
//  }

}
