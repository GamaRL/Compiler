package mx.unam.ingenieria.compiladores.lexer.models;

public class Token {
  private TokenType type;
  private String value;

  public Token(TokenType type, String value) {
    this.type = type;
    this.value = value;
  }

  /**
   * Getter for `type` attribute
   * @return the token type
   */
  public TokenType getType() {
    return type;
  }

  /**
   * Setter for `type` attribute
   * @param type
   */
  public void setType(TokenType type) {
    this.type = type;
  }

  /**
   * Getter for `value` attribute
   * @return the lexeme related to the Token
   */
  public String getValue() {
    return value;
  }

  /**
   * Setter for `value` attribute
   * @param value
   */
  public void setValue(String value) {
    this.value = value;
  }
}
