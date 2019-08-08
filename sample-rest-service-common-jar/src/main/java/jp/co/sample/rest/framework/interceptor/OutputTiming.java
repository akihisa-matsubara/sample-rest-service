package jp.co.sample.rest.framework.interceptor;

/**
 * ログ出力タイミング.
 */
public enum OutputTiming {
  /** 開始時のみ. */
  BEGIN,
  /** 終了時のみ. */
  COMPLETE,
  /** 開始時+終了時. */
  BOTH,
  ;
}
