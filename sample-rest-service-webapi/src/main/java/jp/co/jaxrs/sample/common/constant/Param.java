package jp.co.jaxrs.sample.common.constant;

/**
 * パラメーター定数.
 * {@code @QueryParam}、{@code @Path}、{@code @PathParam} で利用するパラメーター定数
 */
public final class Param {

  /** -- 汎用項目 ------------------------------------------------------------------------------. */
  /** 氏名漢字. */
  public static final String NAME_KANJI = "nameKanji";
  /** 氏名カナ. */
  public static final String NAME_KANA = "nameKana";
  /** 性別. */
  public static final String GENDER = "gender";
  /** 生年月日. */
  public static final String BIRTHDAY = "birthday";
  /** 郵便番号. */
  public static final String ADDRESS_ZIP = "addressZip";
  /** 住所. */
  public static final String ADDRESS = "address";

  /** -- 固有項目 ---------------------------------------------------------------------------------. */
  /** /customers. */
  /** 顧客番号. */
  public static final String CUSTOMER_NO = "customerNo";

  /**
   * デフォルトコンストラクター.
   */
  private Param() {
    // do nothing
  }
}
