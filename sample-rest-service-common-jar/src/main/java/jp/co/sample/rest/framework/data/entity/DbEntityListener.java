package jp.co.sample.rest.framework.data.entity;

import java.security.Principal;
import java.time.Clock;
import java.time.LocalDateTime;
import javax.inject.Inject;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * DB基底EntityListener.
 * 共通項目を設定します.
 */
public class DbEntityListener {

  /** Principal. */
  @Inject
  Principal principal;

  /**
   * Insert前処理.
   * @param entity DB基底Entity
   */
  @PrePersist
  public void prePersist(DbEntityBase entity) {
    preUpdate(entity);
    entity.setCreationDate(entity.getUpdatedDate());
    entity.setCreationUserId(entity.getUpdatedUserId());
  }

  /**
   * Update前処理.
   * @param entity DB基底Entity
   */
  @PreUpdate
  public void preUpdate(DbEntityBase entity) {
    entity.setUpdatedDate(LocalDateTime.now(Clock.systemDefaultZone()));
    entity.setUpdatedUserId(principal.getName());
  }

}
