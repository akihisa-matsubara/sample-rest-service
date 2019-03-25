package jp.co.jaxrs.framework.data.converter;

import jp.co.jaxrs.framework.code.DateFormatVo;
import jp.co.jaxrs.framework.util.DateFormatUtilsExt;
import jp.co.jaxrs.framework.util.LocalDateTimeFormatUtils;
import java.sql.Date;
import java.time.LocalDateTime;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 相互型変換コンバーター(Entity: LocalDateTime, Database: java.sql.Date).
 */
@Converter(autoApply = true)
public class LocalDateTimeToDateConverter implements AttributeConverter<LocalDateTime, Date> {

  /**
   * Entityに格納されている値をDatabaseに格納されるデータ型に変換します.
   *
   * @param attribute 変換されるEntity属性値
   * @return Database列に格納される変換データ
   */
  @Override
  public Date convertToDatabaseColumn(LocalDateTime attribute) {
    return attribute == null ? null : Date.valueOf(attribute.toLocalDate());
  }

  /**
   * Databaseに格納されている値をEntityに格納されるデータ型に変換します.
   *
   * @param attribute 変換されるDatabase属性値
   * @return Entityに格納される変換データ
   */
  @Override
  public LocalDateTime convertToEntityAttribute(Date attribute) {
    return attribute == null ? null : LocalDateTimeFormatUtils.parse(DateFormatUtilsExt.format(attribute, DateFormatVo.YYYYMMDD), DateFormatVo.YYYYMMDD);
  }

}
