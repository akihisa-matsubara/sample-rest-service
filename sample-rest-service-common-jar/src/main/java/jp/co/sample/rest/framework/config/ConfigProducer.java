package jp.co.sample.rest.framework.config;

import jp.co.sample.rest.framework.message.MessageId;
import jp.co.sample.rest.framework.util.MessageUtils;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 限定子 {@code @Config(key)} で指定されたキー値をインジェクションするプロデューサクラス.
 */
@ApplicationScoped
@Startup
@Slf4j
public class ConfigProducer {

  /** デフォルトファイル名. */
  private static final String DEFAULT_FILE_NAME = "/application.properties";

  /** プロファイル置換文字列. */
  private static final String REPLACE_PROFILE_STR = "profile";

  /** プロファイル別ファイル名. */
  private static final String PROFILE_FILE_NAME = "/application_" + REPLACE_PROFILE_STR + ".properties";

  /** 有効なプロファイル. */
  @Inject
  private ActiveProfile profile;

  /** プロファイル共通設定ファイル. */
  private Properties commonConfiguration;

  /** プロファイル別設定ファイル. */
  private Properties profileConfiguration;

  /**
   * 初期化.
   */
  @PostConstruct
  public void initialize() {
    commonConfiguration = load(DEFAULT_FILE_NAME);
    profileConfiguration = load(PROFILE_FILE_NAME.replaceAll(REPLACE_PROFILE_STR, profile.getProfile()));

  }

  /**
   * 設定ファイルにロードします.
   *
   * @param resName リソース名
   * @return 設定ファイル
   */
  private Properties load(String resName) {
    try (InputStream is = this.getClass().getResourceAsStream(resName)) {

      Objects.requireNonNull(is);
      Properties property = new Properties();
      property.load(is);
      log.info(MessageUtils.getMessage(MessageId.F0002I, resName));

      return property;

    } catch (Exception e) {
      log.warn(MessageUtils.getMessage(MessageId.F0003W, resName));
      return null;

    }

  }

  /**
   * 指定されたキーに対応した設定値を取得します.
   * value属性に値が指定されていない場合、フィールド名をキーに設定します.
   *
   * @param ip インジェクションポイント
   * @return 設定値
   */
  @Produces
  @Config
  public String getString(InjectionPoint ip) {
    String key = ip.getAnnotated().getAnnotation(Config.class).value();
    if (StringUtils.isEmpty(key)) {
      key = ip.getMember().getName();
    }
    return getValue(key);

  }

  /**
   * 指定されたキーに対応した設定値を取得します.
   * プロファイル別設定ファイルに設定値が存在しない場合は、プロファイル共通設定ファイルから取得します.
   *
   * @param key キー
   * @return 設定値
   */
  public String getValue(String key) {
    String value = null;
    if (profileConfiguration != null && profileConfiguration.containsKey(key)) {
      value = profileConfiguration.getProperty(key);
    } else if (commonConfiguration != null && commonConfiguration.containsKey(key)) {
      value = commonConfiguration.getProperty(key);
    }

    return value;

  }

}
