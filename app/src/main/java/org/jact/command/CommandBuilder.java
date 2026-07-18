package org.jact.command;

import java.util.ArrayList;
import java.util.List;

import org.jact.enviroment.BuildConfig;
import org.jact.util.StringConcat;

public class CommandBuilder {
  private BuildConfig config;

  public CommandBuilder(BuildConfig config) {
    this.config = config;
  }

  // Build a commands like: gcc src/main.c src/game.c src/player.c -o bin/game -Iinclude -Wall -lm
  // gcc -> compiler
  // src/*.c -> source files
  // -o -> added by us
  // bin -> output
  // game -> project
  // -Iinclude -> includes
  // -Wall -> c flag
  // -lm -> ld flag
  public List<String> build() {
    List<String> commands = new ArrayList<>();

    commands.addFirst(config.getCompiler());

    commands.addAll(config.getSources());

    commands.add("-o");
    commands.add(StringConcat.concat(config.getOutput(), "/", config.getProject()));

    commands.addAll(config.getIncludes());

    commands.addAll(config.getCflags());

    commands.addAll(config.getLdflags());

    return commands;
  }

  public BuildConfig getConfig() {
      return config;
  }

  public void setConfig(BuildConfig config) {
      this.config = config;
  }
}
