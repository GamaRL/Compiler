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
      return EvaluationNode.asTerminal(firstToken);
    }

    if (firstToken.getType() == TokenType.LITERAL) {
      return EvaluationNode.asTerminal(firstToken);
    }

    if (firstToken.getType() == TokenType.LEFT_PARENTHESIS) {
      EvaluationNode left, right;


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
      throw new InvalidFormatException("An IDENTIFIER was expected", lexer.getCurrentLineNumber());
    }

    identifier = lexer.getCurrentToken();

    if(lexer.hasAnotherToken() && lexer.getNextToken().getType() != TokenType.ASSIGN) {
      throw new InvalidFormatException("Assignment operator was expected", lexer.getCurrentLineNumber());
    }

    var evaluation = parseEvaluation();

    if(lexer.hasAnotherToken() && lexer.getNextToken().getType() != TokenType.SEMICOLON) {
      throw new InvalidFormatException("Semicolon was expected", lexer.getCurrentLineNumber());
    }

    return ASTTree.asDeclaration(identifier, new EvaluationTree(evaluation));
  }

  public ASTTree parseAssingment() throws InvalidFormatException {
    Token identifier = null;
    if(lexer.getCurrentToken().getType() != TokenType.IDENTIFIER) {
      throw new InvalidFormatException("An IDENTIFIER was expected", lexer.getCurrentLineNumber());
    }

    identifier = lexer.getCurrentToken();

    if(lexer.hasAnotherToken() && lexer.getNextToken().getType() != TokenType.ASSIGN) {
      throw new InvalidFormatException("Assignment operator was expected", lexer.getCurrentLineNumber());
    }

    var evaluation = parseEvaluation();

    if(lexer.hasAnotherToken() && lexer.getNextToken().getType() != TokenType.SEMICOLON) {
      throw new InvalidFormatException("Semicolon was expected", lexer.getCurrentLineNumber());
    }

    return ASTTree.asAssignment(identifier, new EvaluationTree(evaluation));
  }

  public ASTTree parseShow() throws InvalidFormatException {
    Token identifier = null;
    if(lexer.getCurrentToken().getType() != TokenType.SHOW) {
      throw new InvalidFormatException("The SHOW operand was expected", lexer.getCurrentLineNumber());
    }

    if(lexer.hasAnotherToken() && lexer.getNextToken().getType() != TokenType.IDENTIFIER) {
      throw new InvalidFormatException("An IDENTIFIER was expected", lexer.getCurrentLineNumber());
    }

    identifier = lexer.getCurrentToken();

    if(lexer.hasAnotherToken() && lexer.getNextToken().getType() != TokenType.SEMICOLON) {
      throw new InvalidFormatException("Semicolon was expected", lexer.getCurrentLineNumber());
    }

    return ASTTree.asShow(identifier);
  }

  @Override
  public ASTTree parseNextLine() {
    lexer.getNextToken();
    Token currentToken = lexer.getCurrentToken();

    try {
      if (currentToken.getType() == TokenType.TYPE) {
        LOG.info("Parsing a declaration");
        return parseDeclaration();
      } else if (currentToken.getType() == TokenType.IDENTIFIER) {
        LOG.info("Parsing an assignment");
        return parseAssingment();
      } else if (currentToken.getType() == TokenType.SHOW) {
        LOG.info("Parsing a showing");
        return parseShow();
      } else {
        LOG.error("Invalid operation");
      }
    } catch (InvalidFormatException e) {
      LOG.error(e.getMessage());
    }
    return null;
  }

  @Override
  public boolean hasNextLine() {
    return lexer.hasAnotherToken();
  }

  @Override
  public int getCurrentLineNumber() {
    return lexer.getCurrentLineNumber();
  }

}
