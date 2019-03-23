package jp.co.jaxrs.framework.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import org.apache.commons.lang3.reflect.FieldUtils;

/**
 * Reflectionユーティリティー.
 */
public class ReflectionUtils {

  /**
   * デフォルトコンストラクタ.
   */
  private ReflectionUtils() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * オブジェクトからアノテーションが定義されたインスタンス変数を取得します.
   *
   * @param <T> PrimaryKeyの型
   * @param annotation アノテーション
   * @param target オブジェクト
   * @return インスタンス変数、アノテーションが見つからない場合はnull
   */
  @SuppressWarnings("unchecked")
  public static <T> T getAnnotatedField(Class<? extends Annotation> annotation, Object target) {
    if (annotation == null || target == null) {
      return null;
    }

    List<Field> fieldsListWithAnnotation = FieldUtils.getFieldsListWithAnnotation(target.getClass(), annotation);
    if (!fieldsListWithAnnotation.isEmpty()) {
      Field field = fieldsListWithAnnotation.get(0);
      field.setAccessible(true);

      try {
        return (T)field.get(target);

      } catch (IllegalAccessException iae) {
        throw new IllegalArgumentException(iae);

      }
    }

    return null;
  }
}
