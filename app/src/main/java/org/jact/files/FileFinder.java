package org.jact.files;

import java.io.File;

import org.jact.enviroment.Enviroment;
import org.jact.util.StringConcat;

public class FileFinder {
  private Enviroment env;

  public FileFinder(Enviroment env) {
    this.env = env;
  }

  public File[] filesToFind(String dir) {
    String path;

    if (env.isWindows()) {
      path = StringConcat.concat(
          env.getProjectPath(),
          "\\",
          dir
          );

    } else {
      path = StringConcat.concat(
          env.getProjectPath(),
          "/",
          dir
          );
    }

    File file = new File(path);

    return file.listFiles();
  }

  public Enviroment getEnv() {
    return env;
  }
}
