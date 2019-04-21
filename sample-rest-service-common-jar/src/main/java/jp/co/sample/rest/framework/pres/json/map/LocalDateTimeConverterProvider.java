package jp.co.sample.rest.framework.pres.json.map;

import jp.co.sample.rest.framework.code.DateFormatVo;
import jp.co.sample.rest.framework.util.LocalDateTimeFormatUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

/**
 * LocalDateTime型のJSONシリアライザー.
 */
@Provider
public class LocalDateTimeConverterProvider implements ParamConverterProvider {

  /**
   * コンバーターを取得します.
   */
  @SuppressWarnings("unchecked")
  @Override
  public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
    if (rawType != LocalDateTime.class) {
      return null;
    }
    return (ParamConverter<T>) new ParamConverter<LocalDateTime>() {

      /**
       * 指定された値を解析してLocalDateTimeのインスタンスを作成します.
       *
       * @param value 値
       * @return LocalDateTimeのインスタンス
       */
      @Override
      public LocalDateTime fromString(String value) {
        return LocalDateTimeFormatUtils.parse(value, DateFormatVo.YYYYMMDD);
      }

      /**
       * 文字列に変換します.
       *
       * @param dateTime {@link LocalDateTime}
       * @return 変換した文字列
       */
      @Override
      public String toString(LocalDateTime dateTime) {
        return LocalDateTimeFormatUtils.format(dateTime, DateFormatVo.YYYYMMDDTHHMMSSSSS);
      }
    };
  }

}
