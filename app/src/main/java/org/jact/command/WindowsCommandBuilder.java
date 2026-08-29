
package org.jact.command;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jact.enviroment.BuildConfig;
import org.jact.util.StringConcat;

public class WindowsCommandBuilder extends CommandBuilder {
  public WindowsCommandBuilder(BuildConfig config) {
    this.config = config;
  }

  // gcc.exe src\main.c src\game.c src\player.c -o bin\game.exe -Iinclude -Wall -lm
  @Override
  public List<String> buildToCompile() {
    List<String> commands = new ArrayList<>();

    commands.addFirst(
        StringConcat.concat(
          config.getCompiler(),
          ".exe"
          )
        );

    commands.addAll(config.getSources());

    // Create output directory if it does not exist
    if (!new File(config.getOutput()).exists()) {
      new File(config.getOutput()).mkdir();
    }

    commands.add("-o");
    commands.add(
        StringConcat.concat(
          config.getOutput(),
          "\\",
          config.getProject(),
          ".exe"
          )
        );

    commands.addAll(config.getIncludes());

    commands.addAll(config.getCflags());

    commands.addAll(config.getLdflags());

    commands.addAll(config.getLinks());

    return commands;
  }

  // /bin/game.exe
  @Override
  public List<String> buildToExecute() {
    List<String> commands = new ArrayList<>();

    commands.add(
        StringConcat.concat(
          "\\",
          config.getOutput(),
          "\\",
          config.getProject(),
          ".exe"
          )
        );

    return commands;
  }

  // del /bin/game.exe
  @Override
  public List<String> buildToClean() {
    String pathToExecutable = StringConcat.concat(
        config.getOutput(),
        "\\",
        config.getProject(),
        ".exe"
        );

    if (!new File(pathToExecutable).exists()) {
      return null;
    }

    List<String> commands = new ArrayList<>();

    commands.addFirst("del");

    commands.add(pathToExecutable);

    return commands;
  }
}
