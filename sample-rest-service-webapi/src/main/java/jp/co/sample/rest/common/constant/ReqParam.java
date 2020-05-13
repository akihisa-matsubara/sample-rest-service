package jp.co.sample.rest.common.constant;

/**
 * リクエストパラメーター定数.
 * {@code @QueryParam}、{@code @Path}、{@code @PathParam} で利用するパラメーター定数
 */
public final class ReqParam {

  /** -- 汎用項目 ------------------------------------------------------------------------------. */
  /** 氏名漢字. */
  public static final String NAME_KANJI = "nameKanji";
  /** 氏名カナ. */
  public static final String NAME_KANA = "nameKana";
  /** 性別. */
  public static final String GENDER = "gender";
  /** 生年月日. */
  public static final String BIRTHDAY = "birthday";
  /** 電話番号. */
  public static final String TEL_NO = "telNo";
  /** 郵便番号. */
  public static final String ADDRESS_ZIP = "addressZip";
  /** 住所. */
  public static final String ADDRESS = "address";
  /** Eメール. */
  public static final String EMAIL = "email";

  /** -- 固有項目 ---------------------------------------------------------------------------------. */
  /** /customers. */
  /** 顧客番号. */
  public static final String CUSTOMER_NO = "customerNo";

  /**
   * デフォルトコンストラクター.
   */
  private ReqParam() {
    // do nothing
  }
}
