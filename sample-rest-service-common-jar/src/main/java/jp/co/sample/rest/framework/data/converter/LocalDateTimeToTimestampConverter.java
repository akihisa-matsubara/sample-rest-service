package jp.co.sample.rest.framework.data.converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 相互型変換コンバーター(Entity: LocalDateTime, Database: Timestamp).
 */
@Converter(autoApply = true)
public class LocalDateTimeToTimestampConverter implements AttributeConverter<LocalDateTime, Timestamp> {

  /**
   * Entityに格納されている値をDatabaseに格納されるデータ型に変換します.
   *
   * @param attribute 変換されるEntity属性値
   * @return Database列に格納される変換データ
   */
  @Override
  public Timestamp convertToDatabaseColumn(LocalDateTime attribute) {
    return attribute == null ? null : Timestamp.valueOf(attribute);
  }

  /**
   * Databaseに格納されている値をEntityに格納されるデータ型に変換します.
   *
   * @param attribute 変換されるDatabase属性値
   * @return Entityに格納される変換データ
   */
  @Override
  public LocalDateTime convertToEntityAttribute(Timestamp attribute) {
    return attribute == null ? null : attribute.toLocalDateTime();
  }

}
