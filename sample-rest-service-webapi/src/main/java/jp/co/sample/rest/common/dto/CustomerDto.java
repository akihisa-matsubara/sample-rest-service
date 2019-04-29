package jp.co.sample.rest.common.dto;

import jp.co.sample.common.code.DateFormat;
import java.util.Date;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
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

  /**
   * 生年月日を取得します.
   *
   * @return 生年月日
   */
  @JsonFormat(shape = Shape.STRING, pattern = DateFormat.YYYYMMDD)
  public Date getBirthday() {
    return this.birthday;
  }

}
