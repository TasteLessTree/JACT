package org.jact.parser;

import java.util.List;

import org.jact.ast.ASTNode;
import org.jact.ast.NodeType;
import org.jact.exceptions.ParserException;
import org.jact.lexer.Token;
import org.jact.lexer.TokenType;
import org.jact.util.StringConcat;

public class Parser {
  private List<Token> tokens;
  private int position;

  public Parser(List<Token> tokens) {
    this.tokens = tokens;
    this.position = 0;
  }

  public ASTNode ASTBuilder() throws ParserException {
    ASTNode node = new ASTNode(NodeType.NODE_DSL_PROGRAM);

    while (tokens.get(position).getType() != TokenType.TYPE_EOF) {
      // If token is sources/includes/cflags/ldflags, a '{' must follow
      // If token is project, a string must follow
      // We must check if the curly braces '{}' are being open and close correctly
      
      switch (tokens.get(position).getType()) {
        case TYPE_PROJECT:
          ASTNode projectNode = parseProject();
          node.addChildren(projectNode);
          break;

        case TYPE_COMPILER:
          ASTNode compilerNode = parseCompiler();
          node.addChildren(compilerNode);
          break;

        case TYPE_SOURCES:
          ASTNode sourcesNode = parseSources();
          node.addChildren(sourcesNode);
          break;

        case TYPE_INCLUDES:
          ASTNode includesNode = parseIncludes();
          node.addChildren(includesNode);
          break;

        case TYPE_OUTPUT:
          ASTNode outputNode = parseOutput();
          node.addChildren(outputNode);
          break;

        case TYPE_TARGET:
          ASTNode targetNode = parseTarget();
          node.addChildren(targetNode);
          break;

        case TYPE_CFLAGS:
          ASTNode CFlagsNode = parseCFlags();
          node.addChildren(CFlagsNode);
          break;

        case TYPE_LDFLAGS:
          ASTNode LDFlagsNode = parseLDFlags();
          node.addChildren(LDFlagsNode);
          break;
      
        default:
          throw new ParserException(StringConcat.concat("Unexpected token, unknown keyword: '", tokens.get(position).getText(), "'. Line: ", String.valueOf(tokens.get(position).getLineNumber()), ". Column: ", String.valueOf(tokens.get(position).getColumn()), "."));
      }
    }

    return node;
  }

  private boolean expectTokenType(TokenType actual, TokenType expected) {
    return actual == expected;
  }

  private ASTNode parseProject() throws ParserException {
    ASTNode node = new ASTNode(NodeType.NODE_PROJECT);
    position++;

    Token current = tokens.get(position);
    if (expectTokenType(current.getType(), TokenType.TYPE_STRING)) {
      node.setValue(current.getText());
      position++;
    } else {
      throw new ParserException(StringConcat.concat("Expected a string, got: '", current.getType().toString(), "'. Line: ", String.valueOf(current.getLineNumber()), ". Column: ", String.valueOf(current.getColumn()), "."));
    }

    return node;
  }

  private ASTNode parseCompiler() throws ParserException {
    ASTNode node = new ASTNode(NodeType.NODE_COMPILER);
    position++;

    Token current = tokens.get(position);
    if (expectTokenType(current.getType(), TokenType.TYPE_IDENT)) {
      node.setValue(current.getText());
      position++;
    } else {
      throw new ParserException(StringConcat.concat("Expected an identifier, got: '", current.getType().toString(), "'. Line: ", String.valueOf(current.getLineNumber()), ". Column: ", String.valueOf(current.getColumn()), "."));
    }

    return node;
  }

  private ASTNode parseOutput() throws ParserException {
    ASTNode node = new ASTNode(NodeType.NODE_OUTPUT);
    position++;

    Token current = tokens.get(position);
    if (expectTokenType(current.getType(), TokenType.TYPE_IDENT)) {
      node.setValue(current.getText());
      position++;
    } else {
      throw new ParserException(StringConcat.concat("Expected an identifier, got: '", current.getType().toString(), "'. Line: ", String.valueOf(current.getLineNumber()), ". Column: ", String.valueOf(current.getColumn()), "."));
    }

    return node;
  }

  private ASTNode parseTarget() throws ParserException {
    ASTNode node = new ASTNode(NodeType.NODE_TARGET);
    position++;

    Token current = tokens.get(position);
    if (expectTokenType(current.getType(), TokenType.TYPE_IDENT)) {
      node.setValue(current.getText());
      position++;
    } else {
      throw new ParserException(StringConcat.concat("Expected an identifier, got: '", current.getType().toString(), "'. Line: ", String.valueOf(current.getLineNumber()), ". Column: ", String.valueOf(current.getColumn()), "."));
    }

    return node;
  }

  private ASTNode parseSources() throws ParserException {
    ASTNode node = new ASTNode(NodeType.NODE_SOURCES);
    position++;

    Token current = tokens.get(position);
    if (expectTokenType(current.getType(), TokenType.TYPE_OPEN_CBRACE)) {
      position++;
      current = tokens.get(position);

      while (current.getType() != TokenType.TYPE_CLOSE_CBRACE) {
        ASTNode ident = new ASTNode(NodeType.NODE_SOURCE);

        if (expectTokenType(current.getType(), TokenType.TYPE_IDENT)) {
          ident.setValue(current.getText());
          node.addChildren(ident);

          position++;
          current = tokens.get(position);
        } else {
      throw new ParserException(StringConcat.concat("Expected a closed curly brace '}' at the end of block 'sources', got: '", current.getType().toString(), "'. Line: ", String.valueOf(current.getLineNumber()), ". Column: ", String.valueOf(current.getColumn()), "."));
        }
      }
      position++;
    } else {
      throw new ParserException(StringConcat.concat("Expected an open curly brace '{', got: '", current.getType().toString(), "'. Line: ", String.valueOf(current.getLineNumber()), ". Column: ", String.valueOf(current.getColumn()), "."));
    }

    return node;
  }

  private ASTNode parseIncludes() throws ParserException {
    ASTNode node = new ASTNode(NodeType.NODE_INCLUDES);
    position++;

    Token current = tokens.get(position);
    if (expectTokenType(current.getType(), TokenType.TYPE_OPEN_CBRACE)) {
      position++;
      current = tokens.get(position);

      while (current.getType() != TokenType.TYPE_CLOSE_CBRACE) {
        ASTNode ident = new ASTNode(NodeType.NODE_INCLUDE);

        if (expectTokenType(current.getType(), TokenType.TYPE_IDENT)) {
          ident.setValue(current.getText());
          node.addChildren(ident);

          position++;
          current = tokens.get(position);
        } else {
      throw new ParserException(StringConcat.concat("Expected a closed curly brace '}' at the end of block 'includes', got: '", current.getType().toString(), "'. Line: ", String.valueOf(current.getLineNumber()), ". Column: ", String.valueOf(current.getColumn()), "."));
        }
      }
      position++;
    } else {
      throw new ParserException(StringConcat.concat("Expected an open curly brace '{', got: '", current.getType().toString(), "'. Line: ", String.valueOf(current.getLineNumber()), ". Column: ", String.valueOf(current.getColumn()), "."));
    }

    return node;
  }

  private ASTNode parseCFlags() throws ParserException {
    ASTNode node = new ASTNode(NodeType.NODE_CFLAGS);
    position++;

    Token current = tokens.get(position);
    if (expectTokenType(current.getType(), TokenType.TYPE_OPEN_CBRACE)) {
      position++;
      current = tokens.get(position);

      while (current.getType() != TokenType.TYPE_CLOSE_CBRACE) {
        ASTNode ident = new ASTNode(NodeType.NODE_CFLAG);

        if (expectTokenType(current.getType(), TokenType.TYPE_IDENT)) {
          ident.setValue(current.getText());
          node.addChildren(ident);

          position++;
          current = tokens.get(position);
        } else {
      throw new ParserException(StringConcat.concat("Expected a closed curly brace '}' at the end of block 'cflags', got: '", current.getType().toString(), "'. Line: ", String.valueOf(current.getLineNumber()), ". Column: ", String.valueOf(current.getColumn()), "."));
        }
      }
      position++;
    } else {
      throw new ParserException(StringConcat.concat("Expected an open curly brace '{', got: '", current.getType().toString(), "'. Line: ", String.valueOf(current.getLineNumber()), ". Column: ", String.valueOf(current.getColumn()), "."));
    }

    return node;
  }

  private ASTNode parseLDFlags() throws ParserException {
    ASTNode node = new ASTNode(NodeType.NODE_LDFLAGS);
    position++;

    Token current = tokens.get(position);
    if (expectTokenType(current.getType(), TokenType.TYPE_OPEN_CBRACE)) {
      position++;
      current = tokens.get(position);

      while (current.getType() != TokenType.TYPE_CLOSE_CBRACE) {
        ASTNode ident = new ASTNode(NodeType.NODE_LDFLAG);

        if (expectTokenType(current.getType(), TokenType.TYPE_IDENT)) {
          ident.setValue(current.getText());
          node.addChildren(ident);

          position++;
          current = tokens.get(position);
        } else {
      throw new ParserException(StringConcat.concat("Expected a closed curly brace '}' at the end of block 'ldflags', got: '", current.getType().toString(), "'. Line: ", String.valueOf(current.getLineNumber()), ". Column: ", String.valueOf(current.getColumn()), "."));
        }
      }
      position++;
    } else {
      throw new ParserException(StringConcat.concat("Expected an open curly brace '{', got: '", current.getType().toString(), "'. Line: ", String.valueOf(current.getLineNumber()), ". Column: ", String.valueOf(current.getColumn()), "."));
    }

    return node;
  }

  // For debugging the AST
  private void printIndent(int depth) {
    for (int i = 0; i < depth; i++) {
     System.out.print(' '); 
    }
  }

  public void printAST(ASTNode node, int depth) {
    switch (node.getType()) {
      case NODE_DSL_PROGRAM:
        printIndent(depth);
        System.out.printf("ProgramNode()\n");

        if (!node.getChildren().isEmpty()) {
          for (ASTNode child : node.getChildren()) {
            printAST(child, depth + 1);
          }
        }
        break;

      case NODE_PROJECT:
        printIndent(depth + 1);
        System.out.printf("ProjectNode(%s)\n", node.getValue());
        break;

      case NODE_COMPILER:
        printIndent(depth + 1);
        System.out.printf("CompilerNode(%s)\n", node.getValue());
        break;

      case NODE_SOURCES:
        printIndent(depth + 1);
        System.out.printf("SourcesNode()\n");

        if (!node.getChildren().isEmpty()) {
          for (ASTNode child : node.getChildren()) {
            printAST(child, depth + 1);
          }
        }
        break;

      case NODE_SOURCE:
        printIndent(depth + 2);
        System.out.printf("SourceNode(%s)\n", node.getValue());
        break;

      case NODE_INCLUDES:
        printIndent(depth + 1);
        System.out.printf("IncludesNode()\n");

        if (!node.getChildren().isEmpty()) {
          for (ASTNode child : node.getChildren()) {
            printAST(child, depth + 1);
          }
        }
        break;

      case NODE_INCLUDE:
        printIndent(depth + 2);
        System.out.printf("IncludeNode(%s)\n", node.getValue());
        break;

      case NODE_OUTPUT:
        printIndent(depth + 1);
        System.out.printf("OutputNode(%s)\n", node.getValue());
        break;

      case NODE_TARGET:
        printIndent(depth + 1);
        System.out.printf("TargetNode(%s)\n", node.getValue());
        break;

      case NODE_CFLAGS:
        printIndent(depth + 1);
        System.out.printf("CFlagsNode()\n");

        if (!node.getChildren().isEmpty()) {
          for (ASTNode child : node.getChildren()) {
            printAST(child, depth + 1);
          }
        }
        break;

      case NODE_CFLAG:
        printIndent(depth + 2);
        System.out.printf("CFlagNode(%s)\n", node.getValue());
        break;

      case NODE_LDFLAGS:
        printIndent(depth + 1);
        System.out.printf("LDFlagsNode()\n");

        if (!node.getChildren().isEmpty()) {
          for (ASTNode child : node.getChildren()) {
            printAST(child, depth + 1);
          }
        }
        break;

      case NODE_LDFLAG:
        printIndent(depth + 2);
        System.out.printf("LDFlagNode(%s)\n", node.getValue());
        break;
    }
  }
}
