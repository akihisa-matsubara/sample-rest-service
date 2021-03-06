package dev.sample.rest.data.entity;

import dev.sample.common.code.GenderVo;
import dev.sample.framework.core.data.entity.DbBaseEntity;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 顧客Entity.
 */
@Entity
@Table(name = "T_CUSTOMER")
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerEntity extends DbBaseEntity {

  /** serialVersionUID. */
  private static final long serialVersionUID = 7997404289024567972L;

  /** 主キー削除Query. */
  public static final String DELETE_BY_ID = "Customer.deleteById";

  /** 顧客番号. */
  @Id
  @Column(name = "CUSTOMER_NO")
  private String customerNo;

  /** 氏名漢字. */
  @Column(name = "NAME_KANJI")
  private String nameKanji;

  /** 氏名カナ. */
  @Column(name = "NAME_KANA")
  private String nameKana;

  /** 性別. */
  @Column(name = "GENDER")
  private GenderVo gender;

  /** 生年月日. */
  @Column(name = "BIRTHDAY")
  private LocalDate birthday;

  /** 電話番号. */
  @Column(name = "TEL_NO")
  private String telNo;

  /** 郵便番号. */
  @Column(name = "ADDRESS_ZIP")
  private String addressZip;

  /** 住所. */
  @Column(name = "ADDRESS")
  private String address;

  /** Eメール. */
  @Column(name = "EMAIL")
  private String email;

}
