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
  private Enviroment env;
  private Interpreter interpreter;
  private BuildConfig config;
  private String word;

  public CLIRunner(Enviroment env, Interpreter interpreter, BuildConfig config) {
    this.env = env;
    this.interpreter = interpreter;
    this.config = config;
  }

  public void runCLICommand(CLICommandType command) throws CLIException {
    try {
      CommandBuilder builder = new CommandBuilder(config);
      CommandRunner runner = new CommandRunner(env);

      switch (command) {
        case CLI_CLEAR:
          break;

        case CLI_MAKE:
        case CLI_NO_ARGUMENTS:
          List<String> commands = builder.build();
          runner.run(commands);
          break;

        case CLI_RUN:
          break;

        case CLI_DEBUG:
          break;

        case CLI_HELP:
          helpCommand();
          break;

        case CLI_UNKNOWN:
          throw new CLIException(StringConcat.concat("Unknow keyword: '" , word, "'."));
      }
    } catch (CommandRunnerException e) {
      System.err.println(e.getMessage());
    }
  }

  private void helpCommand() {
    System.out.println("\t\t[Java Automated Compiler Toolkit]\n");
    System.out.println("JACT is a simple tool to build simple C proyects!");
    System.out.println();

    System.out.println("Usage:");
    System.out.println("\tjact: searcher for the config file 'project.jact' and compiles the code.");
    System.out.println("\tjact <make>: searcher for the config file 'project.jact' and compiles the code.");
    System.out.println("\tjact <clean>: removes the execuatable file.");
    System.out.println("\tjact <run>: runs the execuatable file.");
    System.out.println("\tjact <debug>: prints extra information about the config file and commands being run.");
    System.out.println("\tjact <help>: prints this guide.");
    System.out.println();

    System.out.println("Commands can be concatenated:");
    System.out.println("\t jact <clean> <make> <run>");
  }

  public void setWord(String word) {
    this.word = word;
  }
}
