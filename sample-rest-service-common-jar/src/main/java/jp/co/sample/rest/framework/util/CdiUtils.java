package jp.co.sample.rest.framework.util;

import jp.co.sample.rest.framework.exception.SystemException;
import jp.co.sample.rest.framework.exception.dto.ErrorMessage;
import jp.co.sample.rest.framework.message.MessageId;
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
  public static <T> T getBean(Class<T> clazz) {
    Instance<T> instance = CDI.current().select(clazz);
    if (instance.isUnsatisfied()) {
      throw new SystemException(new ErrorMessage(MessageId.F0008E, clazz.getName()));

    } else if (instance.isAmbiguous()) {
      throw new SystemException(new ErrorMessage(MessageId.F0009E, clazz.getName()));

    }

    return instance.get();
  }

}
