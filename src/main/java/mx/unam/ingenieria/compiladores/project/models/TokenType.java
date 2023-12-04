package mx.unam.ingenieria.compiladores.project.models;

/**
 * Definition of the categories in which a token can be classified
 */
public enum TokenType {
  IDENTIFIER, RIGHT_PARENTHESIS, LEFT_PARENTHESIS, RIGHT_BRACE, LEFT_BRACE,
  PLUS, MINUS, PRODUCT, DIVISION, MODULO, KEYWORD, TYPE, SEMICOLON, ASSIGN,
  EQUAL, LESS_OR_EQUAL, LESS, GREATER_OR_EQUAL, GREATER, INVALID, LITERAL, SHOW
}
