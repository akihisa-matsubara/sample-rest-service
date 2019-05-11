package jp.co.sample.rest.test.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

/**
 * Arquillian テスト用 ユーティリティー.
 */
@UtilityClass
@Slf4j
public class ArquillianTestUtils {

  /**
   * テスト用のwarを作成します.
   *
   * @param baseClass パッケージ取得元のクラス
   * @return {@link WebArchive}
   */
  public static WebArchive createWebArchive(Class<?> baseClass) {
    WebArchive war = ShrinkWrap.create(WebArchive.class, baseClass.getSimpleName() + ".war")
        .addPackage(baseClass.getPackage());

    log.debug("create deployment war archve. {}", war.toString(true));

    return war;
  }

}
