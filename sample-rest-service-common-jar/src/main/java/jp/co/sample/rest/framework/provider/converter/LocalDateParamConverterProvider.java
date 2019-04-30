package jp.co.sample.rest.framework.provider.converter;

import jp.co.sample.common.code.DateFormat.DateFormatVo;
import jp.co.sample.common.util.LocalDateFormatUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

/**
 * LocalDate型のパラメーターコンバーター.
 */
@Provider
public class LocalDateParamConverterProvider implements ParamConverterProvider {

  /**
   * コンバーターを取得します.
   */
  @SuppressWarnings("unchecked")
  @Override
  public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
    if (rawType != LocalDate.class) {
      return null;
    }
    return (ParamConverter<T>) new ParamConverter<LocalDate>() {

      /**
       * 指定された値を解析してLocalDateのインスタンスを作成します.
       *
       * @param value 値
       * @return LocalDateのインスタンス
       */
      @Override
      public LocalDate fromString(String value) {
        return LocalDateFormatUtils.parse(value, DateFormatVo.YYYYMMDD);
      }

      /**
       * 文字列に変換します.
       *
       * @param date {@link LocalDateTime}
       * @return 変換した文字列
       */
      @Override
      public String toString(LocalDate date) {
        return LocalDateFormatUtils.format(date, DateFormatVo.YYYYMMDD);
      }
    };
  }

}
