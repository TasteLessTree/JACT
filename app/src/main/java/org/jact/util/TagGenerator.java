package org.jact.util;

public final class TagGenerator {
  private static final String ANSI_RESET = "\u001B[0m";
  private static final String ANSI_RED = "\u001B[31m";
  private static final String ANSI_GREEN = "\u001B[32m";
  private static final String ANSI_YELLOW = "\u001B[33m";

  private TagGenerator() {}

  public static String generateTag(TagType type) {
    return StringConcat.concat(
        " ",
        "[",
        " ",
        tagColor(type),
        tagText(type),
        ANSI_RESET,
        " ",
        "]",
        " "
        );
  }

  private static String tagText(TagType type) {
    return switch(type) {
      case JACT -> "JACT";
      case ERROR -> "ERROR";
      case DEBUG -> "DEBUG";
    };
  }

  private static String tagColor(TagType type) {
    return switch(type) {
      case JACT -> ANSI_GREEN;
      case ERROR -> ANSI_RED;
      case DEBUG -> ANSI_YELLOW;
    };
  }
}
