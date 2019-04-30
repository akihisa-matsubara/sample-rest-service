package jp.co.sample.rest.framework.provider.exceptionmapper;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.apache.bval.jsr.util.PathImpl;

/**
 * 制約違反例外Mapper.
 */
@Provider
public class ConstraintViolationExceptionMapper extends BaseExceptionMapper<ConstraintViolationExceptionMapper, ConstraintViolationException>
    implements ExceptionMapper<ConstraintViolationException> {

  /** メッセージフォーマット. */
  private static final String MESSAGE_FORMAT = "{0}[{1}]: {2}";

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<ConstraintViolationExceptionMapper> getClassType() {
    return ConstraintViolationExceptionMapper.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> getErrors(ConstraintViolationException exception) {
    return exception.getConstraintViolations().stream()
        .map(cv -> MessageFormat.format(MESSAGE_FORMAT, ((PathImpl) cv.getPropertyPath()).getLeafNode().getName(), cv.getInvalidValue(), cv.getMessage()))
        .collect(Collectors.toList());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Status getStatus() {
    return Status.BAD_REQUEST;
  }

}
