package mx.unam.ingenieria.compiladores.project.components;

import mx.unam.ingenieria.compiladores.project.models.Token;

public interface ILexer {
  boolean hasAnotherToken();
  Token getNextToken();
  Token getCurrentToken();
  int getCurrentLineNumber();
}

