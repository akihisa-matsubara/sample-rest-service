package jp.co.jaxrs.framework.constant;

/**
 * パラメーター定数(共通項目).
 * {@code @QueryParam}、{@code @Path}、{@code @PathParam} で利用するパラメーター定数
 */
public final class CommonParam {

  /** Offset. */
  public static final String OFFSET = "offset";
  /** 取得件数. */
  public static final String LIMIT = "limit";
  /** ソート条件. */
  public static final String SORT = "sort";

  /**
   * デフォルトコンストラクター.
   */
  private CommonParam() {
    // do nothing
  }
}
