package mx.unam.ingenieria.compiladores.lexer.components.trees;

public class EvaluationTree {
  private EvaluationNode root;

  public EvaluationTree(EvaluationNode root) {
    this.root = root;
  }

  public int evaluate() {
    return root.evaluate();
  }
}
