package jp.co.jaxrs.framework.exception.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * 想定外例外Mapper.
 */
@Provider
public class IllegalExceptionMapper extends BaseExceptionMapper<IllegalExceptionMapper, Exception> implements ExceptionMapper<Exception> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<IllegalExceptionMapper> getClassType() {
    return IllegalExceptionMapper.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> getErrors(Exception exception) {
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
