package jp.co.sample.rest.framework.util;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import lombok.experimental.UtilityClass;

/**
 * バリデーション ユーティリティー.
 */
@UtilityClass
public class ValidationUtils {

  /**
   * BeanValidationを実行します.
   *
   * @param <T> beanクラス
   * @param bean bean
   * @param groups Validation Groups
   * @return 制約違反のSet、検証OKの場合は空のSet
   */
  public static <T> Set<ConstraintViolation<T>> valid(T bean, Class<?>... groups) {
    Validator validator = (Validator) CdiUtils.getBean(Validator.class);
    return validator.validate(bean, groups);
  }

}
