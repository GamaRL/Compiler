package mx.unam.ingenieria.compiladores.project.components;

import mx.unam.ingenieria.compiladores.project.exceptions.InvalidFormatException;
import mx.unam.ingenieria.compiladores.project.models.trees.ASTTree;

public interface IParser {
  ASTTree parseNextLine() throws InvalidFormatException;
  boolean hasNextLine();
  int getCurrentLineNumber();
}
