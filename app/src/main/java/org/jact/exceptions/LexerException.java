package org.jact.exceptions;

import org.jact.util.StringConcat;
import org.jact.util.TagGenerator;
import org.jact.util.TagType;

public class LexerException extends Exception {
  public LexerException(String m) {
    super(StringConcat.concat(TagGenerator.generateTag(TagType.ERROR), m));
  }
}
