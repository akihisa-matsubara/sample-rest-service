package jp.co.jaxrs.framework.data.condition;

/**
 * 検索条件の比較演算子を表すVO.
 */
public enum OperatorVo {
  /** equal. */
  EQ("="),
  /** not equal. */
  NE("<>"),
  /** greater than. */
  GT(">"),
  /** greater than equal. */
  GE(">="),
  /** less than. */
  LT("<"),
  /** less than equal. */
  LE("<="),
  /** start with. */
  SW("LIKE"),
  /** in. */
  IN("IN"),
  ;

  /** 比較演算子. */
  private String str;

  /**
   * デフォルトコンストラクタ.
   *
   * @param str 比較演算子
   */
  private OperatorVo(String str) {
    this.str = str;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return str;
  }

}
