package jp.co.sample.rest.framework.util;

import jp.co.sample.rest.framework.message.MessageId;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;
import lombok.experimental.UtilityClass;

/**
 * メッセージ・ユーティリティー.
 */
@UtilityClass
public class MessageUtils {

  /** メッセージプロパティ名. */
  private static final String MESSAGE_PROPERTIES_NAME = "/messages_jp.properties";

  /** メッセージプロパティ. */
  private static Properties messages = null;

  static {
    messages = new Properties();
    InputStream is = MessageUtils.class.getResourceAsStream(MESSAGE_PROPERTIES_NAME);

    try {
      messages.load(is);
    } catch (IOException ioe) {
      throw new ExceptionInInitializerError("メッセージプロパティファイルの読み込みが失敗しました。");
    }
  }

  /**
   * メッセージを取得します.
   *
   * @param messageId メッセージID
   * @param params パラメーター
   * @return メッセージ
   */
  public static String getMessage(MessageId messageId, String... params) {
    return MessageFormat.format(messages.getProperty(messageId.getId()), (Object[])params);
  }

}
