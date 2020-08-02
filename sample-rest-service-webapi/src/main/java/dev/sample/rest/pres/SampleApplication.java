package dev.sample.rest.pres;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

// @formatter:off
/**
 * <PRE>
 * サンプルアプリケーション.
 * 本APIの入出力の書式を以下の通りに定義します.
 * </PRE>
 * <table>
 * <tr><th>型</th><th>書式</th></tr>
 * <tr><td>{@code Date}</td><td>yyyy-MM-dd</td></tr>
 * <tr><td>{@code TimeStamp}</td><td>yyyy-MM-dd'T'HH:mm:ss.SSS</td></tr>
 * <tr><td>{@code LocalDate}</td><td>yyyy-MM-dd</td></tr>
 * <tr><td>{@code LocalDateTime}</td><td>yyyy-MM-dd'T'HH:mm:ss.SSS</td></tr>
 * </table>
 */
// @formatter:on
@ApplicationPath("/api/v1/sample")
public class SampleApplication extends Application {
  // nothing
}
