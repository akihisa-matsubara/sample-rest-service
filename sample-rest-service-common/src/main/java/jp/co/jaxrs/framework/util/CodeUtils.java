package jp.co.jaxrs.framework.util;

import jp.co.jaxrs.framework.code.CodeVo;
import java.lang.reflect.InvocationTargetException;

/**
 * コード・ユーティリティ.
 */
public class CodeUtils {

  /**
   * デフォルトコンストラクタ.
   */
  private CodeUtils() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * コード値をコードVOにデコードします.
   *
   * @param <CD> デコード先となるコードの型
   * @param codeValue コード値
   * @param codeType デコード先となるコードの型
   * @return {@link CodeVo} コードVO. 存在しない場合はnull
   */
  @SuppressWarnings("unchecked")
  public static <CD extends CodeVo> CD decode(String codeValue, Class<CD> codeType) {
    CD[] values;
    try {
      values = (CD[]) codeType.getMethod("values").invoke(null);
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
      throw new IllegalArgumentException(e);
    }

    for (CD code : values) {
      if (code.getCode().equals(codeValue)) {
        return code;
      }
    }

    return null;
  }

}