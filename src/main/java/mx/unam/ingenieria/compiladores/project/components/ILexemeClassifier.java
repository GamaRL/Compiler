package mx.unam.ingenieria.compiladores.project.components;

import mx.unam.ingenieria.compiladores.project.models.TokenType;

public interface ILexemeClassifier {
  TokenType getLexemeType(String lexeme);
}
