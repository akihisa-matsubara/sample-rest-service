package jp.co.jaxrs.framework.util;

import jp.co.jaxrs.framework.code.DateFormatVo;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.apache.commons.lang3.StringUtils;

/**
 * システム日付ユーティリティー.
 */
public class SystemDateUtils {

  /** システム日付プロパティ名. */
  private static final String SYSTEM_DATE_PROPERTIES_NAME = "systemDate.properties";

  /** Key値. */
  private static final String KEY_VALUE = "systemDate";

  /** システム日付（みなし日付）. */
  private static String systemDate;

  /** プロパティ読み込み. */
  static {
    try {
      ResourceBundle property = ResourceBundle.getBundle(SYSTEM_DATE_PROPERTIES_NAME);
      String value = property.getString(KEY_VALUE);
      if (!StringUtils.isEmpty(value)) {
        systemDate = value;
      }

    } catch (MissingResourceException mre) {
      // nothing
    }
  }

  /**
   * コンストラクタ.
   */
  private SystemDateUtils() {
    throw new IllegalStateException("Utility class");
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
        DateFormatVo.YYYYMMDDHHMMSSSSS);
  }

}