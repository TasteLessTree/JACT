package org.jact.interpreter;

import org.jact.ast.ASTNode;

public class Interpreter {

  public Interpreter() {}

  public void evaluateAST(ASTNode node) {
    switch (node.getType()) {
      case NODE_DSL_PROGRAM:
        if (!node.getChildren().isEmpty()) {
          for (ASTNode kid : node.getChildren()) {
            evaluateAST(kid);
          }
        }
        break;

      case NODE_PROJECT:
        break;

      case NODE_COMPILER:
        break;

      case NODE_SOURCES:
        if (!node.getChildren().isEmpty()) {
          for (ASTNode kid : node.getChildren()) {
            evaluateAST(kid);
          }
        }
        break;

      case NODE_SOURCE:
        break;

      case NODE_INCLUDES:
        if (!node.getChildren().isEmpty()) {
          for (ASTNode kid : node.getChildren()) {
            evaluateAST(kid);
          }
        }
        break;

      case NODE_INCLUDE:
        break;

      case NODE_OUTPUT:
        break;

      case NODE_TARGET:
        break;

      case NODE_CFLAGS:
        if (!node.getChildren().isEmpty()) {
          for (ASTNode kid : node.getChildren()) {
            evaluateAST(kid);
          }
        }
        break;

      case NODE_CFLAG:
        break;

      case NODE_LDFLAGS:
        if (!node.getChildren().isEmpty()) {
          for (ASTNode kid : node.getChildren()) {
            evaluateAST(kid);
          }
        }
        break;

      case NODE_LDFLAG:
        break;
    }
  }
}
