package jp.co.sample.rest.framework.config;

import jp.co.sample.common.constant.Profile;
import jp.co.sample.rest.framework.message.MessageId;
import jp.co.sample.rest.framework.util.MessageUtils;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
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

  /** active prifile. */
  @Resource(name = "active-profile")
  private String profile;

  /** 本番環境制限. */
  private boolean restriction;

  /**
   * 初期化.
   */
  @PostConstruct
  public void initialize() {
    log.info(MessageUtils.getMessage(MessageId.F0004I, profile));
    restrict();

  }

  /**
   * 本番環境制限を初期化します.
   */
  private void restrict() {
    switch (profile) {
      case Profile.PROD:
      case Profile.ST:
        restriction = true;
        break;
      default:
        restriction = false;
        break;
    }
  }

}
