package jp.co.sample.rest.framework.data.converter;

import java.sql.Date;
import java.time.LocalDate;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 相互型変換コンバーター(Entity: LocalDate, Database: java.sql.Date).
 */
@Converter(autoApply = true)
public class LocalDateToDateConverter implements AttributeConverter<LocalDate, Date> {

  /**
   * Entityに格納されている値をDatabaseに格納されるデータ型に変換します.
   *
   * @param attribute 変換されるEntity属性値
   * @return Database列に格納される変換データ
   */
  @Override
  public Date convertToDatabaseColumn(LocalDate attribute) {
    return attribute == null ? null : Date.valueOf(attribute);
  }

  /**
   * Databaseに格納されている値をEntityに格納されるデータ型に変換します.
   *
   * @param attribute 変換されるDatabase属性値
   * @return Entityに格納される変換データ
   */
  @Override
  public LocalDate convertToEntityAttribute(Date attribute) {
    return attribute == null ? null : attribute.toLocalDate();
  }

}
