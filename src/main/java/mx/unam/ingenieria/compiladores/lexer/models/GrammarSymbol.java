package mx.unam.ingenieria.compiladores.lexer.models;

public class GrammarSymbol {
  protected String value;

  public GrammarSymbol(String value) {
    this.value = value;
  }

  public boolean isOfSameType(Object obj) {
    if(obj instanceof GrammarSymbol) {
      GrammarSymbol other = (GrammarSymbol)obj;
      return other.value.equals(this.value);
    }
    return false;
  }

  @Override
  public String toString() {
    
    return "[" + value + "]";
  }
}