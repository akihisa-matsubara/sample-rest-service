package jp.co.sample.rest.framework.util;

import jp.co.sample.common.code.DateFormat.DateFormatVo;
import jp.co.sample.common.util.LocalDateFormatUtils;
import jp.co.sample.rest.framework.data.dao.SystemDateDao;
import jp.co.sample.rest.framework.message.MessageId;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Properties;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * <PRE>
 * システム日付ユーティリティー.
 * システム日付（みなし日付）を変更したい場合は、以下の方法で設定が可能です.
 * どちらも設定されていた場合、システム日付プロパティの値が優先されます.
 * </PRE>
 * <ul>
 * <li>システム日付プロパティ（{@code systemDate.properties}）</li>
 * <li>システム日付マスタ（{@code M_SYSTEM_DATE}）</li>
 * </ul>
 */
@UtilityClass
@Slf4j
public class SystemDateUtils {

  /** システム日付プロパティ名. */
  private static final String SYSTEM_DATE_PROPERTIES_NAME = "/systemDate.properties";

  /** Key値. */
  private static final String KEY_VALUE = "systemDate";

  /** システム日付（みなし日付）（プロパティ設定値）. */
  private static Optional<LocalDate> propertyDateOpt = Optional.empty();

  /** 利用制限. */
  private static boolean restriction;

  /** プロパティ読み込み. */
  static {
    init();
  }

  /**
   * 現在の日付を文字列形式(yyyyMMdd)で取得します.
   * システム日付（みなし日付）に値が設定されている場合、その値を優先します.
   *
   * @return 現在の日付文字列(yyyyMMdd)
   */
  public static String getNowDateAsString() {
    return LocalDateFormatUtils.format(createDate(), DateFormatVo.YYYYMMDD_NO_DELIMITER);
  }

  /**
   * 現在のLocalDateTimeを取得します.
   * システム日付（みなし日付）に値が設定されている場合、その値を優先します.
   *
   * @return 現在のLocalDateTime
   */
  public static LocalDateTime getNowDateTime() {
    return LocalTime.now().atDate(createDate());
  }

  /**
   * システム日付（みなし日付）を初期化します.
   */
  private static void init() {
    Properties property = PropertiesUtils.get(SYSTEM_DATE_PROPERTIES_NAME);

    if (property != null) {
      String deemedDate = property.getProperty(KEY_VALUE);
      if (StringUtils.isNotEmpty(deemedDate)) {
        propertyDateOpt = Optional.of(LocalDateFormatUtils.parse(deemedDate, DateFormatVo.YYYYMMDD_NO_DELIMITER));
        log.info(MessageUtils.getMessage(MessageId.F0005I, deemedDate));
      }

    } else {
      // システム日付プロパティがない場合は本番環境とみなし利用を制限する
      restriction = true;
    }
  }

  /**
   * システム日付（みなし日付）を作成します.
   * 利用制限がある場合は実際の現在日付を返します.
   * プロパティ／DBともにみなし日付の設定がない場合は、実際の現在日付を返します.
   *
   * @return システム日付（みなし日付）
   */
  private static LocalDate createDate() {
    // 利用制限がある場合は実際の現在日付を返します
    if (restriction) {
      return LocalDate.now();
    }

    return propertyDateOpt.orElseGet(() -> {
      LocalDate deemedDate = CdiUtils.getBean(SystemDateDao.class).find().getSystemDate();
      return deemedDate != null ? deemedDate : LocalDate.now();
    });
  }

}
