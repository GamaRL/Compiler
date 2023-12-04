package mx.unam.ingenieria.compiladores.project.components;

import mx.unam.ingenieria.compiladores.project.components.trees.ASTTree;
import mx.unam.ingenieria.exceptions.InvalidFormatException;

public interface IParser {
  ASTTree parseNextLine() throws InvalidFormatException;
  boolean hasNextLine();
  int getCurrentLineNumber();
}
