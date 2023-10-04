package mx.unam.ingenieria.compiladores.lexer.components;

import mx.unam.ingenieria.compiladores.lexer.models.Token;

public interface ILexer {
  boolean hasAnotherToken();
  Token getNextToken();
  int getCurrentLineNumber();
}

