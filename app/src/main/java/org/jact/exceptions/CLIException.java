package org.jact.exceptions;

import org.jact.util.StringConcat;
import org.jact.util.TagGenerator;
import org.jact.util.TagType;

public class CLIException extends Exception {
  public CLIException(String m) {
    super(StringConcat.concat(TagGenerator.generateTag(TagType.ERROR), " ", m));
  }
}
