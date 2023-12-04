package mx.unam.ingenieria.compiladores.project.models.trees;

import java.util.Map;

import org.springframework.expression.EvaluationException;

import mx.unam.ingenieria.compiladores.project.models.Token;
import mx.unam.ingenieria.compiladores.project.models.TokenType;

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

  public int evaluate(Map<String, Integer> variables) {
    if(terminal != null) {
      if(terminal.getType() == TokenType.LITERAL) {
        return Integer.parseInt(terminal.getValue().substring(1, terminal.getValue().length() - 1));
      } else {
        return variables.get(terminal.getValue());
      }
    } else if(operand.getType() == TokenType.PLUS) {
      return left.evaluate(variables) + right.evaluate(variables);
    } else if(operand.getType() == TokenType.PRODUCT) {
      return left.evaluate(variables) * right.evaluate(variables);
    } else if (operand.getType() == TokenType.DIVISION) {
      return left.evaluate(variables) / right.evaluate(variables);
    } else if (operand.getType() == TokenType.MODULO) {
      return left.evaluate(variables) % right.evaluate(variables);
    }
    throw new EvaluationException("Unable to find this operation");
  }
}
