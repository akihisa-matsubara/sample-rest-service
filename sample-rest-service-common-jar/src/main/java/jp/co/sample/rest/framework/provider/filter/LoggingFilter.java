package jp.co.sample.rest.framework.provider.filter;

import jp.co.sample.common.code.EncodingVo;
import jp.co.sample.rest.framework.code.LoggerVo;
import jp.co.sample.rest.framework.pres.dto.ResponseDto;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
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
   * @param context ContainerRequestContext
   */
  @Override
  public void filter(ContainerRequestContext context) throws IOException {
    MDC.put(START_TIME, String.valueOf(System.currentTimeMillis()));

    ACCESS_LOGGER.debug("Resource:/{}, Method-Type:{}, Method-Name:{}", context.getUriInfo().getPath(),
        context.getMethod(), resourceInfo.getResourceMethod().getName());
    logRequest(context);
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
   * @param context WriterInterceptorContext
   */
  @Override
  public void aroundWriteTo(WriterInterceptorContext context) throws IOException {
    logResponseBody(context);

  }

  /**
   * リクエスト情報をログ出力します.
   *
   * @param context ContainerRequestContext
   */
  private void logRequest(ContainerRequestContext context) {
    StringBuilder builder = new StringBuilder();
    builder.append("\n----- HTTP REQUEST ---------------------\n");
    builder.append(String.format(DUMP_TEMPLATE, "Request URL", context.getUriInfo().getAbsolutePath()));
    builder.append(String.format(DUMP_TEMPLATE, "Request Method", context.getMethod()));
    builder.append(String.format(DUMP_TEMPLATE, "Resource Method", resourceInfo.getResourceMethod().getName()));
    builder.append(String.format(DUMP_TEMPLATE, "Class", resourceInfo.getResourceClass().getCanonicalName()));

    builder.append("----- PATH PARAMETER -------------------\n");
    builder.append(dumpParameters(context.getUriInfo().getPathParameters()));

    builder.append("----- QUERY PARAMETER ------------------\n");
    builder.append(dumpParameters(context.getUriInfo().getQueryParameters()));

    builder.append("----- HTTP REQUEST HEADER --------------\n");
    for (String headerName : context.getHeaders().keySet()) {
      builder.append(String.format(DUMP_TEMPLATE, headerName, context.getHeaderString(headerName)));
    }

    builder.append("----- HTTP REQUEST BODY ----------------\n");
    try (Scanner sc = new Scanner(context.getEntityStream())) {
      sc.useDelimiter("\\A");
      if (sc.hasNext()) {
        String body = sc.next();
        context.setEntityStream(new ByteArrayInputStream(body.getBytes()));
        builder.append(body);
      }
    }

    log.debug(builder.toString());
  }

  /**
   * レスポンス情報をログ出力します.
   *
   * @param context ContainerResponseContext
   */
  private void logResponse(ContainerResponseContext context) {
    StringBuilder builder = new StringBuilder();
    builder.append("\n----- HTTP RESPONSE --------------------\n");
    builder.append(String.format(DUMP_TEMPLATE, "Status", context.getStatus()));

    builder.append("----- HTTP RESPONSE HEADER -------------\n");
    for (String headerName : context.getHeaders().keySet()) {
      builder.append(String.format(DUMP_TEMPLATE, headerName, context.getHeaderString(headerName)));
    }

    log.debug(builder.toString());
  }

  /**
   * レスポンスボディーをログ出力します.
   *
   * @param context WriterInterceptorContext
   * @throws IOException IO例外
   */
  private void logResponseBody(WriterInterceptorContext context) throws IOException {
    ResponseDto response = (ResponseDto) context.getEntity();
    if (response == null || response.getResponse() == null) {
      return;
    }

    StringBuilder builder = new StringBuilder();
    OutputStream originalStream = context.getOutputStream();
    try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
      context.setOutputStream(byteArrayOutputStream);
      context.proceed();
      builder.append("\n----- HTTP RESPONSE BODY ---------------\n");
      builder.append(byteArrayOutputStream.toString(EncodingVo.UTF8.getCode()));
      byteArrayOutputStream.writeTo(originalStream);

    } finally {
      context.setOutputStream(originalStream);

    }

    log.debug(builder.toString());
  }

  /**
   * 性能情報をログ出力します.
   *
   * @param context ContainerRequestContext
   */
  private void logPerformance(ContainerRequestContext context) {
    String startTime = MDC.get(START_TIME);
    if (startTime == null || startTime.isEmpty()) {
      return;
    }
    long executionTime = System.currentTimeMillis() - Long.parseLong(startTime);

    PERFORMANCE_LOGGER.debug("Total time: {} milliseconds, Resource: /{}, Method-Type: {}, Method-Name: {}", executionTime,
        context.getUriInfo().getPath(), context.getMethod(), resourceInfo.getResourceMethod().getName());
  }

  /**
   * パラメーター情報をダンプします.
   *
   * @param paramters パラメーター
   * @return パラメーターのダンプ
   */
  private StringBuilder dumpParameters(Map<String, List<String>> paramters) {
    StringBuilder dump = new StringBuilder();
    for (Map.Entry<String, List<String>> entry : paramters.entrySet()) {
      List<String> obj = entry.getValue();
      String value = null;
      if (obj != null && !obj.isEmpty()) {
        value = obj.get(0);
      }
      dump.append(String.format(DUMP_TEMPLATE, entry.getKey(), value));
    }
    return dump;
  }

}
