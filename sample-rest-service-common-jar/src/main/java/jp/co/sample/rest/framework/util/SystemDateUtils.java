package jp.co.sample.rest.framework.util;

import jp.co.sample.common.code.DateFormatVo;
import jp.co.sample.common.util.DateFormatUtilsExt;
import jp.co.sample.common.util.LocalDateTimeFormatUtils;
import jp.co.sample.rest.framework.message.MessageId;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Properties;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * システム日付ユーティリティー.
 * システム日付を変更したい場合は、{@code systemDate.properties} に {@code systemDate=yyyyMMdd} で設定してください.
 */
@UtilityClass
@Slf4j
public class SystemDateUtils {

  /** システム日付プロパティ名. */
  private static final String SYSTEM_DATE_PROPERTIES_NAME = "/systemDate.properties";

  /** Key値. */
  private static final String KEY_VALUE = "systemDate";

  /** システム日付（みなし日付）. */
  private static String systemDate;

  /** プロパティ読み込み. */
  static {
    loadProperty();
  }

  /**
   * 現在の日付文字列(yyyyMMdd)を取得します.
   * システム日付プロパティに値が設定されている場合、その値を優先します.
   *
   * @return 現在の日付文字列(yyyyMMdd)
   */
  public static String getNowDateString() {
    return StringUtils.isEmpty(systemDate) ? DateFormatUtilsExt.format(new Date(), DateFormatVo.YYYYMMDD_NO_DELIMITER) : systemDate;
  }

  /**
   * 現在のLocalDateTimeを取得します.
   * システム日付プロパティに値が設定されている場合、その値を優先します.
   *
   * @return 現在のLocalDateTime
   */
  public static LocalDateTime getNowLocalDateTime() {
    return LocalDateTimeFormatUtils.parse(getNowDateString() + DateFormatUtilsExt.format(new Date(), DateFormatVo.HHMMSSSSS_NO_DELIMITER),
        DateFormatVo.YYYYMMDDHHMMSSSSS_NO_DELIMITER);
  }

  /**
   * プロパティを読み込みます.
   */
  private static void loadProperty() {
    try (InputStream is = SystemDateUtils.class.getResourceAsStream(SYSTEM_DATE_PROPERTIES_NAME)) {
      Properties property = new Properties();
      property.load(is);

      String value = property.getProperty(KEY_VALUE);
      if (!StringUtils.isEmpty(value)) {
        systemDate = value;
      }
      log.info(MessageUtils.getMessage(MessageId.F0005I, systemDate));

    } catch (IOException ioe) {
      log.warn(MessageUtils.getMessage(MessageId.F0003W, SYSTEM_DATE_PROPERTIES_NAME));

    }
  }

}
