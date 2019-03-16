package jp.co.jaxrs.framework.pres.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Response Dto.
 */
@Builder
@Getter
@Setter
public class ResponseDto {

  /** 結果. */
  private String result;

  /** 実行結果. */
  private Object response;

  /** エラー情報. */
  private List<String> errors;

}
