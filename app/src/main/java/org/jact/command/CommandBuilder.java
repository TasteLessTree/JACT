package org.jact.command;

import java.util.List;

import org.jact.enviroment.BuildConfig;

public abstract class CommandBuilder {
  protected BuildConfig config;

  // gcc src/main.c src/game.c src/player.c -o bin/game -Iinclude -Wall -lm
  public abstract List<String> buildToCompile();

  // ./bin/game
  public abstract List<String> buildToExecute();

  // rm /bin/game
  public abstract List<String> buildToClean();

  public BuildConfig getConfig() {
    return config;
  }

  public void setConfig(BuildConfig config) {
    this.config = config;
  }
}
