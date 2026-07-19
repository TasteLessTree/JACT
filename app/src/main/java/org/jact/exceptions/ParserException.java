package org.jact.exceptions;

import org.jact.util.StringConcat;

public class ParserException extends Exception{
   private static final String ANSI_RESET = "\u001B[0m";
  private static final String ANSI_RED = "\u001B[31m";

  public ParserException(String m) {
    super(StringConcat.concat("[", ANSI_RED, "ERROR", ANSI_RESET, "]", " ", m));
  } 
}
