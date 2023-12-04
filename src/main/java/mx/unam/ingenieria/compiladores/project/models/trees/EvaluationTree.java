package mx.unam.ingenieria.compiladores.project.models.trees;

import java.util.Map;

public class EvaluationTree {
  private EvaluationNode root;

  public EvaluationTree(EvaluationNode root) {
    this.root = root;
  }

  public int evaluate(Map<String, Integer> variables) {
    return root.evaluate(variables);
  }
}
