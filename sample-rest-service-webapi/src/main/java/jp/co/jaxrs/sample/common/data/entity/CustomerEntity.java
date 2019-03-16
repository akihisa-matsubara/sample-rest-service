package jp.co.jaxrs.sample.common.data.entity;

import jp.co.jaxrs.framework.data.entity.DbBaseEntity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 顧客Entity.
 */
@Entity
@Table(name = "T_CUSTOMER")
@Data
@EqualsAndHashCode(callSuper = true)
@NamedQueries({
    @NamedQuery(name = CustomerEntity.FIND_ALL, query = "SELECT cst FROM CustomerEntity cst ORDER BY cst.customerNo"),
    @NamedQuery(name = CustomerEntity.DELETE_BY_ID, query = "DELETE FROM CustomerEntity cst WHERE cst.customerNo = :customerNo")
})
public class CustomerEntity extends DbBaseEntity {

  /** 全件取得Query. */
  public static final String FIND_ALL = "Customer.findAll";

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
  private String gender;

  /** 生年月日. */
  @Column(name = "BIRTHDAY")
  @Temporal(TemporalType.DATE)
  private Date birthday;

  /** 郵便番号. */
  @Column(name = "ADDRESS_ZIP")
  private String addressZip;

  /** 住所. */
  @Column(name = "ADDRESS")
  private String address;

}
