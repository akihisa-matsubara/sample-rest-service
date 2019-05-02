package jp.co.sample.rest.framework.provider.exceptionmapper;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * 想定外例外Mapper.
 */
@Provider
@Slf4j
public class IllegalExceptionMapper extends ExceptionMapperBase<IllegalExceptionMapper, Exception> implements ExceptionMapper<Exception> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Response toResponse(Exception exception) {
    log.error(ExceptionUtils.getStackTrace(exception));
    ERROR_LOGGER.error(ExceptionUtils.getStackTrace(exception));

    return super.toResponse(exception);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Class<IllegalExceptionMapper> getClassType() {
    return IllegalExceptionMapper.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected List<String> getErrors(Exception exception) {
    List<String> errors = new ArrayList<>();
    errors.add(exception.getMessage());
    return errors;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected int getStatusCode(Exception exception) {
    return Status.INTERNAL_SERVER_ERROR.getStatusCode();
  }

}
