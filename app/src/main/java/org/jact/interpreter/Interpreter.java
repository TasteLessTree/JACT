package org.jact.interpreter;

import java.io.File;

import org.jact.ast.ASTNode;
import org.jact.enviroment.Enviroment;
import org.jact.enviroment.BuildConfig;
import org.jact.files.FileFinder;
import org.jact.util.StringConcat;

public class Interpreter {
  private Enviroment env;

  public Interpreter(Enviroment env) {
    this.env = env;
  }

  public BuildConfig evaluateAST(ASTNode node) {
    BuildConfig config = new BuildConfig();

    visit(node, config);

    return config;
  }

  private void visit(ASTNode node, BuildConfig config) {
    switch (node.getType()) {
      case NODE_DSL_PROGRAM:
        visitChildren(node, config);
        break;

      case NODE_PROJECT:
        config.setProject(node.getValue());
        break;

      case NODE_COMPILER:
        config.setCompiler(node.getValue());
        break;

      case NODE_SOURCES:
        visitChildren(node, config);
        break;

      case NODE_SOURCE:
        FileFinder finder = new FileFinder(env);
        File[] sources = finder.filesToFind(node.getValue());

        for (File file : sources) { 
          if (env.isUnix()) {
            config.getSources().add(
                StringConcat.concat(
                  node.getValue(),
                  "/",
                  file.getName()
                  )
                );
          } else {
            config.getSources().add(
                StringConcat.concat(
                  node.getValue(),
                  "\\",
                  file.getName()
                  )
                );
          }
        }
        break;

      case NODE_INCLUDES:
        visitChildren(node, config);
        break;

      case NODE_INCLUDE:
        config.getIncludes().add(
            StringConcat.concat(
              "-I",
              node.getValue()
              )
            );
        break;

      case NODE_OUTPUT:
        config.setOutput(node.getValue());
        break;

      case NODE_TARGET:
        // Right now does nothing as only generates an executable
        config.setTarget(node.getValue());
        break;

      case NODE_CFLAGS:
        visitChildren(node, config);
        break;

      case NODE_CFLAG:
        config.getCflags().add(node.getValue());
        break;

      case NODE_LDFLAGS:
        visitChildren(node, config);
        break;

      case NODE_LDFLAG:
        config.getLdflags().add(node.getValue());
        break;
    }
  }

  private void visitChildren(ASTNode node, BuildConfig config) {
    if (!node.getChildren().isEmpty()) {
      for (ASTNode child : node.getChildren()) {
        visit(child, config);
      }
    }
  }
}
