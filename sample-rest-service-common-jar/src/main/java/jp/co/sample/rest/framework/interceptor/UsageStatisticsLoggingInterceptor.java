package jp.co.sample.rest.framework.interceptor;

import jp.co.sample.rest.framework.code.LoggerVo;
import jp.co.sample.rest.framework.util.MessageUtils;
import java.io.Serializable;
import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 利用統計 ロギングインターセプター.
 */
@Priority(Interceptor.Priority.APPLICATION)
@Interceptor
@UsageStatistics
public class UsageStatisticsLoggingInterceptor implements Serializable {

  /** serialVersionUID. */
  private static final long serialVersionUID = -7985776564208590378L;

  /** 利用統計(Access) Logger. */
  private static final Logger ACCESS_LOGGER = LoggerFactory.getLogger(LoggerVo.ACCESS_LOGGER.getCode());

  /**
   * 利用統計ログを出力します.
   *
   * @param context InvocationContext
   * @return Object
   * @throws Exception 例外
   */
  @AroundInvoke
  public Object logUsageStatistics(InvocationContext context) throws Exception {
    Object obj = context.proceed();
    UsageStatistics annotation = context.getMethod().getAnnotation(UsageStatistics.class);
    ACCESS_LOGGER.debug(MessageUtils.getMessage(annotation.messageId(), annotation.params()));
    return obj;
  }

}
