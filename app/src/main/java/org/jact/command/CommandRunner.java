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

  public CommandRunner(Enviroment env) {
    this.env = env;
  }

  public void run(List<String> commands) throws CommandRunnerException {
    try {
      processBuilder(commands);
      Process process = pb.start();

      try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
        String line;
        while ((line = reader.readLine()) != null) {
          System.out.println(line);
        }
      } catch (IOException readerException) {
        throw new CommandRunnerException(StringConcat.concat("Could not read process standard input: '", readerException.getMessage(), "'."));
      }

      try (BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
        String line;
        while ((line = error.readLine()) != null) {
          System.err.println(line);
        }
      } catch (IOException errorException) {
        throw new CommandRunnerException(StringConcat.concat("Could not read process standard error: '", errorException.getMessage(), "'."));
      }

      int exitCode = process.waitFor();
      System.out.println("Compilation finished with exit code: " + exitCode);

    } catch (IOException e) {
        throw new CommandRunnerException(StringConcat.concat("Could not start compilation process: '", e.getMessage(), "'.", "\n", "Have you specified a compiler? Example: \"compiler clang\"."));
    } catch (InterruptedException e) {
        throw new CommandRunnerException(StringConcat.concat("Could not obtain compilation exit code: '", e.getMessage(), "'."));
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
