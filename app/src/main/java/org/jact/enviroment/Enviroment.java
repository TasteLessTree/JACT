package org.jact.enviroment;

import java.io.File;

public class Enviroment {
  private String projectPath;
  private String configFilePath;

  public Enviroment() {
    this.projectPath = System.getProperty("user.dir");
    this.configFilePath = findConfigFile(projectPath);
  }
 
  private String findConfigFile(String path) {
    File directory = new File(path);

    File[] contents = directory.listFiles();

    for (File file : contents) {
      if (file.isDirectory()) {
        findConfigFile(file.getPath());
      } else if (isConfigFile(file)) {
        return file.getAbsolutePath();
      }
    }

    return null;
  }

  private boolean isConfigFile(File file) {
    return file.getAbsolutePath().endsWith("project.jact");
  }

  public String getProjectPath() {
      return projectPath;
  }

  public String getConfigFilePath() {
      return configFilePath;
  }

  public void setConfigFilePath(String configFilePath) {
      this.configFilePath = configFilePath;
  }
}
