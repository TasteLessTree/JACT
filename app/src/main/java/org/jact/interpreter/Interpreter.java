package org.jact.interpreter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jact.ast.ASTNode;
import org.jact.command.CommandRunner;
import org.jact.enviroment.Enviroment;
import org.jact.files.FileFinder;
import org.jact.util.StringConcat;

public class Interpreter {
  private Enviroment env;
  List<String> commands;

  public Interpreter(Enviroment env) {
    this.env = env;
    this.commands = new ArrayList<String>();
  }

  // Build a commands like: gcc src/main.c src/game.c src/player.c -Wall -Iinclude -lm -o bin/game
  // gcc -> compiler
  // src/*.c -> source files
  // -Wall -> c flag
  // -Iinclude -> includes
  // -lm -> ld flag
  // // -o -> added by us
  // bin -> output
  // game -> project
  public void evaluateAST(ASTNode node) {
    switch (node.getType()) {
      case NODE_DSL_PROGRAM:
        if (!node.getChildren().isEmpty()) {
          for (ASTNode child : node.getChildren()) {
            evaluateAST(child);
          }
        }
        break;

      case NODE_PROJECT:
        commands.add(node.getValue());
        break;

      case NODE_COMPILER:
        commands.addFirst(node.getValue());
        break;

      case NODE_SOURCES:
        if (!node.getChildren().isEmpty()) {
          for (ASTNode child : node.getChildren()) {
            evaluateAST(child);
          }
        }
        break;

      case NODE_SOURCE:
        FileFinder finder = new FileFinder(env);
        File[] sources = finder.filesToFind(node.getValue());

        for (File file : sources) {
          commands.add(StringConcat.concat(node.getValue(), "/", file.getName()));
        }
        break;

      case NODE_INCLUDES:
        if (!node.getChildren().isEmpty()) {
          for (ASTNode child : node.getChildren()) {
            evaluateAST(child);
          }
        }
        break;

      case NODE_INCLUDE:
        commands.add(StringConcat.concat("-I", node.getValue()));
        break;

      case NODE_OUTPUT:
        commands.add(node.getValue());
        break;

      case NODE_TARGET:
        // Right now does nothing as only generates an executable
        //commands.add(node.getValue());
        break;

      case NODE_CFLAGS:
        if (!node.getChildren().isEmpty()) {
          for (ASTNode child : node.getChildren()) {
            evaluateAST(child);
          }
        }
        break;

      case NODE_CFLAG:
        commands.add(node.getValue());
        break;

      case NODE_LDFLAGS:
        if (!node.getChildren().isEmpty()) {
          for (ASTNode child : node.getChildren()) {
            evaluateAST(child);
          }
        }
        break;

      case NODE_LDFLAG:
        commands.add(node.getValue());
        break;
    }

    System.out.println(commands);
    /*CommandRunner runner = new CommandRunner(env);
      runner.run(commands);*/
  }

  public Enviroment getEnv() {
    return env;
  }

  public List<String> getCommands() {
    return commands;
  }
}
