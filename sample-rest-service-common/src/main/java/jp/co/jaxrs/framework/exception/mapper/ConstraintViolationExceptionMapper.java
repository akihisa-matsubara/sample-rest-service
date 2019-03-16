package jp.co.jaxrs.framework.exception.mapper;

import jp.co.jaxrs.framework.code.ResultVo;
import jp.co.jaxrs.framework.pres.dto.ResponseDto;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
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
    List<String> errors = exception.getConstraintViolations().stream()
        .map(cv -> {
          PathImpl propertyPath = (PathImpl) cv.getPropertyPath();
          return MessageFormat.format(MESSAGE_FORMAT, propertyPath.getLeafNode().getName(), cv.getInvalidValue(), cv.getMessage());
        }).collect(Collectors.toList());

    ResponseDto responseDto = ResponseDto.builder()
        .result(ResultVo.FAILURE.getDecode())
        .errors(errors)
        .build();

    return Response.status(Status.BAD_REQUEST).entity(responseDto).build();
  }

}
