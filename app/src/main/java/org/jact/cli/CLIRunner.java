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

  private static final String ANSI_RESET = "\u001B[0m";
  private static final String ANSI_YELLOW = "\u001B[33m";
  private static final String TAG = StringConcat.concat(" [ ", ANSI_YELLOW, "DEBUG", ANSI_RESET, " ] ");

  public CLIRunner(Enviroment env, Interpreter interpreter, BuildConfig config) {
    this.builder = new CommandBuilder(config);
    this.runner = new CommandRunner(env);
    this.commands = new ArrayList<>();
  }

  public void runNoArguments() throws CLIException {
    try {
      commands = builder.buildToCompile();
      runner.executeCompiler(commands);
    } catch (CommandRunnerException e) {
      System.err.println(e.getMessage());
    }
  }

  public void runArgument(String arg) throws CLIException {
    try {
      commands.clear();
      switch (arg.toLowerCase()) {
        case "build":
          commands = builder.buildToCompile();
          runner.executeCompiler(commands);
          break;

        case "clean":
          break;

        case "run":
          commands = builder.buildToCompile();
          runner.executeCompiler(commands);
          System.out.println();

          commands = builder.buildToExecute();
          runner.executeProgram(commands);
          break;

        case "debug":
          System.out.println(
              StringConcat.concat(
                TAG,
                "Compilation ->",
                " ",
                builder.buildToCompile().toString()
                )
              );

          System.out.println(
              StringConcat.concat(
                TAG,
                "Execution ->",
                " ",
                builder.buildToExecute().toString()
                )
              );

          System.out.println(
              StringConcat.concat(
                TAG,
                "Clean ->",
                " ",
                "TODO"
                )
              );

          System.out.println(
              StringConcat.concat(
                TAG,
                builder.getConfig().toString()
                )
              );
          break;

        case "help":
          helpCommand();
          break;

        default:
          throw new CLIException(
              StringConcat.concat(
                "Unknow keyword: '",
                arg,
                "'.",
                "\n",
                "Try running 'jact help'."
                )
              );
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
