package org.jact;

import java.util.List;

import org.jact.lexer.Lexer;
import org.jact.lexer.Token;

public class Main {
  public static void main(String[] args) {
    Lexer lexer = new Lexer();
    List<Token> tokens = lexer.tokenize("app/src/main/resources/project.jact");

    for (Token token : tokens) {
      System.out.println(token);
    }
  }
}
