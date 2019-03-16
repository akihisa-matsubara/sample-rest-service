package jp.co.jaxrs.framework.exception.mapper;

import jp.co.jaxrs.framework.code.ResultVo;
import jp.co.jaxrs.framework.pres.dto.ResponseDto;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.apache.bval.jsr.util.PathImpl;

/**
 * 制約違反例外Mapper.
 */
@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

  /** メッセージフォーマット. */
  private static final String MESSAGE_FORMAT = "{0}[{1}]: {2}";

  /**
   * 例外をResponseに設定します.
   *
   * @return 例外を設定したResponse
   */
  @Override
  public Response toResponse(ConstraintViolationException exception) {
    Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
    ResponseDto responseDto = ResponseDto.builder()
        .result(ResultVo.FAILURE.getDecode())
        .errors(new ArrayList<>())
        .build();

    for (ConstraintViolation<?> cv : constraintViolations) {
      PathImpl propertyPath = (PathImpl) cv.getPropertyPath();
      responseDto.getErrors().add(MessageFormat.format(MESSAGE_FORMAT, propertyPath.getLeafNode().getName(), cv.getInvalidValue(), cv.getMessage()));
    }

    return Response.status(Status.BAD_REQUEST).entity(responseDto).build();
  }

}
