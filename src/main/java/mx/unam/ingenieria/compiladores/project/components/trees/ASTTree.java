package mx.unam.ingenieria.compiladores.project.components.trees;

import mx.unam.ingenieria.compiladores.project.models.Token;

public class ASTTree {
  private ASTTreeType type;
  private Token variable;
  private EvaluationTree evaluation;

  public static ASTTree asDeclaration(Token variable, EvaluationTree tree) {
    return new ASTTree(variable, ASTTreeType.DEFINITION, tree);
  }

  public static ASTTree asAssignment(Token variable, EvaluationTree tree) {
    return new ASTTree(variable, ASTTreeType.ASSIGNMENT, tree);
  }

  public static ASTTree asShow(Token variable) {
    return new ASTTree(variable, ASTTreeType.SHOW, null);
  }

  private ASTTree(Token variable, ASTTreeType type, EvaluationTree tree) {
    this.variable = variable;
    this.type = type;
    this.evaluation = tree;
  }

  public ASTTreeType getType() {
    return type;
  }

  public void setType(ASTTreeType type) {
    this.type = type;
  }

  public Token getVariable() {
    return variable;
  }

  public void setVariable(Token variable) {
    this.variable = variable;
  }

  public EvaluationTree getEvaluation() {
    return evaluation;
  }

  public void setEvaluation(EvaluationTree evaluation) {
    this.evaluation = evaluation;
  }
}
