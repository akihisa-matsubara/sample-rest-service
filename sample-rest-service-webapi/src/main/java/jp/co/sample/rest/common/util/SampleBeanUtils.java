package jp.co.sample.rest.common.util;

import jp.co.sample.framework.core.util.AbstractBeanUtils;

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
