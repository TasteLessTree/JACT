package org.jact.lexer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.jact.exceptions.LexerException;
import org.jact.util.StringConcat;

public class Lexer {
  private int position;
  private int line;
  private int column;

  public Lexer() {
    this.position = 0;
    this.line = 1;
    this.column = 1;
  }

  public List<Token> tokenize(String path) throws LexerException {
    List<Token> tokens = new ArrayList<Token>();

    try {
      String source = Files.readString(Path.of(path));

      while (position < source.length()) {
        if (Character.isWhitespace(source.charAt(position))) {
          consumeWhiteSpace(source);
          continue;
        }

        if (source.charAt(position) == '#') {
          consumeComment(source); 
          continue;
        }

        if (source.charAt(position) == '"') {
          tokens.add(readString(source));
          continue;
        }

        if (source.charAt(position) == '{') {
          tokens.add(new Token(TokenType.TYPE_OPEN_CBRACE, "Open Curly Brace", line, column));
          position++;
          continue;
        } else if (source.charAt(position) == '}') {
          tokens.add(new Token(TokenType.TYPE_CLOSE_CBRACE, "Closed Curly Brace", line, column));
          position++;
          continue;
        }

        if (isIdentifierChar(source.charAt(position))) {
          tokens.add(readIdentifier(source));
          continue;
        }

        throw new LexerException(StringConcat.concat("Unexpected char: '", String.valueOf(source.charAt(position)), "'. Line: ", String.valueOf(line), ". Column: ", String.valueOf(column), "."));
      }
    } catch (IOException e) {
      throw new RuntimeException(StringConcat.concat("Could not open file: '", path, "'.\n"));
    }

    tokens.add(new Token(TokenType.TYPE_EOF, "End Of File", line, column));
    return tokens;
  }

  private boolean isIdentifierChar(char c) {
    return Character.isLetterOrDigit(c) ||
      c == '-' ||
      c == '_' ||
      c == '.' ||
      c == '/' ||
      c == '='; // Is need in, for example:  "-stc=c23"
  }

  private void consumeWhiteSpace(String source) {
    while (position < source.length()) {
      char c = source.charAt(position);

      if (!Character.isWhitespace(c)) {
        break;
      }

      if (c == '\n') {
        line++;
        column = 1;
      } else {
        column++;
      }

      position++;
    }
  }

  private void consumeComment(String source) {
    while (position < source.length() && source.charAt(position) != '\n') {
      position++;
      column++;
    }
  }

  private Token readString(String source) throws LexerException {
    int startColumn = column;

    position++;
    column++;

    int start = position;

    while (position < source.length() && source.charAt(position) != '"') {
      if (source.charAt(position) == '\n') {
        throw new LexerException(StringConcat.concat("String literals cannot span multiple lines. Line: ", String.valueOf(line), ". Column: ", String.valueOf(column), "."));
      }

      position++;
      column++;
    }

    if (position >= source.length()) {
      throw new LexerException(StringConcat.concat("Unterminated string literal. Line: ", String.valueOf(line), ". Column: ", String.valueOf(column), "."));
    }

    String text = source.substring(start, position);

    position++;
    column++;

    return new Token(
        TokenType.TYPE_STRING,
        text,
        line,
        startColumn
    );
  }

  private Token readIdentifier(String source) {
    int start = position;
    int startColumn = column;

    while (position < source.length() && isIdentifierChar(source.charAt(position))) {
      position++;
      column++;
    }

    String word = source.substring(start, position);

    return new Token(
        checkForKeyword(word),
        word,
        line,
        startColumn
    );
  }

  private TokenType checkForKeyword(String word) {
    if (word.equalsIgnoreCase("project")) {
      return TokenType.TYPE_PROJECT;
    } else if (word.equalsIgnoreCase("compiler")) {
      return TokenType.TYPE_COMPILER;
    } else if (word.equalsIgnoreCase("sources")) {
      return TokenType.TYPE_SOURCES;
    } else if (word.equalsIgnoreCase("includes")) {
      return TokenType.TYPE_INCLUDES;
    } else if (word.equalsIgnoreCase("output")) {
      return TokenType.TYPE_OUTPUT;
    } else if (word.equalsIgnoreCase("target")) {
      return TokenType.TYPE_TARGET;
    } else if (word.equalsIgnoreCase("cflags")) {
      return TokenType.TYPE_CFLAGS;
    } else if (word.equalsIgnoreCase("ldflags")) {
      return TokenType.TYPE_LDFLAGS;
    }

    return TokenType.TYPE_IDENT;
  }
}
