package org.jact;

import java.util.List;

import org.jact.ast.ASTNode;
import org.jact.command.CommandBuilder;
import org.jact.command.CommandRunner;
import org.jact.enviroment.BuildConfig;
import org.jact.enviroment.Enviroment;
import org.jact.exceptions.CommandRunnerException;
import org.jact.files.ProjectExplorer;
import org.jact.interpreter.Interpreter;

public class Main {
  public static void main(String[] args) {
    try {
      Enviroment env = new Enviroment();

      ProjectExplorer explorer = new ProjectExplorer(env);
      ASTNode ast = explorer.generateAST();

      if (ast != null) {
        Interpreter interpreter = new Interpreter(env);
        BuildConfig config = interpreter.evaluateAST(ast);

        CommandBuilder builder = new CommandBuilder(config);
        List<String> commands = builder.build();

        CommandRunner runner = new CommandRunner(env);
        runner.run(commands);
      } else {
        System.err.println("Could not generate AST.");
      }
    } catch (CommandRunnerException e) {
      System.err.println(e.getMessage());
    }
  }
}
