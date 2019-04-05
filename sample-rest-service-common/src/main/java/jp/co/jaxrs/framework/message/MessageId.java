package jp.co.jaxrs.framework.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * メッセージID.
 */
@AllArgsConstructor
@Getter
public enum MessageId {

  // F～ FRAMEWORK
  /** Beanの変換({0} -> {1})に失敗しました. */
  F0001E("F0001E"),
  // C～ COMMON
  // B～ BUSINESS LOGIC
  // P～ PRESENTATION
  ;

  /** メッセージID. */
  private String id;

}
