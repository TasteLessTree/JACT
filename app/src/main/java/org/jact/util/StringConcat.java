package org.jact.util;

public final class StringConcat {
  private StringConcat() {}

  public static String concat(String s1, String s2, String... sN) {
    if (sN.length > 0) {
      String s = s1.concat(s2);

      for (int i = 0; i < sN.length; i++) {
        s = s.concat(sN[i]);
      }

      return s;
    }

    return s1.concat(s2);
  }
}
