package org.jact.parser;

import java.util.List;

import org.jact.ast.ASTNode;
import org.jact.ast.NodeType;
import org.jact.lexer.Token;
import org.jact.lexer.TokenType;

public class Parser {
  private List<Token> tokens;
  private int position;

  public Parser(List<Token> tokens) {
    this.tokens = tokens;
    this.position = 0;
  }

  public ASTNode ASTBuilder() {
    ASTNode node = new ASTNode(NodeType.DSL_PROGRAM);

    while (tokens.get(position).getType() != TokenType.TYPE_EOF) {
      // If token is sources/includes/cflags/ldflags, a '{' must follow
      // If token is project, a string must follow
      // If we encounter a '{' but, we must check if the previous one has been closed

      Token token = tokens.get(position);

      switch (token.getType()) {
        case TYPE_PROJECT:
          ASTNode projectNode = parseProject(token);
          node.addChildren(projectNode);
          break;

        case TYPE_COMPILER:
          ASTNode compilerNode = parseCompiler(token);
          node.addChildren(compilerNode);
          break;

        case TYPE_SOURCES:
          break;

        case TYPE_INCLUDES:
          break;

        case TYPE_OUTPUT:
          break;

        case TYPE_TARGET:
          break;

        case TYPE_CFLAGS:
          break;

        case TYPE_LDFLAGS:
          break;
      
        default:
          throw new RuntimeException("[ERROR] Unknown token: " + token.toString());
      }
    }

    return node;
  }

  private boolean expectTokenType(TokenType actual, TokenType expected) {
    return actual == expected;
  }

  private ASTNode parseProject(Token current) {
    ASTNode node = new ASTNode(NodeType.PROJECT);
    position++;

    if (expectTokenType(current.getType(), TokenType.TYPE_STRING)) {
      node.setValue(current.getText());
      position++;
    } else {
      throw new RuntimeException("[ERROR] Expected a string, got: '" + current.getType() + "'. Content: '" + current.getText() + "'.");
    }

    return node;
  }

  private ASTNode parseCompiler(Token current) {
    ASTNode node = new ASTNode(NodeType.COMPILER);
    position++;

    if (expectTokenType(current.getType(), TokenType.TYPE_IDENT)) {
      node.setValue(current.getText());
      position++;
    } else {
      throw new RuntimeException("[ERROR] Expected an identifier, got: '" + current.getType() + "'. Content: '" + current.getText() + "'.");
    }

    return node;
  }
}
