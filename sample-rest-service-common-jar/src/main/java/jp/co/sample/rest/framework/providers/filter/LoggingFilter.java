package jp.co.sample.rest.framework.providers.filter;

import jp.co.sample.rest.framework.code.LoggerVo;
import jp.co.sample.rest.framework.exception.SystemException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Scanner;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * ログ出力フィルター.
 */
@Provider
@Slf4j
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter, WriterInterceptor {
  /** MDC Key - start time. */
  private static final String START_TIME = "start-time";

  /** Logging Header Template. */
  private static final String DUMP_TEMPLATE = "%-20s%s\n";

  /** Performance Logger. */
  private static final Logger PERFORMANCE_LOGGER = LoggerFactory.getLogger(LoggerVo.PERFORMANCE_LOGGER.getCode());
  /** Access Logger. */
  private static final Logger ACCESS_LOGGER = LoggerFactory.getLogger(LoggerVo.ACCESS_LOGGER.getCode());

  /** Resource Info. */
  @Context
  private ResourceInfo resourceInfo;

  /**
   * リクエスト受付時にアクセス履歴とリクエストに関する情報をログ出力します.
   *
   * @param requestContext ContainerRequestContext
   */
  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    MDC.put(START_TIME, String.valueOf(System.currentTimeMillis()));

    ACCESS_LOGGER.debug("Resource : /{}, Method Type : {}, Method Name : {}", requestContext.getUriInfo().getPath(),
        requestContext.getMethod(), resourceInfo.getResourceMethod().getName());
    logRequest(requestContext);
  }

  /**
   * リクエスト応答時に性能とレスポンスに関する情報をログ出力します.
   *
   * @param requestContext ContainerRequestContext
   * @param responseContext ContainerResponseContext
   */
  @Override
  public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
    logPerformance(requestContext);
    logResponse(responseContext);
    // clear the context on exit
    MDC.clear();
  }

  /**
   * メッセージボディーをログ出力します.
   *
   * @param writerInterceptorContext WriterInterceptorContext
   */
  @Override
  public void aroundWriteTo(WriterInterceptorContext writerInterceptorContext) throws IOException {
    logResponseBody(writerInterceptorContext);

  }

  /**
   * リクエスト情報をログ出力します.
   *
   * @param requestContext ContainerRequestContext
   */
  private void logRequest(ContainerRequestContext requestContext) {
    StringBuilder builder = new StringBuilder();
    builder.append("\n----- HTTP REQUEST ---------------------\n");
    builder.append(String.format(DUMP_TEMPLATE, "Request URL", requestContext.getUriInfo().getAbsolutePath()));
    builder.append(String.format(DUMP_TEMPLATE, "Request Method", requestContext.getMethod()));
    builder.append(String.format(DUMP_TEMPLATE, "Resource Method", resourceInfo.getResourceMethod().getName()));
    builder.append(String.format(DUMP_TEMPLATE, "Class", resourceInfo.getResourceClass().getCanonicalName()));

    builder.append("----- QUERY PARAMETER ------------------\n");
    for (String name : requestContext.getUriInfo().getPathParameters().keySet()) {
      List<String> obj = requestContext.getUriInfo().getPathParameters().get(name);
      String value = null;
      if (obj != null && !obj.isEmpty()) {
        value = obj.get(0);
      }
      builder.append(String.format(DUMP_TEMPLATE, name, value));
    }

    builder.append("----- HTTP REQUEST HEADER --------------\n");
    for (String headerName : requestContext.getHeaders().keySet()) {
      builder.append(String.format(DUMP_TEMPLATE, headerName, requestContext.getHeaderString(headerName)));
    }

    builder.append("----- HTTP REQUEST BODY ----------------\n");
    try (Scanner sc = new Scanner(requestContext.getEntityStream())) {
      sc.useDelimiter("\\A");
      if (sc.hasNext()) {
        String body = sc.next();
        requestContext.setEntityStream(new ByteArrayInputStream(body.getBytes()));
        builder.append(body);
      }
    }

    log.debug(builder.toString());
  }

  /**
   * レスポンス情報をログ出力します.
   *
   * @param responseContext ContainerResponseContext
   */
  private void logResponse(ContainerResponseContext responseContext) {
    StringBuilder builder = new StringBuilder();
    builder.append("\n----- HTTP RESPONSE --------------------\n");
    builder.append(String.format(DUMP_TEMPLATE, "Status", responseContext.getStatus()));

    builder.append("----- HTTP RESPONSE HEADER -------------\n");
    for (String headerName : responseContext.getHeaders().keySet()) {
      builder.append(String.format(DUMP_TEMPLATE, headerName, responseContext.getHeaderString(headerName)));
    }

    log.debug(builder.toString());
  }

  /**
   * レスポンスボディーをログ出力します.
   *
   * @param writerInterceptorContext WriterInterceptorContext
   */
  private void logResponseBody(WriterInterceptorContext writerInterceptorContext) {
    StringBuilder builder = new StringBuilder();
    OutputStream originalStream = writerInterceptorContext.getOutputStream();
    try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
      writerInterceptorContext.setOutputStream(byteArrayOutputStream);
      writerInterceptorContext.proceed();
      builder.append("\n----- HTTP RESPONSE BODY ---------------\n");
      builder.append(byteArrayOutputStream.toString("UTF-8"));
      byteArrayOutputStream.writeTo(originalStream);
      writerInterceptorContext.setOutputStream(originalStream);

    } catch (IOException ioe) {
      throw new SystemException(ioe);
    }

    log.debug(builder.toString());
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
