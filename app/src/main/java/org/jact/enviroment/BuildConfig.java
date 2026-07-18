package org.jact.enviroment;

import java.util.ArrayList;
import java.util.List;

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
