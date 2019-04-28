package jp.co.sample.rest.framework.config;

import jp.co.sample.rest.framework.message.MessageId;
import jp.co.sample.rest.framework.util.MessageUtils;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

/**
 * プロファイル.
 */
@ApplicationScoped
@Slf4j
public class Profile {

  /** active prifile. */
  @Resource(name = "active-profile")
  private String activeProfile;

  /**
   * 初期化.
   */
  @PostConstruct
  public void initialized() {
    log.info(MessageUtils.getMessage(MessageId.F0004I, activeProfile));
  }

  /**
   * 有効なProfileを取得します.
   *
   * @return 有効なProfile
   */
  public String getActiveProfile() {
    return activeProfile;
  }

}
