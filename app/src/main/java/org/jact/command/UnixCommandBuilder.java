package org.jact.command;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jact.enviroment.BuildConfig;
import org.jact.util.StringConcat;

public class UnixCommandBuilder extends CommandBuilder {
  public UnixCommandBuilder(BuildConfig config) {
    this.config = config;
  }

  // Builds a command like: gcc src/main.c src/game.c src/player.c -o bin/game -Iinclude -Wall -lm
  // gcc -> compiler
  // src/*.c -> source files
  // -o -> added by us
  // bin -> output
  // game -> project
  // -Iinclude -> includes
  // -Wall -> c flag
  // -lm -> ld flag
  @Override
  public List<String> buildToCompile() {
    List<String> commands = new ArrayList<>();

    commands.addFirst(config.getCompiler());

    commands.addAll(config.getSources());

    // Create output directory if it does not exist
    if (!new File(config.getOutput()).exists()) {
      new File(config.getOutput()).mkdir();
    }

    commands.add("-o");
    commands.add(
        StringConcat.concat(
          config.getOutput(),
          "/",
          config.getProject()
          )
        );

    commands.addAll(config.getIncludes());

    commands.addAll(config.getCflags());

    commands.addAll(config.getLdflags());

    commands.addAll(config.getLinks());

    return commands;
  }

  // For example: ./bin/game
  @Override
  public List<String> buildToExecute() {
    List<String> commands = new ArrayList<>();

    commands.add(
        StringConcat.concat(
          "./",
          config.getOutput(),
          "/",
          config.getProject()
          )
        );

    return commands;
  }

  // For example: rm /bin/game
  @Override
  public List<String> buildToClean() {
    String pathToExecutable = StringConcat.concat(
        config.getOutput(),
        "/",
        config.getProject()
        );

    if (!new File(pathToExecutable).exists()) {
      return null;
    }

    List<String> commands = new ArrayList<>();

    commands.addFirst("rm");

    commands.add(pathToExecutable);

    return commands;
  }
}
