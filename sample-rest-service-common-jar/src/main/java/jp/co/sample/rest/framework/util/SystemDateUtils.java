package jp.co.sample.rest.framework.util;

import jp.co.sample.framework.util.AbstractSystemDateUtils;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

/**
 * システム日付ユーティリティー.
 * システム日付を変更したい場合は、クラスパス上にsystemDate.propertiesを配置し、
 * systemDate=yyyyMMdd形式で設定してください.
 */
@UtilityClass
public class SystemDateUtils extends AbstractSystemDateUtils {

  /** システム日付プロパティ名. */
  private static final String SYSTEM_DATE_PROPERTIES_NAME = "systemDate.properties";

  /** Key値. */
  private static final String KEY_VALUE = "systemDate";

  /** プロパティ読み込み. */
  static {
    try {
      ResourceBundle property = ResourceBundle.getBundle(SYSTEM_DATE_PROPERTIES_NAME);
      String value = property.getString(KEY_VALUE);
      if (!StringUtils.isEmpty(value)) {
        setSystemDate(value);
      }

    } catch (MissingResourceException mre) {
      // nothing
    }
  }

}