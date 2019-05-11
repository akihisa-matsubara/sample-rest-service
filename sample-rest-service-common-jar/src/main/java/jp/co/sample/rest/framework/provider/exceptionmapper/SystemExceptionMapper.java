package jp.co.sample.rest.framework.provider.exceptionmapper;

import jp.co.sample.rest.framework.exception.SystemException;
import jp.co.sample.rest.framework.util.MessageUtils;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * システム基底基底例外Mapper.
 */
@Provider
@Slf4j
public class SystemExceptionMapper extends ExceptionMapperBase<SystemExceptionMapper, SystemException>
    implements ExceptionMapper<SystemException> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Response toResponse(SystemException exception) {
    log.error(ExceptionUtils.getStackTrace(exception));
    ERROR_LOGGER.error(ExceptionUtils.getStackTrace(exception));

    return super.toResponse(exception);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<SystemExceptionMapper> getClassType() {
    return SystemExceptionMapper.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> getErrors(SystemException exception) {
    List<String> errors = new ArrayList<>();
    if (exception.getErrorDto() != null) {
      errors.add(MessageUtils.getErrorMessage(exception.getErrorDto()));
    } else {
      errors.add(ExceptionUtils.getRootCauseMessage(exception));
    }
    return errors;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getStatusCode(SystemException exception) {
    return Status.INTERNAL_SERVER_ERROR.getStatusCode();
  }

}
