package jp.co.jaxrs.sample.pres;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * <PRE>
 * サンプルアプリケーション.
 * 本APIの入出力の書式を以下の通りに定義します.
 * </PRE>
 * <table>
 * <tr><th>型</th><th>書式</th></tr>
 * <tr><td>Date</td><td>yyyy-MM-dd</td></tr>
 * <tr><td>TimeStamp</td><td>yyyy-MM-ddTHH:mm:ss.SSS</td></tr>
 * <tr><td>LocalDateTime</td><td>yyyy-MM-ddTHH:mm:ss.SSS</td></tr>
 * </table>
 */
@ApplicationPath("/api/v1/sample")
public class SampleApplication extends Application {
  // nothing
}
