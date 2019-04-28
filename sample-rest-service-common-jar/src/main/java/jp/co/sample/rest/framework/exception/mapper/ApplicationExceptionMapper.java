package jp.co.sample.rest.framework.exception.mapper;

import jp.co.sample.rest.framework.exception.ApplicationException;
import jp.co.sample.rest.framework.util.MessageUtils;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * アプリケーション基底例外Mapper.
 */
@Provider
public class ApplicationExceptionMapper extends BaseExceptionMapper<ApplicationExceptionMapper, ApplicationException>
    implements ExceptionMapper<ApplicationException> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<ApplicationExceptionMapper> getClassType() {
    return ApplicationExceptionMapper.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> getErrors(ApplicationException exception) {
    return exception.getErrorList().stream().map(
        error -> MessageUtils.getMessage(error.getMessageId(), error.getParams())).collect(Collectors.toList());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Status getStatus() {
    return Status.INTERNAL_SERVER_ERROR;
  }

}
