package jp.co.sample.rest.framework.config;

import jp.co.sample.rest.framework.message.MessageId;
import jp.co.sample.rest.framework.util.MessageUtils;
import jp.co.sample.rest.framework.util.PropertiesUtils;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * 限定子 {@code @Config(key)} で指定されたキー値をインジェクションするプロデューサークラス.
 */
@ApplicationScoped
public class ConfigProducer {

  /** デフォルトファイル名. */
  private static final String DEFAULT_FILE_NAME = "/application.properties";

  /** プロファイル置換文字列. */
  private static final String REPLACE_PROFILE_STR = "profile";

  /** プロファイル別ファイル名. */
  private static final String PROFILE_FILE_NAME = "/application-" + REPLACE_PROFILE_STR + ".properties";

  /** 設定値が存在しない場合のint型デフォルト値. */
  private static final int UNDEFINED = -1;

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
    commonConfiguration = PropertiesUtils.get(DEFAULT_FILE_NAME);
    if (StringUtils.isNotEmpty(profile.getProfile())) {
      profileConfiguration = PropertiesUtils.get(PROFILE_FILE_NAME.replaceAll(REPLACE_PROFILE_STR, profile.getProfile()));
    }
  }

  /**
   * 指定されたキーに対応した設定値を取得します.
   * value属性に値が指定されていない場合、フィールド名をキーに設定します.
   *
   * @param ip インジェクションポイント
   * @return 設定値、設定値が存在しない場合は-1
   */
  @Produces
  @Config
  @Dependent
  public int getInteger(InjectionPoint ip) {
    String strValue = getString(ip);
    return NumberUtils.toInt(strValue, UNDEFINED);

  }

  /**
   * 指定されたキーに対応した設定値を取得します.
   * value属性に値が指定されていない場合、フィールド名をキーに設定します.
   *
   * @param ip インジェクションポイント
   * @return 設定値、設定値が存在しない場合はnull
   */
  @Produces
  @Config
  @Dependent
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
   * @return 設定値、設定値がいずれも存在しない場合はnull
   */
  public String getValue(String key) {
    String value = null;
    if (profileConfiguration != null && profileConfiguration.containsKey(key)) {
      value = profileConfiguration.getProperty(key);
    } else if (commonConfiguration != null && commonConfiguration.containsKey(key)) {
      value = commonConfiguration.getProperty(key);
    }

    if (value == null) {
      throw new IllegalArgumentException(MessageUtils.getMessage(MessageId.F0007E, key));
    }

    return value;

  }

}
