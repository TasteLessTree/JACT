package org.jact.ast;

import java.util.ArrayList;
import java.util.List;

public class ASTNode {
  private NodeType type;
  private String value;
  private List<ASTNode> children;

  public ASTNode(NodeType type) {
    this.type = type;
    this.children = new ArrayList<>();
  }

  public ASTNode(NodeType type, String value) {
    this.type = type;
    this.value = value;
    this.children = new ArrayList<>();
  }

  public void addChildren(ASTNode node) {
    children.add(node);
  }

  public NodeType getType() {
      return type;
  }

  public String getValue() {
      return value;
  }

  public List<ASTNode> getChildren() {
      return children;
  }

  public void setType(NodeType type) {
      this.type = type;
  }

  public void setValue(String value) {
      this.value = value;
  }

  public void setChildren(List<ASTNode> children) {
      this.children = children;
  }

  @Override
  public String toString() {
    return "AST Node -> {\n" +
      "\tType: " + type + "\n" +
      "\tValue: '" + value + "'\n" +
      "\tChildren: "; 
      
  }
}
