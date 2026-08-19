package org.jact.enviroment;

import java.io.File;

public class Enviroment {
  private String projectPath;
  private String configFilePath;
  private OSType os;

  public Enviroment() {
    this.projectPath = System.getProperty("user.dir");
    this.configFilePath = findConfigFile(projectPath);
    this.os = osToType(System.getProperty("os.name"));
  }

  private String findConfigFile(String path) {
    File directory = new File(path);

    File[] contents = directory.listFiles();

    for (File file : contents) {
      if (file.isFile() && isConfigFile(file)) {
        return file.getAbsolutePath();
      } else if (file.isDirectory()) {
        findConfigFile(file.getPath());
      }
    }

    return null;
  }

  // ! Untested on Windows machines
  // ! Only has been tested on Linux, unaware if it works on MacOS
  private OSType osToType(String os) {
    if (os.startsWith("Win")) {
      return OSType.WINDOWS;     
    }

    return OSType.UNIX;  
  }

  private boolean isConfigFile(File file) {
    return file.getAbsolutePath().endsWith("project.jact");
  }

  public boolean isWindows() {
    return getOs() == OSType.WINDOWS;
  }

  public boolean isUnix() {
    return getOs() == OSType.UNIX;
  }

  public String getProjectPath() {
    return projectPath;
  }

  public String getConfigFilePath() {
    return configFilePath;
  }

  public OSType getOs() {
    return os;
  }

  public void setConfigFilePath(String configFilePath) {
    this.configFilePath = configFilePath;
  }
}
