package mx.unam.ingenieria.compiladores.project.models;

public class Token extends GrammarSymbol {
  private TokenType type;

  public Token(TokenType type, String value) {
    super(value);
    this.type = type;
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

  @Override
  public boolean isOfSameType(Object obj) {
    if(obj instanceof Token) {
      Token other = (Token)obj;

      return other.type == this.type;
    }
    return false;
  }
}
