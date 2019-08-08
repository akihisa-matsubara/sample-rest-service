package jp.co.sample.rest.framework.config;

import jp.co.sample.common.constant.Profile;
import jp.co.sample.rest.framework.message.MessageId;
import jp.co.sample.rest.framework.util.MessageUtils;
import jp.co.sample.rest.framework.util.PropertiesUtils;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 有効なプロファイル.
 */
@ApplicationScoped
@Getter
@Slf4j
public class ActiveProfile {

  /** Key値:activeProfile. */
  private static final String KEY_ACTIVE_PROFILE = "activeProfile";

  /** プロファイル共通設定ファイル名. */
  private static final String COMMON_CONFIG_FILE_NAME = "/application.properties";

  /** active prifile. */
  private String profile;

  /** 本番環境制限. */
  private boolean restriction;

  /**
   * 初期化.
   */
  @PostConstruct
  public void initialize() {
    profile = PropertiesUtils.get(COMMON_CONFIG_FILE_NAME).getProperty(KEY_ACTIVE_PROFILE);
    log.info(MessageUtils.getMessage(MessageId.F0004I, profile));
    restrict();

  }

  /**
   * 本番環境制限を初期化します.
   */
  private void restrict() {
    if (profile == null) {
      restriction = true;
      return;
    }
    switch (profile) {
      case Profile.IT:
      case Profile.UT:
      case Profile.DEV:
        restriction = false;
        break;
      default:
        restriction = true;
        break;
    }
  }

}
