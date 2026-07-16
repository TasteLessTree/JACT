package org.jact;

import org.jact.ast.ASTNode;
import org.jact.enviroment.Enviroment;
import org.jact.files.ProjectExplorer;

public class Main {
  public static void main(String[] args) {
    /*Lexer lexer = new Lexer();
    List<Token> tokens = lexer.tokenize("app/src/main/resources/project.jact");

    Parser parser = new Parser(tokens);
    ASTNode ast = parser.ASTBuilder();

    parser.printAST(ast, 0);*/

    Enviroment env = new Enviroment();

    ProjectExplorer explorer = new ProjectExplorer(env);
    ASTNode ast = explorer.generateAST();
  }
}
