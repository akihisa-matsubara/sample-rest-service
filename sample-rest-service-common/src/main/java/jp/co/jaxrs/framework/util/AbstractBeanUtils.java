package jp.co.jaxrs.framework.util;

import jp.co.jaxrs.framework.exception.ApplicationException;
import jp.co.jaxrs.framework.message.MessageId;
import jp.co.jaxrs.framework.pres.dto.ErrorDto;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.beanutils.converters.ByteConverter;
import org.apache.commons.beanutils.converters.CharacterConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.ShortConverter;

/**
 * Beanユーティリティー.
 * ※アプリ固有のConverterは継承先で追加してください.
 */
public abstract class AbstractBeanUtils {

  static {
    register();
  }

  /**
   * デフォルトコンストラクタ.
   */
  protected AbstractBeanUtils() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * プロパティ名が同じ場合はすべて、コピー元のBeanからコピー先のBeanにプロパティ値をコピー(shallow copy)します.
   *
   * @param <T> 引数無しのデフォルトコンストラクターを持つ任意のクラス
   * @param type コピー先のBeanクラス
   * @param orig コピー元のBean
   * @return コピー先のBeanインスタンス
   */
  public static <T> T copyProperties(Class<T> type, final Object orig) {
    try {
      T dest = type.getDeclaredConstructor().newInstance();
      copyProperties(dest, orig);
      return dest;
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
        | SecurityException e) {
      throw new ApplicationException(e, new ErrorDto(MessageId.F0001E, type.getSimpleName(), orig.getClass().getSimpleName()));
    }
  }

  /**
   * プロパティ名が同じ場合はすべて、コピー元のBeanからコピー先のBeanにプロパティ値をコピー(shallow copy)します.
   *
   * @param dest コピー先のBean
   * @param orig コピー元のBean
   */
  public static void copyProperties(final Object dest, final Object orig) {
    try {
      BeanUtils.copyProperties(dest, orig);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new ApplicationException(e, new ErrorDto(MessageId.F0001E, dest.getClass().getSimpleName(), orig.getClass().getSimpleName()));
    }
  }

  /**
   * 初期値設定付きのコンバーターを登録します.
   */
  protected static void register() {
    // TODO 見直し
    ConvertUtils.register(new DateConverter(null), Date.class);
    ConvertUtils.register(null, Timestamp.class);
    ConvertUtils.register(new BooleanConverter(null), Boolean.class);
    ConvertUtils.register(new CharacterConverter(null), Character.class);
    ConvertUtils.register(new ByteConverter(null), Byte.class);
    ConvertUtils.register(new ShortConverter(null), Short.class);
    ConvertUtils.register(new IntegerConverter(null), Integer.class);
    ConvertUtils.register(new LongConverter(null), Long.class);
    ConvertUtils.register(new FloatConverter(null), Float.class);
    ConvertUtils.register(new DoubleConverter(null), Double.class);
    ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
  }
}
