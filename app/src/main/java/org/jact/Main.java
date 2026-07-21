package org.jact;


import org.jact.ast.ASTNode;
import org.jact.cli.CLICommandType;
import org.jact.cli.CLIParser;
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

        if (args.length > 0) {
          for (String arg : args) {
            cliRunner.setWord(arg);
            cliRunner.runCLICommand(CLIParser.checkForCommand(arg));
          }
        } else {
          cliRunner.runCLICommand(CLICommandType.CLI_NO_ARGUMENTS);
        }
      } else {
        System.err.println("Could not generate AST.");
      }
    } catch (CLIException e) {
      System.err.println(e.getMessage());
    }
  }
}
