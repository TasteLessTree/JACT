package org.jact.files;

import java.util.List;

import org.jact.ast.ASTNode;
import org.jact.enviroment.Enviroment;
import org.jact.exceptions.LexerException;
import org.jact.exceptions.ParserException;
import org.jact.lexer.Lexer;
import org.jact.lexer.Token;
import org.jact.parser.Parser;
import org.jact.util.StringConcat;

public class ProjectExplorer {
  private Enviroment env;

  public ProjectExplorer(Enviroment env) {
    this.env = env;
  }

  public ASTNode generateAST() {
    try {
      String configFilePath = env.getConfigFilePath();

      if (configFilePath == null) {
        System.err.println(StringConcat.concat("Could not find configuration file ('project.jact') on this project: '", env.getProjectPath(), "'."));
        System.exit(1);
      }

      Lexer lexer = new Lexer();
      List<Token> tokens = lexer.tokenize(configFilePath);
      Parser parser = new Parser(tokens);

      return parser.ASTBuilder();
    } catch (LexerException lexerE) {
      System.err.println(lexerE.getMessage());
      return null;
    } catch (ParserException parserE) {
      System.err.println(parserE.getMessage());
      return null;
    }
  }

  public Enviroment getEnv() {
    return env;
  }
}
