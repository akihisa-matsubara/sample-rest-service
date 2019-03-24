package jp.co.jaxrs.framework.log.persistence;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.logging.SessionLog;

public enum LogCategory {
  ALL((byte) 0x00, "all"),
  CACHE((byte) 0x01, SessionLog.CACHE),
  CONNECTION((byte) 0x02, SessionLog.CONNECTION),
  DDL((byte) 0x03, SessionLog.DDL),
  DMS((byte) 0x04, SessionLog.DMS),
  EJB((byte) 0x05, SessionLog.EJB),
  EVENT((byte) 0x06, SessionLog.EVENT),
  JPA((byte) 0x07, SessionLog.JPA),
  JPARS((byte) 0x08, SessionLog.JPARS),
  METADATA((byte) 0x09, SessionLog.METADATA),
  METAMODEL((byte) 0x0A, SessionLog.METAMODEL),
  MISC((byte) 0x0B, SessionLog.MISC),
  MONITORING((byte) 0x0C, SessionLog.MONITORING),
  PROPAGATION((byte) 0x0D, SessionLog.PROPAGATION),
  PROPERTIES((byte) 0x0E, SessionLog.PROPERTIES),
  QUERY((byte) 0x0F, SessionLog.QUERY),
  SEQUENCING((byte) 0x10, SessionLog.SEQUENCING),
  SERVER((byte) 0x11, SessionLog.SERVER),
  SQL((byte) 0x12, SessionLog.SQL),
  TRANSACTION((byte) 0x13, SessionLog.TRANSACTION),
  WEAVER((byte) 0x14, SessionLog.WEAVER),
  ;

  /** Logging categories enumeration length. */
  public static final int length = LogCategory.values().length;

  /** Logger name spaces prefix. */
  private static final String NAMESPACE_PREFIX = "eclipselink.logging.";

  /** {@link Map} for {@link String} to {@link LogCategory} case insensitive conversion. */
  private static final Map<String, LogCategory> stringValuesMap = new HashMap<String, LogCategory>(2 * length);

  /** Logger name spaces lookup table. */
  private static final String[] nameSpaces = new String[length];

  /** Logger name spaces lookup table. */
  private static final String[] levelNameSpaces = new String[length];

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

  /** Logging category ID. Continuous integer sequence starting from 0. */
  private final byte id;

  /** Logging category name. */
  private final String name;

  /**
   * Creates an instance of logging category.
   *
   * @param id Logging category ID.
   * @param name Logging category name.
   */
  private LogCategory(final byte id, final String name) {
    this.id = id;
    this.name = name;
  }

  /**
   * Get logging category ID.
   *
   * @return Logging category ID.
   */
  public byte getId() {
    return id;
  }

  /**
   * Get logging category name.
   *
   * @return Logging category name.
   */
  public String getName() {
    return name;
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
