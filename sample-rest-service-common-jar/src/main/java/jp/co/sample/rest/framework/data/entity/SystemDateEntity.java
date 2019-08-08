package jp.co.sample.rest.framework.data.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * システム日付マスタEntity.
 */
@Entity
@Table(name = "M_SYSTEM_DATE")
@Data
@EqualsAndHashCode(callSuper = true)
public class SystemDateEntity extends DbBaseEntity {

  /** serialVersionUID. */
  private static final long serialVersionUID = 7997404289024567972L;

  /** 取得Query. */
  public static final String FIND = "SystemDate.find";

  /** システム日付. */
  @Id
  @Column(name = "SYSTEM_DATE")
  private LocalDate systemDate;

}
