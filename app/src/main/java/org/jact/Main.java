package org.jact;

import java.util.List;

import org.jact.lexer.Lexer;
import org.jact.lexer.Token;

public class Main {
  public static void main(String[] args) {
    try {
      Lexer lexer = new Lexer();
      List<Token> tokens = lexer.tokenize("app/src/main/resources/project.jact");

      for (Token token : tokens) {
        System.out.println(token);
      }
    } catch (RuntimeException e) {
      System.err.println("The Lexer has produced an error.\n");
      System.err.println(e.getMessage());
    }
  }
}
