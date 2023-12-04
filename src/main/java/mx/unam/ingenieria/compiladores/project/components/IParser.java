package mx.unam.ingenieria.compiladores.project.components;

import mx.unam.ingenieria.compiladores.project.components.trees.ASTTree;

public interface IParser {
  ASTTree parseNextLine();
  boolean hasNextLine();
  int getCurrentLineNumber();
}
