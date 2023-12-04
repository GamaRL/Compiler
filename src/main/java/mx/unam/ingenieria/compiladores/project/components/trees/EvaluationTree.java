package mx.unam.ingenieria.compiladores.project.components.trees;

public class EvaluationTree {
  private EvaluationNode root;

  public EvaluationTree(EvaluationNode root) {
    this.root = root;
  }

  public int evaluate() {
    return root.evaluate();
  }
}
