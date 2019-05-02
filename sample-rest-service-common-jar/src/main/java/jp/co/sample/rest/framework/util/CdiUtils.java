package jp.co.sample.rest.framework.util;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;
import lombok.experimental.UtilityClass;

/**
 * CDI ユーティリティー.
 */
@UtilityClass
public class CdiUtils {

  /**
   * Beanを取得します.
   *
   * @param <T> beanクラス
   * @param clazz beanクラス
   * @return beanインスタンス
   */
  public static <T> Instance<T> getBean(Class<T> clazz) {
    return CDI.current().select(clazz);
  }

}
