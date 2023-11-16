package mx.unam.ingenieria.compiladores.lexer.components;

import org.springframework.stereotype.Service;

import mx.unam.ingenieria.compiladores.lexer.models.Token;
import mx.unam.ingenieria.compiladores.lexer.models.TokenType;

//@Service
public class RecursiveParser implements IParser {

  private ILexer lexer;

  public RecursiveParser(ILexer lexer) {
    this.lexer = lexer;
  }

  /*
   * E –> T E’ 
   * E’ –> + T E’ | e 
   * T –> F T’ 
   * T’ –> * F T’ | e 
   * F –> ( E ) | id
   */
  private boolean F() {
    System.out.println("Entrando en F");
    if(lexer.hasAnotherToken()) {
      Token next = lexer.getNextToken();
      System.out.println(next);
      if(next.isOfSameType(new Token(TokenType.LEFT_PARENTHESIS,"("))){
        if(E()) {
          Token next2 = lexer.getNextToken();
          if(next2.isOfSameType(new Token(TokenType.RIGHT_PARENTHESIS,")"))){
            return true;
          }
        }
      } else if (next.isOfSameType(new Token(TokenType.LITERAL, "--"))) {
        return true;
      }

      // System.out.println(next.getType());
      // System.out.println(next.isOfSameType(new Token(TokenType.LITERAL, "--")));
    }
    return false;
  }

  private boolean Tx() {
    System.out.println("Entrando en Tx");
    if(lexer.hasAnotherToken()) {
      Token next = lexer.getNextToken();
      if(next.isOfSameType(new Token(TokenType.PRODUCT,"*"))){
        return F() && Tx();
      }
    }
    return false;
  }
  private boolean T() {
    System.out.println("Entrando en T");
    if(lexer.hasAnotherToken()) {
      return F() && Tx();
    }
    return false;
  }

  private boolean Ex() {
    System.out.println("Entrando en Ex");
    if(lexer.hasAnotherToken()) {
      Token next = lexer.getNextToken();
      if(next.isOfSameType(new Token(TokenType.PLUS,"+"))){
          return T() && Ex();
      }
    }
    return false;
  }

  private boolean E() {
    System.out.println("Entrando en E");
    if(lexer.hasAnotherToken()) {
      return T() && Ex();     
    }
    return false;
  }

  

  @Override
  public boolean validate() {
    return E();
  }
  
}
