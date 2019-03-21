package jp.co.jaxrs.framework.pres.dto;

import jp.co.jaxrs.framework.message.MessageId;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * エラーDto.
 */
@Getter
@Setter
public class ErrorDto implements Serializable {

  /** serialVersionUID. */
  private static final long serialVersionUID = -5529239137876367074L;

  /** メッセージID. */
  private MessageId messageId;

  /** パラメーター. */
  private String[] params;

  /**
   * デフォルトコンストラクター.
   *
   * @param messageId メッセージID
   * @param params パラメータ－
   */
  public ErrorDto(MessageId messageId, String... params) {
    this.messageId = messageId;
    this.params = params;
  }
}