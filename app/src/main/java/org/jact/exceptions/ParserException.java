package org.jact.exceptions;

import org.jact.util.StringConcat;
import org.jact.util.TagGenerator;
import org.jact.util.TagType;

public class ParserException extends Exception{
  public ParserException(String m) {
    super(StringConcat.concat(TagGenerator.generateTag(TagType.ERROR), m));
  } 
}
