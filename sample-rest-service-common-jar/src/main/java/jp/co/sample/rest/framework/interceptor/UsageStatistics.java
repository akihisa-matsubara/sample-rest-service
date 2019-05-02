package jp.co.sample.rest.framework.interceptor;

import jp.co.sample.rest.framework.message.MessageId;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;

/**
 * 利用統計ログ.
 */
@Inherited
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface UsageStatistics {

  /**
   * メッセージID.
   *
   * @return メッセージID
   */
  @Nonbinding
  MessageId messageId() default MessageId.UNDEFINED;

  /**
   * パラメーター.
   *
   * @return パラメーター
   */
  @Nonbinding
  String[] params() default {};

}
