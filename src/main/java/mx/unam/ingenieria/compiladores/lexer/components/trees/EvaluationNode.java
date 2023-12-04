package mx.unam.ingenieria.compiladores.lexer.components.trees;

import org.springframework.expression.EvaluationException;

import mx.unam.ingenieria.compiladores.lexer.models.Token;
import mx.unam.ingenieria.compiladores.lexer.models.TokenType;

public class EvaluationNode {
  private Token terminal;
  private EvaluationNode left;
  private EvaluationNode right;
  private Token operand;

  public static EvaluationNode asTerminal(Token terminal) {
    return new EvaluationNode(terminal, null, null, null);
  }

  public static EvaluationNode asEvaluation(EvaluationNode left, EvaluationNode right, Token operand) {
    return new EvaluationNode(null, left, right, operand);
  }

  private EvaluationNode(Token terminal, EvaluationNode left, EvaluationNode right, Token operand) {
    this.terminal = terminal;
    this.left = left;
    this.right = right;
    this.operand = operand;
  }

  public int evaluate() {
    if(terminal != null) {
      if(terminal.getType() == TokenType.LITERAL) {
        return Integer.parseInt(terminal.getValue().substring(1, terminal.getValue().length() - 1));
      }
    } else if(operand.getType() == TokenType.PLUS) {
      return left.evaluate() + right.evaluate();
    } else if(operand.getType() == TokenType.PRODUCT) {
      return left.evaluate() * right.evaluate();
    }
    throw new EvaluationException("Unable to find this operation");
  }
}
