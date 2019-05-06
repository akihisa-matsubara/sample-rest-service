package jp.co.sample.rest.framework.util;

import jp.co.sample.rest.framework.pres.dto.ResponseBaseDto;
import jp.co.sample.rest.framework.provider.JsonpProvider;
import java.util.Map;
import java.util.Map.Entry;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * Client Wrapper.
 * Singletonから変更する場合は、破棄するタイミングでインスタンス内のClientをcloseしてください.
 */
@UtilityClass
@Slf4j
public class ClientWrapper {

  /** Client (thread safe). */
  private static final Client client;

  static {
    client = ClientBuilder.newClient();
    client.register(JsonpProvider.class);
  }

  /**
   * Getリクエスト（with Path Parameter）を送信します.
   * 取得したResponseは呼び出し元で適切にcloseしてください.
   *
   * @param uri Web Resource URI
   * @param path Path Parameter
   * @return {@link Response} Response 実行前にエラーが発生した場合はnull
   */
  public static Response getWithPath(String uri, String path) {
    return get(target(uri).path(path));
  }

  /**
   * Getリクエスト（with Path Parameter）を送信します.
   *
   * @param <T> Response Dto
   * @param uri Web Resource URI
   * @param path Path Parameter
   * @param responseType Response Type
   * @return ResponseBaseDto Response 実行前にエラーが発生した場合はnull
   */
  @SuppressWarnings("rawtypes")
  public static <T extends ResponseBaseDto> T getWithPath(String uri, String path, Class<T> responseType) {
    Response response = getWithPath(uri, path);
    return readAndCloseEntity(response, responseType);
  }

  /**
   * Getリクエスト（with Query Parameters）を送信します.
   * 取得したResponseは呼び出し元で適切にcloseしてください.
   *
   * @param uri Web Resource URI
   * @param queryParams クエリパラメーター
   * @return {@link Response} Response 実行前にエラーが発生した場合はnull
   */
  public static Response getWithQueryParams(String uri, Map<String, Object[]> queryParams) {
    WebTarget target = target(uri);
    for (Entry<String, Object[]> queryParam : queryParams.entrySet()) {
      target = target.queryParam(queryParam.getKey(), queryParam.getValue());
    }
    return get(target);
  }

  /**
   * Getリクエスト（with Query Parameters）を送信します.
   *
   * @param <T> Response Dto
   * @param uri Web Resource URI
   * @param queryParams クエリパラメーター
   * @param responseType Response Type
   * @return ResponseBaseDto Response 実行前にエラーが発生した場合はnull
   */
  @SuppressWarnings("rawtypes")
  public static <T extends ResponseBaseDto> T getWithQueryParams(String uri, Map<String, Object[]> queryParams, Class<T> responseType) {
    Response response = getWithQueryParams(uri, queryParams);
    return readAndCloseEntity(response, responseType);
  }

  /**
   * Getリクエストを送信します.
   * 取得したResponseは呼び出し元で適切にcloseしてください.
   *
   * @param uri Web Resource URI
   * @return {@link Response} Response 実行前にエラーが発生した場合はnull
   */
  public static Response get(String uri) {
    return get(target(uri));
  }

  /**
   * Getリクエストを送信します.
   *
   * @param <T> Response Dto
   * @param uri Web Resource URI
   * @param responseType Response Type
   * @return ResponseBaseDto Response 実行前にエラーが発生した場合はnull
   */
  @SuppressWarnings("rawtypes")
  public static <T extends ResponseBaseDto> T get(String uri, Class<T> responseType) {
    Response response = get(uri);
    return readAndCloseEntity(response, responseType);

  }

  /**
   * Getリクエストを送信します.
   *
   * @param target {@link WebTarget}
   * @return {@link Response} Response 実行前にエラーが発生した場合はnull
   */
  private static Response get(WebTarget target) {
    Response response = null;

    try {
      response = target.request(MediaType.APPLICATION_JSON_TYPE).get();

    } catch (WebApplicationException wae) {
      // 外部サービス呼び出し時に例外が発生しても業務継続する
      log.warn(ExceptionUtils.getStackTrace(wae));
      return wae.getResponse();

    } catch (Exception e) {
      // 外部サービス呼び出し時に例外が発生しても業務継続する
      log.warn(ExceptionUtils.getStackTrace(e));
      return null;

    }

    return response;
  }

  /**
   * {@link WebTarget} インスタンスを取得します.
   *
   * @param uri Web Resource URI
   * @return {@link WebTarget}
   */
  private static WebTarget target(String uri) {
    return client.target(uri);
  }

  /**
   * ResponseからEntityを取得し、Responseをクローズします.
   *
   * @param <T> Entity
   * @param response {@link Response}
   * @param entityType Entity Type
   * @return entity Responseがnullの場合はnull
   */
  public static <T> T readAndCloseEntity(Response response, Class<T> entityType) {
    if (response == null) {
      return null;
    }

    T entity = response.readEntity(entityType);
    response.close();
    return entity;

  }

}
