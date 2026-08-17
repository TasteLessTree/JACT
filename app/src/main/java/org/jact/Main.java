package org.jact;

import org.jact.ast.ASTNode;
import org.jact.cli.CLIRunner;
import org.jact.enviroment.BuildConfig;
import org.jact.enviroment.Enviroment;
import org.jact.exceptions.CLIException;
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

        CLIRunner cliRunner = new CLIRunner(env, interpreter, config);

        if (args.length == 0) {
          cliRunner.runNoArguments();
        } else {
          for (String arg : args) {
            cliRunner.runArgument(arg);
          }
        }

      } else {
        System.err.println("Could not generate AST from 'project.jact' file.");
      }
    } catch (CLIException e) {
      System.err.println(e.getMessage());
    }
  }
}
