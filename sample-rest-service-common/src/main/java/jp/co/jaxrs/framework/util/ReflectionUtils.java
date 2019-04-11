package jp.co.jaxrs.framework.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.reflect.FieldUtils;

/**
 * Reflectionユーティリティー.
 */
@UtilityClass
public class ReflectionUtils {

  /**
   * オブジェクトからアノテーションが定義されたインスタンス変数を取得します.
   *
   * @param <T> PrimaryKeyの型
   * @param annotation アノテーション
   * @param target オブジェクト
   * @return インスタンス変数、アノテーションが見つからない場合はnull
   */
  @SuppressWarnings("unchecked")
  public static <T> T getAnnotatedField(@NonNull Class<? extends Annotation> annotation, @NonNull Object target) {
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
