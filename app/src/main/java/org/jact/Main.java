package org.jact;

import java.util.List;

import org.jact.ast.ASTNode;
import org.jact.lexer.Lexer;
import org.jact.lexer.Token;
import org.jact.parser.Parser;

public class Main {
  public static void main(String[] args) {
    Lexer lexer = new Lexer();
    List<Token> tokens = lexer.tokenize("app/src/main/resources/project.jact");

    Parser parser = new Parser(tokens);
    ASTNode ast = parser.ASTBuilder();

    parser.printAST(ast, 0);
  }
}
