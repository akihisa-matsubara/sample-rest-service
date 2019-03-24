package jp.co.jaxrs.framework.exception.mapper;

import jp.co.jaxrs.framework.code.LoggerVo;
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
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 制約違反例外Mapper.
 */
@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

  /** Debug Logger. */
  private static final Logger DEBUG_LOGGER = LoggerFactory.getLogger(ConstraintViolationExceptionMapper.class);
  /** Error Logger. */
  private static final Logger ERROR_LOGGER = LoggerFactory.getLogger(LoggerVo.ERROR_LOGGER.getCode());

  /** メッセージフォーマット. */
  private static final String MESSAGE_FORMAT = "{0}[{1}]: {2}";

  /**
   * 例外をResponseに設定します.
   *
   * @return {@link Response} 例外を設定したResponse
   */
  @Override
  public Response toResponse(ConstraintViolationException exception) {
    DEBUG_LOGGER.error(ExceptionUtils.getStackTrace(exception));
    ERROR_LOGGER.error(ExceptionUtils.getStackTrace(exception));

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
