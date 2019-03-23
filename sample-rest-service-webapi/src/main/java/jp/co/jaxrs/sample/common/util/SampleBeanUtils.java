package jp.co.jaxrs.sample.common.util;

import jp.co.jaxrs.framework.util.AbstractBeanUtils;

/**
 * Beanユーティリティー.
 */
public class SampleBeanUtils extends AbstractBeanUtils {

  static {
    register();
  }

  /**
   * デフォルトコンストラクタ.
   */
  private SampleBeanUtils() {
    super();
  }

  /**
   * コンバーターを登録します.
   */
  protected static void register() {
    // アプリ固有
  }
}
