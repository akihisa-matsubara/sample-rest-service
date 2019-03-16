package jp.co.jaxrs.framework.util;

import jp.co.jaxrs.framework.message.MessageId;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

/**
 * メッセージ・ユーティリティー.
 */
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
    } catch (IOException e) {
      throw new ExceptionInInitializerError("メッセージプロパティファイルの読み込みが失敗しました。");
    }
  }

  /**
   * デフォルトコンストラクタ.
   */
  private MessageUtils() {
    throw new IllegalStateException("Utility class");
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
