package jp.co.sample.rest.framework.pres.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response Dto.
 *
 * @param <T> Responseの型
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseDto<T> {

  /** 結果. */
  private String result;

  /** 実行結果. */
  private T response;

  /** エラー情報. */
  private List<String> errors;

}
