package jp.co.sample.rest.framework.util;

import jp.co.sample.common.constant.SystemProperty;
import jp.co.sample.rest.framework.exception.dto.ErrorMessage;
import jp.co.sample.rest.framework.message.MessageId;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * メッセージ・ユーティリティー.
 */
@UtilityClass
@Slf4j
public class MessageUtils {

  /** メッセージ基底名. */
  private static final String MESSAGE_BASE_NAME = "messages";

  /** メッセージプロパティ. */
  private static ResourceBundle messages = null;

  static {
    try {
      // JVMにキャッシュされるのでクリア
      ResourceBundle.clearCache(MessageUtils.class.getClassLoader());

      // Localeは環境依存
      messages = ResourceBundle.getBundle(MESSAGE_BASE_NAME);
      log.info(MessageUtils.getMessage(MessageId.F0006I, SystemProperty.LANGUAGE));

    } catch (MissingResourceException mre) {
      throw new ExceptionInInitializerError("メッセージプロパティファイルの読み込みに失敗しました。");

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
    return MessageFormat.format(messages.getString(messageId.name()), (Object[])params);
  }

  /**
   * エラーメッセージを取得します.
   *
   * @param errorDto エラーDto
   * @return メッセージ
   */
  public static String getErrorMessage(ErrorMessage errorDto) {
    return getMessage(errorDto.getMessageId(), errorDto.getParams());
  }

}
