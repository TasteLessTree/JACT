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
  private String[] args;
  private CommandBuilder builder;
  private CommandRunner runner;
  private List<String> commands;
  private BuildConfig config;

  public CLIRunner(String[] args, Enviroment env, Interpreter interpreter, BuildConfig config) {
    this.args = args;
    this.runner = new CommandRunner(env);
    this.commands = new ArrayList<>();
    this.config = config;

    if (env.isUnix()) {
      this.builder = new UnixCommandBuilder(config);
    } else {
      this.builder = new WindowsCommandBuilder(config);
    }
  }

  public void execute() throws CLIException {
    if (args.length == 0) {
      runNoArguments();
    }

    for (String arg : args) {
      runArgument(arg);
    }
  }

  private void runNoArguments() throws CLIException {
    try {
      commands.clear();
      commands = builder.buildToCompile();
      runner.executeCompiler(commands);
    } catch (CommandRunnerException e) {
      System.err.println(e.getMessage());
    }
  }

  private void runArgument(String arg) throws CLIException {
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
                  "Executable does not exists.",
                  "\n",
                  TagGenerator.generateTag(TagType.DEBUG),
                  "Try running 'jact build'."
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

        case "-h":
        case "--help":
          helpCommand();
          break;

        case "--check-config":
          config.checkConfiguration();
          break;

        default:
          throw new CLIException(
              StringConcat.concat(
                "Unknow keyword: '",
                arg,
                "'.",
                "\n",
                "Try running 'jact --help'."
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

    System.out.println("Usage: jact [command] [arguments]");
    System.out.println();

    System.out.println("Options:");
    System.out.println("  -h, --help: prints this guide.");
    System.out.println("  --check-config: checks the 'project.jact' config file and prints whether or not there are empty fields.");
    System.out.println();

    System.out.println("Commands:");
    System.out.println("  jact: searches for the config file 'project.jact' and compiles the code.");
    System.out.println("  build: searches for the config file 'project.jact' and compiles the code (same as running 'jact' without arguments).");
    System.out.println("  clean: removes the execuatable file.");
    System.out.println("  run: runs the execuatable file.");
    System.out.println("  debug: prints extra information about the commands being run.");
    System.out.println();

    System.out.println("Commands can be concatenated:");
    System.out.println("  jact <clean> <build> <run>");
    System.out.println();
  }

  private void debugCommand() {
    System.out.println(
        StringConcat.concat(
          TagGenerator.generateTag(TagType.DEBUG),
          "JACT arguments ->",
          " ",
          argsToString()
          )
        );

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
          "\n",
          builder.getConfig().toString()
          )
        );
  }

  private String argsToString() {
    if (args.length == 0) {
      return "Running JACT without arguments";
    }

    String arguments = "[ ";

    for (int i = 0; i < getArgs().length; i++) {
      arguments += StringConcat.concat(
          getArgs()[i],
          " "
          );
    }

    arguments += "]";

    return arguments;
  }

  public String[] getArgs() {
      return args;
  }
}
