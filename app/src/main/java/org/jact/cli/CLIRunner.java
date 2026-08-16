package org.jact.cli;

import java.util.ArrayList;
import java.util.List;

import org.jact.command.CommandBuilder;
import org.jact.command.CommandRunner;
import org.jact.exceptions.CommandRunnerException;
import org.jact.exceptions.CLIException;
import org.jact.enviroment.BuildConfig;
import org.jact.enviroment.Enviroment;
import org.jact.interpreter.Interpreter;
import org.jact.util.StringConcat;

public class CLIRunner {
  private CommandBuilder builder;
  private CommandRunner runner;
  private List<String> commands;

  public CLIRunner(Enviroment env, Interpreter interpreter, BuildConfig config) {
    this.builder = new CommandBuilder(config);
    this.runner = new CommandRunner(env);
    this.commands = new ArrayList<>();
  }

  public void runNoArguments() throws CLIException {
    try {
      commands = builder.buildForCompilation();
      runner.run(commands, false);
    } catch (CommandRunnerException e) {
      System.err.println(e.getMessage());
    }
  }

  public void runArgument(String arg) throws CLIException {
    try {
      commands.clear();
      switch (arg.toLowerCase()) {
        case "build":
          commands = builder.buildForCompilation();
          runner.run(commands, false);
          break;

        case "clean":
          break;

        case "run":
          commands = builder.buildForCompilation();
          System.out.println(commands);
          runner.run(commands, false);

          commands = builder.buildForExecution();
          System.out.println(commands);
          runner.run(commands, true);
          break;

        case "debug":
          System.out.println(builder.getConfig());
          break;

        case "help":
          helpCommand();
          break;

        default:
          throw new CLIException(StringConcat.concat("Unknow keyword: '" , arg, "'.", "\n", "Try running 'jact help'."));
      }
    } catch (CommandRunnerException e) {
      System.err.println(e.getMessage());
    }
  }

  private void helpCommand() {
    System.out.println("\t[Java Automated Compiler Toolkit]");
    System.out.println("JACT is a simple tool to build simple C projects!");
    System.out.println();

    System.out.println("Usage:");
    System.out.println("\tjact: searches for the config file 'project.jact' and compiles the code.");
    System.out.println("\tjact <build>: searches for the config file 'project.jact' and compiles the code.");
    System.out.println("\tjact <clean>: removes the execuatable file.");
    System.out.println("\tjact <run>: runs the execuatable file.");
    System.out.println("\tjact <help>: prints this guide.");
    System.out.println("\tjact <debug>: prints extra information about the commands being run.");
    System.out.println();

    System.out.println("Commands can be concatenated:");
    System.out.println("\tjact <clean> <build> <run>");
    System.out.println();
  }
}
