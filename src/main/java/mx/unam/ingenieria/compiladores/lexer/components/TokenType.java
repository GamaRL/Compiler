package mx.unam.ingenieria.compiladores.lexer.components;

/**
 * Definition of the categories in which a token can be classified
 */
public enum TokenType {
  IDENTIFIER, RIGHT_PARENTHESIS, LEFT_PARENTHESIS, RIGHT_BRACE, LEFT_BRACE,
  PLUS, MINUS, PRODUCT, DIVISION, MODULO, KEY_WORD, TYPE, SEMICOLON, ASIGN,
  EQUAL, LESS_OR_EQUAL, LESS, GREATER_OR_EQUAL, GREATER
}
