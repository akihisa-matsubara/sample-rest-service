package jp.co.jaxrs.framework.filter;

import jp.co.jaxrs.framework.code.LoggerVo;
import java.io.IOException;
import java.util.List;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * ログ出力フィルター.
 */
@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {
  /** MDC Key - start time. */
  private static final String START_TIME = "start-time";

  /** Debug Logger. */
  private static final Logger DEBUG_LOGGER = LoggerFactory.getLogger(LoggingFilter.class);
  /** Performance Logger. */
  private static final Logger PERFORMANCE_LOGGER = LoggerFactory.getLogger(LoggerVo.PERFORMANCE_LOGGER.getCode());
  /** Access Logger. */
  private static final Logger ACCESS_LOGGER = LoggerFactory.getLogger(LoggerVo.ACCESS_LOGGER.getCode());

  /** Resource Info. */
  @Context
  private ResourceInfo resourceInfo;

  /**
   * リクエスト受付時にリクエストに関する情報をログ出力します.
   *
   * @param requestContext ContainerRequestContext
   */
  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    MDC.put(START_TIME, String.valueOf(System.currentTimeMillis()));

    ACCESS_LOGGER.debug("Resource : /{}, Method Type : {}, Method Name : {}", requestContext.getUriInfo().getPath(),
        requestContext.getMethod(), resourceInfo.getResourceMethod().getName());

    DEBUG_LOGGER.debug("Entering in Resource : /{} ", requestContext.getUriInfo().getPath());
    DEBUG_LOGGER.debug("Method Name : {} ", resourceInfo.getResourceMethod().getName());
    DEBUG_LOGGER.debug("Class : {} ", resourceInfo.getResourceClass().getCanonicalName());
    logQueryParameters(requestContext);
    logRequestHeader(requestContext);
  }

  /**
   * リクエスト応答時に性能に関する情報をログ出力します.
   *
   * @param requestContext ContainerRequestContext
   * @param responseContext ContainerResponseContext
   */
  @Override
  public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
    logPerformance(requestContext);

    // clear the context on exit
    MDC.clear();
  }

  /**
   * クエリパラメーターをログ出力します.
   *
   * @param requestContext ContainerRequestContext
   */
  private void logQueryParameters(ContainerRequestContext requestContext) {
    requestContext.getUriInfo().getPathParameters().keySet().stream().forEach(name -> {
      List<String> obj = requestContext.getUriInfo().getPathParameters().get(name);
      String value = null;
      if (obj != null && !obj.isEmpty()) {
        value = obj.get(0);
      }
      DEBUG_LOGGER.debug("Query Parameter Name: {}, Value :{}", name, value);
    });
  }

  /**
   * リクエストヘッダーをログ出力します.
   *
   * @param requestContext ContainerRequestContext
   */
  private void logRequestHeader(ContainerRequestContext requestContext) {
    DEBUG_LOGGER.debug("----Start Header Section of request ----");
    DEBUG_LOGGER.debug("Method Type : {}", requestContext.getMethod());
    requestContext.getHeaders().keySet().stream()
        .forEach(headerName -> DEBUG_LOGGER.debug("Header Name: {}, Header Value :{} ", headerName, requestContext.getHeaderString(headerName)));
    DEBUG_LOGGER.debug("----End Header Section of request ----");
  }

  /**
   * 性能情報をログ出力します.
   *
   * @param requestContext ContainerRequestContext
   */
  private void logPerformance(ContainerRequestContext requestContext) {
    String startTime = MDC.get(START_TIME);
    if (startTime == null || startTime.isEmpty()) {
      return;
    }
    long executionTime = System.currentTimeMillis() - Long.parseLong(startTime);

    PERFORMANCE_LOGGER.debug("Total request execution time : {} milliseconds, Resource: /{}, Method Type : {}, Method Name : {}", executionTime,
        requestContext.getUriInfo().getPath(), requestContext.getMethod(), resourceInfo.getResourceMethod().getName());
  }
}
