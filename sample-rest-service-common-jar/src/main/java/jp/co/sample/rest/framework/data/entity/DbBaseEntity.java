package jp.co.sample.rest.framework.data.entity;

import jp.co.sample.rest.framework.data.converter.LocalDateTimeToTimestampConverter;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import lombok.Data;

/**
 * DB基底Entity.
 */
@Data
@MappedSuperclass
public abstract class DbBaseEntity implements Serializable {

  /** serialVersionUID. */
  private static final long serialVersionUID = -486137350490161455L;

  /** バージョン. */
  @Version
  @Column(name = "VERSION")
  private int version;

  /** 作成ユーザーID. */
  @Column(name = "CREATION_USER_ID", updatable = false)
  private String creationUserId;

  /** 作成日時. */
  @Column(name = "CREATION_DATE", updatable = false)
  @Convert(converter = LocalDateTimeToTimestampConverter.class)
  private LocalDateTime creationDate;

  /** 更新ユーザーID. */
  @Column(name = "UPDATED_USER_ID")
  private String updatedUserId;

  /** 更新日時. */
  @Column(name = "UPDATED_DATE")
  @Convert(converter = LocalDateTimeToTimestampConverter.class)
  private LocalDateTime updatedDate;

}
