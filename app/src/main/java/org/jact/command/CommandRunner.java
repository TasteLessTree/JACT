package org.jact.command;

import java.util.List;
import java.io.IOException;

import org.jact.enviroment.Enviroment;
import org.jact.exceptions.CommandRunnerException;
import org.jact.util.TagGenerator;
import org.jact.util.TagType;
import org.jact.util.StringConcat;

public class CommandRunner {
  private Enviroment env;
  private ProcessBuilder pb;

  public CommandRunner(Enviroment env) {
    this.env = env;
  }

  public void executeCompiler(List<String> commands) throws CommandRunnerException {
    try {
      processBuilder(commands);

      Process process = pb.start();

      int exitCode = process.waitFor();
      System.out.println(
          StringConcat.concat(
            "\n",
            TagGenerator.generateTag(TagType.JACT),
            "Compilation finished with exit code: ",
            String.valueOf(exitCode)
            )
          );

    } catch (IOException e) {
      throw new CommandRunnerException(
          StringConcat.concat(
            TagGenerator.generateTag(TagType.JACT),
            "Could not start process: '",
            e.getMessage(),
            "'."
            )
          );
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();

      throw new CommandRunnerException(
          StringConcat.concat(
            TagGenerator.generateTag(TagType.JACT),
            "Process was interrupted: '",
            e.getMessage(),
            "'."
            )
          );
    }
  }

  public void executeProgram(List<String> commands) throws CommandRunnerException {
    try {
      processBuilder(commands);

      Process process = pb.start();

      int exitCode = process.waitFor();
      System.out.println(
          StringConcat.concat(
            "\n",
            TagGenerator.generateTag(TagType.JACT),
            "Process finished with exit code: ",
            String.valueOf(exitCode)
            )
          );
    } catch (IOException e) {
      throw new CommandRunnerException(
          StringConcat.concat(
            TagGenerator.generateTag(TagType.JACT),
            "Could not start process: '",
            e.getMessage(),
            "'."
            )
          );
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();

      throw new CommandRunnerException(
          StringConcat.concat(
            TagGenerator.generateTag(TagType.JACT),
            "Process was interrupted: '",
            e.getMessage(),
            "'."
            )
          );
    }
  }

  public void executeClean(List<String> commands) throws CommandRunnerException {
    try {
      processBuilder(commands);

      Process process = pb.start();

      int exitCode = process.waitFor();
      System.out.println(
          StringConcat.concat(
            "\n",
            TagGenerator.generateTag(TagType.JACT),
            "Process finished with exit code: ",
            String.valueOf(exitCode)
            )
          );
    } catch (IOException e) {
      throw new CommandRunnerException(
          StringConcat.concat(
            TagGenerator.generateTag(TagType.JACT),
            "Could not start process: '",
            e.getMessage(),
            "'."
            )
          );
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();

      throw new CommandRunnerException(
          StringConcat.concat(
            TagGenerator.generateTag(TagType.JACT),
            "Process was interrupted: '",
            e.getMessage(),
            "'."
            )
          );
    }
  }

  private void processBuilder(List<String> commands) {
    this.pb = new ProcessBuilder(commands);
      pb.inheritIO();
  }

  public Enviroment getEnv() {
    return env;
  }

  public ProcessBuilder getPb() {
    return pb;
  }
}
