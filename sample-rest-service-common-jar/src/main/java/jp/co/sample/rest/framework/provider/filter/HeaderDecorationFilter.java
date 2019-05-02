package jp.co.sample.rest.framework.provider.filter;

import java.io.IOException;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

/**
 * ヘッダー編集フィルター.
 */
@Provider
@Priority(Priorities.HEADER_DECORATOR)
@Slf4j
public class HeaderDecorationFilter implements ContainerResponseFilter {

  /**
   * ヘッダーを編集します.
   * リクエスト応答時にContent-Type:application/jsonが設定されていなければ追加します.
   *
   * @param requestContext ContainerRequestContext
   * @param responseContext ContainerResponseContext
   */
  @Override
  public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
    if (!responseContext.getHeaders().containsKey(HttpHeaders.CONTENT_TYPE)) {
      responseContext.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
      log.debug("Response header add {}: {}", HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
    }
  }

}
