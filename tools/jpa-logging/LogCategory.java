package jp.co.jaxrs.framework.log.persistence;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.logging.SessionLog;

/**
 * ログカテゴリー.
 */
@AllArgsConstructor
@Getter
public enum LogCategory {

  /** ALL. */
  ALL((byte) 0x00, "all"),
  /** CACHE. */
  CACHE((byte) 0x01, SessionLog.CACHE),
  /** CONNECTION. */
  CONNECTION((byte) 0x02, SessionLog.CONNECTION),
  /** DDL. */
  DDL((byte) 0x03, SessionLog.DDL),
  /** DMS. */
  DMS((byte) 0x04, SessionLog.DMS),
  /** EJB. */
  EJB((byte) 0x05, SessionLog.EJB),
  /** EVENT. */
  EVENT((byte) 0x06, SessionLog.EVENT),
  /** JPA. */
  JPA((byte) 0x07, SessionLog.JPA),
  /** JPARS. */
  JPARS((byte) 0x08, SessionLog.JPARS),
  /** MEATADATA. */
  METADATA((byte) 0x09, SessionLog.METADATA),
  /** METAMODEL. */
  METAMODEL((byte) 0x0A, SessionLog.METAMODEL),
  /** MISC. */
  MISC((byte) 0x0B, SessionLog.MISC),
  /** MONITORING. */
  MONITORING((byte) 0x0C, SessionLog.MONITORING),
  /** PROPAGATION. */
  PROPAGATION((byte) 0x0D, SessionLog.PROPAGATION),
  /** PROPERTIES. */
  PROPERTIES((byte) 0x0E, SessionLog.PROPERTIES),
  /** QUERY. */
  QUERY((byte) 0x0F, SessionLog.QUERY),
  /** SEQUENCING. */
  SEQUENCING((byte) 0x10, SessionLog.SEQUENCING),
  /** SERVER. */
  SERVER((byte) 0x11, SessionLog.SERVER),
  /** SQL. */
  SQL((byte) 0x12, SessionLog.SQL),
  /** TRANSACTION. */
  TRANSACTION((byte) 0x13, SessionLog.TRANSACTION),
  /** WEAVER. */
  WEAVER((byte) 0x14, SessionLog.WEAVER),
  ;

  /** Logging categories enumeration length. */
  public static final int LENGTH = LogCategory.values().length;

  /** Logger name spaces prefix. */
  private static final String NAMESPACE_PREFIX = "eclipselink.logging.";

  /** Map for String to LogCategory case insensitive conversion. */
  private static final Map<String, LogCategory> stringValuesMap = new HashMap<>(2 * LENGTH);

  /** Logger name spaces lookup table. */
  private static final String[] nameSpaces = new String[LENGTH];

  /** Logger name spaces lookup table. */
  private static final String[] levelNameSpaces = new String[LENGTH];

  /** Logging category ID. Continuous integer sequence starting from 0. */
  private final byte id;

  /** Logging category name. */
  private final String name;

  static {
    // Initialize String to LogCategory case insensitive lookup Map.
    for (LogCategory category : LogCategory.values()) {
      stringValuesMap.put(category.name.toLowerCase(), category);
    }
    // Initialize logger name spaces lookup table.
    for (LogCategory category : LogCategory.values()) {
      nameSpaces[category.id] = (NAMESPACE_PREFIX + category.name).intern();
      levelNameSpaces[category.id] = (PersistenceUnitProperties.CATEGORY_LOGGING_LEVEL_ + category.name).intern();
    }
  }

  /**
   * Returns {@link LogCategory} object holding the value of the specified {@link String}.
   *
   * @param name The {@link String} to be parsed.
   * @return {@link LogCategory} object holding the value represented by the string argument or {@code null} when
   *         there exists no corresponding {@link LogCategory} object to provided argument value. {@code null} value
   *         of the string argument is converted to {@code ALL}.
   */
  public static final LogCategory toValue(final String name) {
    return name != null && name.length() > 0 ? stringValuesMap.get(name.toLowerCase()) : ALL;
  }

  /**
   * Get logger name space for this logging category.
   *
   * @return Logger name space for this logging category.
   */
  public String getNameSpace() {
    return nameSpaces[id];
  }

  /**
   * Get log level property name for this logging category.
   *
   * @return Log level property name for this logging category.
   */
  public String getLogLevelProperty() {
    return levelNameSpaces[id];
  }

}
