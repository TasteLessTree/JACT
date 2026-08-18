package org.jact.enviroment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildConfig {
  private String project;
  private String compiler;
  private String output;
  private String target;

  private List<String> sources;
  private List<String> includes;
  private List<String> cflags;
  private List<String> ldflags;

  public BuildConfig() {
    this.sources = new ArrayList<>();
    this.includes = new ArrayList<>();
    this.cflags = new ArrayList<>();
    this.ldflags = new ArrayList<>();
  }

  public boolean checkConfiguration(boolean everyField) {
    Map<String, Boolean> map = isValidConfiguration();

    if (everyField) {
      for (String key : map.keySet()) {
        if (map.get(key) == false) {
          System.out.println("Found: " + key + " being false");
          return false;
        }
      }
      return true;
    } else {
      if (map.get("project") == false) {
        System.out.println("Project is false");
        return false;
      } else if (map.get("compiler") == false) {
        System.out.println("Compiler is false");
        return false;
      } else if (map.get("output") == false) {
        System.out.println("Output is false");
        return false;
      } else if (map.get("sources") == false) {
        System.out.println("Sources are empty");
        return false;
      }
    }

    return true;
  }

  private Map<String, Boolean> isValidConfiguration() {
    Map<String, Boolean> map = new HashMap<>();

    map.put("project", getProject() != null);
    map.put("compiler", getCompiler() != null);
    map.put("output", getOutput() != null);
    map.put("target", getTarget() != null);

    map.put("sources", !getSources().isEmpty());
    map.put("includes", !getIncludes().isEmpty());
    map.put("cflags", !getCflags().isEmpty());
    map.put("ldflags", !getLdflags().isEmpty());

    return map;
  }

  public String getProject() {
    return project;
  }

  public String getCompiler() {
    return compiler;
  }

  public String getOutput() {
    return output;
  }

  public String getTarget() {
    return target;
  }

  public List<String> getSources() {
    return sources;
  }

  public List<String> getIncludes() {
    return includes;
  }

  public List<String> getCflags() {
    return cflags;
  }

  public List<String> getLdflags() {
    return ldflags;
  }

  public void setProject(String project) {
    this.project = project;
  }

  public void setCompiler(String compiler) {
    this.compiler = compiler;
  }

  public void setOutput(String output) {
    this.output = output;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public void setSources(List<String> sources) {
    this.sources = sources;
  }

  public void setIncludes(List<String> includes) {
    this.includes = includes;
  }

  public void setCflags(List<String> cflags) {
    this.cflags = cflags;
  }

  public void setLdflags(List<String> ldflags) {
    this.ldflags = ldflags;
  }

  @Override
  public String toString() {
    return "Build Configuration -> {\n" +
      "\tProject: " + project + "\n" +
      "\tCompiler: " + compiler + "\n" +
      "\tOutput: " + output + "\n" +
      "\tTarget: " + target + "\n" +
      "\tSources: " + sources + "\n" +
      "\tIncludes: " + includes + "\n" +
      "\tC Flags: " + cflags + "\n" +
      "\tLD Flags: " + ldflags + "\n" +
      "}";
  }
}
