package jp.co.jaxrs.framework.message;

import lombok.Getter;

/**
 * メッセージID.
 */
@Getter
public enum MessageId {

  // F～ FRAMEWORK
  // C～ COMMON
  // A～ APPLICATION
  ;

  /** メッセージID. */
  private String id;

  /**
   * デフォルトコンストラクタ.
   *
   * @param id ID
   */
  private MessageId(String id) {
    this.id = id;
  }

}
