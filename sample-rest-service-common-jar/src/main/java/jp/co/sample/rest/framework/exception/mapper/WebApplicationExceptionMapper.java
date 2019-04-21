package jp.co.sample.rest.framework.exception.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * JAX-RS の汎用基底例外Mapper.
 */
@Provider
public class WebApplicationExceptionMapper extends BaseExceptionMapper<WebApplicationExceptionMapper, WebApplicationException>
    implements ExceptionMapper<WebApplicationException> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<WebApplicationExceptionMapper> getClassType() {
    return WebApplicationExceptionMapper.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> getErrors(WebApplicationException exception) {
    List<String> errors = new ArrayList<>();
    errors.add(exception.getMessage());
    return errors;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Status getStatus() {
    return Status.INTERNAL_SERVER_ERROR;
  }

}
