package jp.co.jaxrs.framework.pres.json.map;

import jp.co.jaxrs.framework.code.DateFormatVo;
import jp.co.jaxrs.framework.util.DateFormatUtilsExt;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Date;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

/**
 * Date型のJSONシリアライザー.
 */
@Provider
public class DateConverterProvider implements ParamConverterProvider {

  /**
   * コンバーターを取得します.
   */
  @SuppressWarnings("unchecked")
  @Override
  public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
    if (rawType != Date.class) {
      return null;
    }
    return (ParamConverter<T>) new ParamConverter<Date>() {

      /**
       * 指定された値を解析してDateのインスタンスを作成します.
       *
       * @param value 値
       * @return Dateのインスタンス
       */
      @Override
      public Date fromString(String value) {
        return DateFormatUtilsExt.parse(value, DateFormatVo.YYYYMMDD);
      }

      /**
       * 文字列に変換します.
       *
       * @param date {@link Date}
       * @return 変換した文字列
       */
      @Override
      public String toString(Date date) {
        return DateFormatUtilsExt.format(date, DateFormatVo.YYYYMMDD);
      }
    };
  }

}
