package jp.co.sample.rest.framework.interceptor;

import jp.co.sample.rest.framework.code.LoggerVo;
import jp.co.sample.rest.framework.message.MessageId;
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

  /** 利用統計 Logger. */
  private static final Logger STATISTICS_LOGGER = LoggerFactory.getLogger(LoggerVo.STATISTICS_LOGGER.getCode());

  /**
   * 利用統計ログを出力します.
   *
   * @param context InvocationContext
   * @return Object
   * @throws Exception 例外
   */
  @AroundInvoke
  public Object logUsageStatistics(InvocationContext context) throws Exception {
    UsageStatistics annotation = context.getMethod().getAnnotation(UsageStatistics.class);
    if (OutputTiming.BEGIN == annotation.outputTiming() || OutputTiming.BOTH == annotation.outputTiming()) {
      STATISTICS_LOGGER.info(MessageUtils.getMessage(MessageId.U0001I, annotation.processName()));
    }

    try {
      Object obj = context.proceed();

      if (OutputTiming.COMPLETE == annotation.outputTiming() || OutputTiming.BOTH == annotation.outputTiming()) {
        STATISTICS_LOGGER.info(MessageUtils.getMessage(MessageId.U0002I, annotation.processName()));
      }
      return obj;

    } catch (Exception e) {
      if (OutputTiming.COMPLETE == annotation.outputTiming() || OutputTiming.BOTH == annotation.outputTiming()) {
        STATISTICS_LOGGER.error(MessageUtils.getMessage(MessageId.U0003E, annotation.processName()));
      }
      throw e;

    }
  }

}
