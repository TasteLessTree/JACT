package org.jact.cli;

public final class CLIParser {
  private CLIParser() {}

  public static CLICommandType checkForCommand(String word) {
    if (word.equalsIgnoreCase("clear")) {
      return CLICommandType.CLI_CLEAR;
    } else if (word.equalsIgnoreCase("make")) {
      return CLICommandType.CLI_MAKE;
    } else if (word.equalsIgnoreCase("run")) {
      return CLICommandType.CLI_RUN;
    } else if (word.equalsIgnoreCase("help")) {
      return CLICommandType.CLI_HELP;
    } else if (word.equalsIgnoreCase("debug")) {
      return CLICommandType.CLI_DEBUG;
    }
    return CLICommandType.CLI_UNKNOWN;
  }
}
