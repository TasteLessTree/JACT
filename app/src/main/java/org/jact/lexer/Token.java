package org.jact.lexer;

public class Token {
  private TokenType type;
  private String text;
  private int lineNumber;
  private int column;

  public Token(TokenType type, String text, int lineNumber, int column) {
    this.type = type;
    this.text = text;
    this.lineNumber = lineNumber;
    this.column = column;
  }

  public TokenType getType() {
      return type;
  }

  public String getText() {
      return text;
  }

  public int getLineNumber() {
      return lineNumber;
  }

  public int getColumn() {
      return column;
  }

  @Override
  public String toString() {
      return "Token -> {\n"
        + "\tType: " + type.toString() + "\n"
        + "\tText: '" + text + "'\n"
        + "\tLine number: " + lineNumber + "\n"
        + "\tColumn: " + column + "\n"
        + "}\n";
  }
}
