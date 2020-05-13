package jp.co.sample.rest.common.dto;

import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * 顧客Dto.
 */
@Data
public class CustomerDto implements Serializable {

  /** serialVersionUID. */
  private static final long serialVersionUID = 8473313641114716747L;

  /** 顧客番号. */
  @Size(min = 8, max = 8)
  private String customerNo;

  /** 氏名漢字. */
  private String nameKanji;

  /** 氏名カナ. */
  private String nameKana;

  /** 性別. */
  private String gender;

  /** 生年月日. */
  private LocalDate birthday;

  /** 電話番号. */
  private String telNo;

  /** 郵便番号. */
  private String addressZip;

  /** 住所. */
  private String address;

  /** Eメール. */
  private String email;

}
