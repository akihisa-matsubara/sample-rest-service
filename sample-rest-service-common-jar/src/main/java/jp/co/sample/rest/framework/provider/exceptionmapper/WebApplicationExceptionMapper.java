package jp.co.sample.rest.framework.provider.exceptionmapper;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * JAX-RS の汎用基底例外Mapper.
 */
@Provider
@Slf4j
public class WebApplicationExceptionMapper extends ExceptionMapperBase<WebApplicationExceptionMapper, WebApplicationException>
    implements ExceptionMapper<WebApplicationException> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Response toResponse(WebApplicationException exception) {
    log.error(ExceptionUtils.getStackTrace(exception));
    ERROR_LOGGER.error(ExceptionUtils.getStackTrace(exception));

    return super.toResponse(exception);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Class<WebApplicationExceptionMapper> getClassType() {
    return WebApplicationExceptionMapper.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected List<String> getErrors(WebApplicationException exception) {
    List<String> errors = new ArrayList<>();
    errors.add(ExceptionUtils.getRootCauseMessage(exception));
    return errors;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected int getStatusCode(WebApplicationException exception) {
    return exception.getResponse().getStatus();
  }

}
