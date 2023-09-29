package mx.unam.ingenieria.compiladores.lexer.components;

import mx.unam.ingenieria.compiladores.lexer.models.TokenType;

public interface ILexemeClassifier {
  TokenType getLexemeType(String lexeme);
}
