package jp.co.sample.rest.framework.message;

/**
 * メッセージID.
 */
public enum MessageId {

  // F～ FRAMEWORK
  /** Beanの変換({0} -> {1})に失敗しました。. */
  F0001E,
  /** 設定ファイル[{0}]を読み込みました。. */
  F0002I,
  /** 設定ファイル[{0}]の読み込みに失敗しました。読み込みをSkipします。. */
  F0003W,
  /** active profile is [{0}]. */
  F0004I,
  /** システム日付（みなし日付）を[{0}]に設定しました。. */
  F0005I,
  /** メッセージプロパティ[language:{0}]を読み込みました。. */
  F0006I,
  // C～ COMMON
  // B～ BUSINESS LOGIC
  // P～ PRESENTATION
  ;

}
