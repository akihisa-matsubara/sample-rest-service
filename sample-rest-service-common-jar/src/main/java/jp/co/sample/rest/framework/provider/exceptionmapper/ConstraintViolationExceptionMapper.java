package jp.co.sample.rest.framework.provider.exceptionmapper;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * 制約違反例外Mapper.
 */
@Provider
@Slf4j
public class ConstraintViolationExceptionMapper extends ExceptionMapperBase<ConstraintViolationExceptionMapper, ConstraintViolationException>
    implements ExceptionMapper<ConstraintViolationException> {

  /** メッセージフォーマット. */
  private static final String MESSAGE_FORMAT = "[{0}]: {1}";

  /**
   * {@inheritDoc}
   */
  @Override
  public Response toResponse(ConstraintViolationException exception) {
    log.error(ExceptionUtils.getStackTrace(exception));
    ERROR_LOGGER.error(ExceptionUtils.getStackTrace(exception));

    return super.toResponse(exception);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Class<ConstraintViolationExceptionMapper> getClassType() {
    return ConstraintViolationExceptionMapper.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected List<String> getErrors(ConstraintViolationException exception) {
    return exception.getConstraintViolations().stream()
        .map(cv -> MessageFormat.format(MESSAGE_FORMAT, cv.getInvalidValue(), cv.getMessage()))
        .collect(Collectors.toList());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected int getStatusCode(ConstraintViolationException exception) {
    return Status.BAD_REQUEST.getStatusCode();
  }

}
