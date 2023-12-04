package mx.unam.ingenieria.compiladores.project.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import mx.unam.ingenieria.compiladores.project.components.trees.ASTTree;
import mx.unam.ingenieria.compiladores.project.components.trees.EvaluationNode;
import mx.unam.ingenieria.compiladores.project.components.trees.EvaluationTree;
import mx.unam.ingenieria.compiladores.project.models.Token;
import mx.unam.ingenieria.compiladores.project.models.TokenType;
import mx.unam.ingenieria.exceptions.InvalidFormatException;

@Service
public class LRParser implements IParser {

  private static Logger LOG = LoggerFactory.getLogger(LRParser.class);

  private ILexer lexer;

  public LRParser(ILexer lexer) {
    this.lexer = lexer;
  }

  public EvaluationNode parseEvaluation() throws InvalidFormatException {
    if(!lexer.hasAnotherToken()) {
      throw new InvalidFormatException("Invalid expression", lexer.getCurrentLineNumber());
    }

    Token firstToken = lexer.getNextToken();
    if (firstToken.getType() == TokenType.IDENTIFIER) {
      LOG.info("Un identificador");
      return EvaluationNode.asTerminal(firstToken);
    }

    if (firstToken.getType() == TokenType.LITERAL) {
      LOG.info("Una literal");
      return EvaluationNode.asTerminal(firstToken);
    }

    if (firstToken.getType() == TokenType.LEFT_PARENTHESIS) {
      EvaluationNode left, right;

      LOG.info("Una expresión");

      left = parseEvaluation();

      if (!lexer.hasAnotherToken()) {
        throw new InvalidFormatException("An operand was expected", lexer.getCurrentLineNumber());
      }

      Token opToken = lexer.getNextToken();

      if(opToken.getType() == TokenType.PLUS || opToken.getType() == TokenType.PRODUCT) {

        if (!lexer.hasAnotherToken()) {
          throw new InvalidFormatException("An operand was expected", lexer.getCurrentLineNumber());
        }

        right = parseEvaluation();

        Token endToken = lexer.getNextToken();

        if(endToken.getType() != TokenType.RIGHT_PARENTHESIS) {
          throw new InvalidFormatException("Right parenthesis was expected", lexer.getCurrentLineNumber());
        }

        return EvaluationNode.asEvaluation(left, right, opToken);
      }
    }
    throw new InvalidFormatException("Unable to parse expression", lexer.getCurrentLineNumber());
  }

  public ASTTree parseDeclaration() throws InvalidFormatException {
    Token identifier = null;
    if(lexer.getCurrentToken().getType() != TokenType.TYPE) {
      throw new InvalidFormatException("Invalid TYPE assignment", lexer.getCurrentLineNumber());
    }

    if(lexer.hasAnotherToken() && lexer.getNextToken().getType() != TokenType.IDENTIFIER) {
      identifier = lexer.getCurrentToken();
      throw new InvalidFormatException("An IDENTIFIER was expected", lexer.getCurrentLineNumber());
    }

    if(lexer.hasAnotherToken() && lexer.getNextToken().getType() != TokenType.ASSIGN) {
      throw new InvalidFormatException("Assignment operator was expected", lexer.getCurrentLineNumber());
    }

    var evaluation = parseEvaluation();

    // TODO: FIX
    System.out.println(new EvaluationTree(evaluation).evaluate());

    if(lexer.hasAnotherToken() && lexer.getNextToken().getType() != TokenType.SEMICOLON) {
      LOG.error("Semicolon was expected");
    }

    return ASTTree.asDeclaration(identifier, new EvaluationTree(evaluation));
  }

  @Override
  public void parseNextLine() {
    while (lexer.hasAnotherToken()) {
      Token currentToken = lexer.getNextToken();

      try {
        if (currentToken.getType() == TokenType.TYPE) {
          LOG.info("Assignment");
          parseDeclaration();
        } else {
          LOG.error("Invalid operation");
        }
      } catch (InvalidFormatException e) {
        LOG.error(e.getMessage());
      }

    }
  }

  /*@Override
  public boolean validate() {
    
    lexer.getNextToken();
    
    List<GrammarSymbol> stack = new ArrayList<>();
    List<List<GrammarSymbol>> grammarRules = List.of(
      List.of(new GrammarSymbol("E"), new GrammarSymbol("E"), new Token(TokenType.PLUS, "+"), new GrammarSymbol("E")),
      List.of(new GrammarSymbol("E"), new GrammarSymbol("E"), new Token(TokenType.PRODUCT, "*"), new GrammarSymbol("E")),
      List.of(new GrammarSymbol("E"), new Token(TokenType.LITERAL, "-")),
      List.of(new GrammarSymbol("E"), new Token(TokenType.IDENTIFIER, "-")),
      List.of(new GrammarSymbol("E"), new Token(TokenType.LEFT_PARENTHESIS, "("), new GrammarSymbol("E"), new Token(TokenType.RIGHT_PARENTHESIS, ")"))
    );

    while (lexer.hasAnotherToken()) {
      Token t = lexer.getNextToken();
      if (t.getType() == TokenType.INVALID) {
        LOG.error("{} : {}", "INVALID LEXEME", "'" + t.getValue() + "' at line " + lexer.getCurrentLineNumber());
      } else {

        stack.add(t);

        LOG.info("[SHIFT...    ] : {}", stack.stream().map(i -> i + "").collect(Collectors.joining(" ")));
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
                hasChanged = true;
                stack = stack.subList(0, stack.size() - grammar.size() + 1);
                stack.add(grammar.get(0));
                LOG.info("[REDUCING... ] : {}", stack.stream().map(i -> i + "").collect(Collectors.joining(" ")));
              }
            } catch (Exception e) {
            }
          }
        }
      }
    }


    return stack.size() == 1;
  }*/

}
