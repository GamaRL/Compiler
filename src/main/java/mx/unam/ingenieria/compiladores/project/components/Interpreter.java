package mx.unam.ingenieria.compiladores.project.components;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import mx.unam.ingenieria.compiladores.project.components.trees.ASTTreeType;
import mx.unam.ingenieria.exceptions.InvalidFormatException;
import mx.unam.ingenieria.exceptions.UnknownkVariableException;

@Service
public class Interpreter implements ISemantics {

  private static Logger LOG = LoggerFactory.getLogger(UnknownkVariableException.class);

  private IParser parser;
  private Map<String, Integer> variables;

  public Interpreter(IParser parser) {
    this.parser = parser;
    this.variables = new HashMap<>();
  }

  @Override
  public void analizeNextLine() {
    try {
      while (parser.hasNextLine()) {
        var tree = parser.parseNextLine();
        var name = tree.getVariable().getValue();
        if (tree.getType() == ASTTreeType.DEFINITION) {
          var value = tree.getEvaluation().evaluate(variables);
          variables.put(name, value);
        } else if (tree.getType() == ASTTreeType.ASSIGNMENT) {
          if (variables.containsKey(name)) {
            var value = tree.getEvaluation().evaluate(variables);
            variables.put(name, value);
          } else {
            throw new UnknownkVariableException("Invalid identifier", parser.getCurrentLineNumber());
          }
        } else if (tree.getType() == ASTTreeType.SHOW) {
          if (variables.containsKey(name)) {
            var value = variables.get(name);
            System.out.println(value);
          } else {
            throw new UnknownkVariableException("Invalid identifier", parser.getCurrentLineNumber());
          }
        }
      }
    } catch (UnknownkVariableException ex) {
      LOG.error(ex.getMessage());
    } catch (InvalidFormatException e) {
      LOG.error(e.getMessage());
    }
  }

}
