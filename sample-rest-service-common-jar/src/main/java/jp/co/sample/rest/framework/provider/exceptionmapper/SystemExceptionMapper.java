package jp.co.sample.rest.framework.provider.exceptionmapper;

import jp.co.sample.rest.framework.exception.SystemException;
import jp.co.sample.rest.framework.util.MessageUtils;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * システム基底基底例外Mapper.
 */
@Provider
public class SystemExceptionMapper extends BaseExceptionMapper<SystemExceptionMapper, SystemException>
    implements ExceptionMapper<SystemException> {

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
      errors.add(exception.getCause().getMessage());
    }
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
