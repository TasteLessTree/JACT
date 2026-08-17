package org.jact.command;

import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jact.enviroment.Enviroment;
import org.jact.exceptions.CommandRunnerException;
import org.jact.util.StringConcat;

public class CommandRunner {
  private Enviroment env;
  private ProcessBuilder pb;

  private static final String ANSI_RESET = "\u001B[0m";
  private static final String ANSI_GREEN = "\u001B[32m";
  private static final String TAG = StringConcat.concat(" [ ", ANSI_GREEN, "JACT", ANSI_RESET, " ] ");

  public CommandRunner(Enviroment env) {
    this.env = env;
  }

  public void executeCompiler(List<String> commands) throws CommandRunnerException {
    try {
      processBuilder(commands);

      Process process = pb.start();

      try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
        String line;
        while ((line = reader.readLine()) != null) {
          System.out.println(line);
        }
      } catch (IOException readerException) {
        throw new CommandRunnerException(
            StringConcat.concat(
              TAG,
              "Could not read process standard input: '",
              readerException.getMessage(),
              "'."
              )
            );
      }

      try (BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
        String line;
        while ((line = error.readLine()) != null) {
          System.err.println(line);
        }
      } catch (IOException errorException) {
        throw new CommandRunnerException(
            StringConcat.concat(
              TAG,
              "Could not read process standard error: '",
              errorException.getMessage(),
              "'."
              )
            );
      }

      int exitCode = process.waitFor();
      System.out.println(
          StringConcat.concat(
            "\n",
            TAG,
            "Compilation finished with exit code: ",
            String.valueOf(exitCode)
            )
          );

    } catch (IOException e) {
      throw new CommandRunnerException(
          StringConcat.concat(
            TAG,
            "Could not start process: '",
            e.getMessage(),
            "'."
            )
          );
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();

      throw new CommandRunnerException(
          StringConcat.concat(
            TAG,
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
      pb.inheritIO();

      Process process = pb.start();

      int exitCode = process.waitFor();
      System.out.println(
          StringConcat.concat(
            "\n",
            TAG,
            "Process finished with exit code: ",
            String.valueOf(exitCode)
            )
          );
    } catch (IOException e) {
      throw new CommandRunnerException(
          StringConcat.concat(
            TAG,
            "Could not start process: '",
            e.getMessage(),
            "'."
            )
          );
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();

      throw new CommandRunnerException(
          StringConcat.concat(
            TAG,
            "Process was interrupted: '",
            e.getMessage(),
            "'."
            )
          );
    }
  }

  private void processBuilder(List<String> commands) {
    this.pb = new ProcessBuilder(commands);
  }

  public Enviroment getEnv() {
    return env;
  }

  public ProcessBuilder getPb() {
    return pb;
  }
}
