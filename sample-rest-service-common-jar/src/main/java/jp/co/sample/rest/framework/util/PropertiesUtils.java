package jp.co.sample.rest.framework.util;

import jp.co.sample.rest.framework.message.MessageId;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * プロパティ・ユーティリティー.
 */
@UtilityClass
@Slf4j
public class PropertiesUtils {

  /**
   * 設定ファイルを読み込み、{@code Properties} を返します.
   *
   * @param resName リソース名
   * @return 設定ファイル
   */
  public static Properties get(String resName) {
    try (InputStream is = PropertiesUtils.class.getResourceAsStream(resName)) {

      Properties property = new Properties();
      property.load(Objects.requireNonNull(is));
      log.info(MessageUtils.getMessage(MessageId.F0002I, resName));

      return property;

    } catch (Exception e) {
      log.warn(MessageUtils.getMessage(MessageId.F0003W, resName));
      return null;

    }

  }

}
