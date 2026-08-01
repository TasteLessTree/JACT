package org.jact.cli;

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
  }

  public void runNoArguments() throws CLIException {
    try {
      this.commands = builder.buildCompilation();
      runner.run(commands);
    } catch (CommandRunnerException e) {
      System.err.println(e.getMessage());
    }
  }

  public void runArgument(String arg) throws CLIException {
    try {
      switch (arg.toLowerCase()) {
        case "make":
          this.commands = builder.buildCompilation();
          runner.run(commands);
          break;

        case "clean":
          break;

        case "run":
          this.commands = builder.buildExecutable();
          runner.run(commands);
          break;

        case "debug":
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
    System.out.println("\tjact <make>: searches for the config file 'project.jact' and compiles the code.");
    System.out.println("\tjact <clean>: removes the execuatable file.");
    System.out.println("\tjact <run>: runs the execuatable file.");
    System.out.println("\tjact <help>: prints this guide.");
    System.out.println("\tjact <debug>: prints extra information about the config file and commands being run.");
    System.out.println();

    System.out.println("Commands can be concatenated:");
    System.out.println("\t jact <clean> <make> <run>");
    System.out.println();
  }
}
