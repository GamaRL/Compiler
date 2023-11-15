package mx.unam.ingenieria.compiladores.lexer.components;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import mx.unam.ingenieria.compiladores.lexer.models.GrammarSymbol;
import mx.unam.ingenieria.compiladores.lexer.models.Token;
import mx.unam.ingenieria.compiladores.lexer.models.TokenType;

@Service
public class LRParser implements IParser {

  private static Logger LOG = LoggerFactory.getLogger(LRParser.class);

  private ILexer lexer;

  public LRParser(ILexer lexer) {
    this.lexer = lexer;
  }

  @Override
  public boolean validate() {
    List<GrammarSymbol> stack = new ArrayList<>();
    List<List<GrammarSymbol>> grammarRules = List.of(
        // List.of(new GrammarSymbol("E"), new GrammarSymbol("E"), new
        // Token(TokenType.PLUS, "+"), new GrammarSymbol("T")),
        // List.of(new GrammarSymbol("E"), new GrammarSymbol("T")),
        // List.of(new GrammarSymbol("T"), new GrammarSymbol("T"), new
        // Token(TokenType.PRODUCT, "*"), new GrammarSymbol("F")),
        // List.of(new GrammarSymbol("T"), new GrammarSymbol("F")),
        // List.of(new GrammarSymbol("F"), new Token(TokenType.LEFT_PARENTHESIS, "("),
        // new GrammarSymbol("E"), new Token(TokenType.RIGHT_PARENTHESIS, ")")),
        // List.of(new GrammarSymbol("F"), new Token(TokenType.LITERAL, "-"))
        List.of(new GrammarSymbol("E"), new GrammarSymbol("E"), new Token(TokenType.PLUS, "+"), new GrammarSymbol("E")),
        List.of(new GrammarSymbol("E"), new GrammarSymbol("E"), new Token(TokenType.PRODUCT, "+"),
            new GrammarSymbol("E")),
        List.of(new GrammarSymbol("E"), new Token(TokenType.LITERAL, "-")),
        List.of(new GrammarSymbol("E"), new Token(TokenType.LEFT_PARENTHESIS, "("), new GrammarSymbol("E"),
            new Token(TokenType.RIGHT_PARENTHESIS, ")")));

    while (lexer.hasAnotherToken()) {
      Token t = lexer.getNextToken();
      if (t.getType() == TokenType.INVALID) {
        LOG.error("{} : {}", "INVALID LEXEME", "'" + t.getValue() + "' at line " + lexer.getCurrentLineNumber());
      } else {
        LOG.info("[TokenType: {}] : {}", t.getType(), "'" + t.getValue() + "'");

        stack.add(t);
        System.out.println("---");
        stack.forEach(i -> System.out.print(i + " "));
        System.out.println();
        boolean hasChanged = true;

        while (hasChanged) {
          hasChanged = false;

          for (var grammar : grammarRules) {
            try {
              var prod = grammar.subList(1, grammar.size());
              var k = stack.subList(stack.size() - grammar.size() + 1, stack.size());

              boolean needsReduce = true;
              for (int i = 0; i < prod.size(); i++) {
                if (!prod.get(i).isOfSameType(k.get(i))) {
                  needsReduce = false;
                }
              }

              if (needsReduce) {
                System.out.println("Reducing....");
                hasChanged = true;
                stack = stack.subList(0, stack.size() - grammar.size() + 1);
                stack.add(grammar.get(0));
                System.out.println("---");
                stack.forEach(i -> System.out.print(i + " "));
                System.out.println();

              }
            } catch (Exception e) {
            }
          }
        }
      }
    }
    stack.forEach(i -> System.out.print(i + " "));
    System.out.println();

    if (stack.size() == 1) {
      System.out.println("Aceptado");
    } else {
      System.out.println("Rechazado");
    }

    return false;
  }

}
