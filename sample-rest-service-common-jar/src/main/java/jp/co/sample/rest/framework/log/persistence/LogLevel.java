package jp.co.sample.rest.framework.log.persistence;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ログレベル.
 */
@AllArgsConstructor
@Getter
public enum LogLevel {

  /** Log everything. */
  ALL((byte) 0x00, "ALL"),
  /** Finest (the most detailed) logging level. */
  FINEST((byte) 0x01, "FINEST"),
  /** Finer logging level. */
  FINER((byte) 0x02, "FINER"),
  /** Fine logging level. */
  FINE((byte) 0x03, "FINE"),
  /** Configuration information logging level. */
  CONFIG((byte) 0x04, "CONFIG"),
  /** Informational messages. */
  INFO((byte) 0x05, "INFO"),
  /** Exceptions that are not fatal. */
  WARNING((byte) 0x06, "WARNING"),
  /** Fatal exceptions. */
  SEVERE((byte) 0x07, "SEVERE"),
  /** Logging is turned off. */
  OFF((byte) 0x08, "OFF");

  /** Logging levels enumeration length. */
  public static final int LENGTH = LogLevel.values().length;

  /** Map for String to LogLevel case insensitive lookup. */
  private static final Map<String, LogLevel> stringValuesMap = new HashMap<>(2 * LENGTH);

  // Holds value of SessionLog logging levels constants (e.g. ALL, FINES, FINER, ...).
  /** Logging level ID. Continuous integer sequence starting from 0. */
  private final byte id;

  /** Logging level name. */
  private final String name;

  /** Array for id to LogLevel lookup. */
  private static final LogLevel[] idValues = new LogLevel[LENGTH];

  static {
    // Initialize String to LogLevel case insensitive lookup Map.
    for (LogLevel logLevel : LogLevel.values()) {
      stringValuesMap.put(logLevel.name.toUpperCase(), logLevel);
    }
    // Initialize id to LogLevel lookup array.
    for (LogLevel logLevel : LogLevel.values()) {
      idValues[logLevel.id] = logLevel;
    }
  }

  /**
   * Returns {@link LogLevel} object holding the value of the specified {@link String}.
   *
   * @param name The {@link String} to be parsed.
   * @return {@link LogLevel} object holding the value represented by the string argument or {@code null} when
   *         there exists no corresponding {@link LogLevel} object to provided argument value.
   */
  public static final LogLevel toValue(final String name) {
    return name != null ? stringValuesMap.get(name.toUpperCase()) : null;
  }

  /**
   * Returns {@link LogLevel} object holding the value of the specified {@link String}.
   *
   * @param name The {@link String} to be parsed.
   * @param fallBack {@link LogLevel} object to return on ID lookup failure.
   * @return {@link LogLevel} object holding the value represented by the string argument or {@code fallBack} when
   *         there exists no corresponding {@link LogLevel} object to provided argument value.
   */
  public static final LogLevel toValue(final String name, final LogLevel fallBack) {
    if (name != null) {
      final LogLevel level = stringValuesMap.get(name.toUpperCase());
      return level != null ? level : fallBack;
    } else {
      return fallBack;
    }
  }

  /**
   * Returns {@link LogLevel} object holding the value of the specified {@link LogLevel} ID.
   *
   * @param id {@link LogLevel} ID.
   * @return {@link LogLevel} object holding the value represented by the {@code id} argument.
   * @throws IllegalArgumentException when {@link LogLevel} ID is out of valid {@link LogLevel} IDs range.
   */
  public static final LogLevel toValue(final int id) {
    if (id < 0 || id >= LENGTH) {
      throw new IllegalArgumentException(
          "Log level ID " + id + "is out of range <0, " + Integer.toString(LENGTH) + ">.");
    }
    return idValues[id];
  }

  /**
   * Returns {@link LogLevel} object holding the value of the specified {@link LogLevel} ID.
   *
   * @param id {@link LogLevel} ID.
   * @param fallBack {@link LogLevel} object to return on ID lookup failure.
   * @return {@link LogLevel} object holding the value represented by the {@code id} argument or {@code fallBack}
   *         when provided ID is not valid {@link LogLevel} ID.
   * @throws IllegalArgumentException when {@link LogLevel} ID is out of valid {@link LogLevel} IDs range.
   */
  public static final LogLevel toValue(final int id, final LogLevel fallBack) {
    if (id >= 0 && id < LENGTH) {
      return idValues[id];
    }
    return fallBack;
  }

  /**
   * Check if a message of the given level would actually be logged under this logging level.
   *
   * @param level Message logging level.
   * @return Value of {@code true} if the given message logging level will be logged or {@code false} otherwise.
   */
  public boolean shouldLog(final LogLevel level) {
    return this.id <= level.id;
  }

  /**
   * Check if a message of the given level ID would actually be logged under this logging level.
   *
   * @param id Message logging level.
   * @return Value of {@code true} if the given message logging level will be logged or {@code false} otherwise.
   */
  public boolean shouldLog(final byte id) {
    return this.id <= id;
  }

}
