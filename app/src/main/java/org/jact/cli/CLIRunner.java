package org.jact.cli;

import java.util.ArrayList;
import java.util.List;

import org.jact.command.CommandBuilder;
import org.jact.command.CommandRunner;
import org.jact.command.UnixCommandBuilder;
import org.jact.command.WindowsCommandBuilder;
import org.jact.exceptions.CommandRunnerException;
import org.jact.exceptions.CLIException;
import org.jact.enviroment.BuildConfig;
import org.jact.enviroment.Enviroment;
import org.jact.interpreter.Interpreter;
import org.jact.util.StringConcat;
import org.jact.util.TagGenerator;
import org.jact.util.TagType;

public class CLIRunner {
  private CommandBuilder builder;
  private CommandRunner runner;
  private List<String> commands;

  public CLIRunner(Enviroment env, Interpreter interpreter, BuildConfig config) {
    this.runner = new CommandRunner(env);
    this.commands = new ArrayList<>();

    if (env.isUnix()) {
      this.builder = new UnixCommandBuilder(config);
    } else {
      this.builder = new WindowsCommandBuilder(config);
    }
  }

  public void runNoArguments() throws CLIException {
    try {
      commands.clear();
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
          commands = builder.buildToClean();

          if (commands != null) {
            runner.executeClean(commands);
          } else {
            System.out.println(
                StringConcat.concat(
                  "\n",
                  TagGenerator.generateTag(TagType.DEBUG),
                  "Executable does not exists."
                  )
                );
          }
          break;

        case "run":
          commands = builder.buildToCompile();
          runner.executeCompiler(commands);
          System.out.println();

          commands = builder.buildToExecute();
          runner.executeProgram(commands);
          break;

        case "debug":
          debugCommand();
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

  private void debugCommand() {
    System.out.println(
        StringConcat.concat(
          TagGenerator.generateTag(TagType.DEBUG),
          "Compilation ->",
          " ",
          builder.buildToCompile().toString()
          )
        );

    System.out.println(
        StringConcat.concat(
          TagGenerator.generateTag(TagType.DEBUG),
          "Execution ->",
          " ",
          builder.buildToExecute().toString()
          )
        );

    System.out.println(
        StringConcat.concat(
          TagGenerator.generateTag(TagType.DEBUG),
          "Clean ->",
          " ",
          builder.buildToClean().toString()
          )
        );

    System.out.println(
        StringConcat.concat(
          TagGenerator.generateTag(TagType.DEBUG),
          builder.getConfig().toString()
          )
        );
  }
}
