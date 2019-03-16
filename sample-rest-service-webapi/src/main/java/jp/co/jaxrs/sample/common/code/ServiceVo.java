package jp.co.jaxrs.sample.common.code;

import jp.co.jaxrs.framework.code.CodeVo;
import lombok.Getter;

/**
 * サービスVO.
 */
@Getter
public enum ServiceVo implements CodeVo {

  /** 顧客. */
  CUSTOMERS("customers", "顧客"),
  ;

  /** コード. */
  private String code;

  /** デコード. */
  private String decode;

  /**
   * デフォルトコンストラクタ.
   *
   * @param code コード
   * @param decode デコード
   */
  private ServiceVo(String code, String decode) {
    this.code = code;
    this.decode = decode;
  }

}
