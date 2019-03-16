package jp.co.jaxrs.sample.pres.dto;

import java.util.Date;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 顧客Dto.
 */
@Getter
@Setter
public class CustomerDto {

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
  private Date birthday;

  /** 郵便番号. */
  private String addressZip;

  /** 住所. */
  private String address;

}
