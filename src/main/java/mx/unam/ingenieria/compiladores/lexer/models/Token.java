package mx.unam.ingenieria.compiladores.lexer.models;

import lombok.Data;

@Data
public class Token {
  private TokenType type;
  private String value;
}
