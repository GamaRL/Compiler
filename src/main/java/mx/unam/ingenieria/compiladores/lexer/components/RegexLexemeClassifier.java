package mx.unam.ingenieria.compiladores.lexer.components;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import mx.unam.ingenieria.compiladores.lexer.models.TokenType;

@Service
public class RegexLexemeClassifier implements ILexemeClassifier {

  private static final Pattern identifierPattern = Pattern.compile("^\\$[A-Za-z][A-Za-z0-9]*$");
  private static final Pattern keywordPattern = Pattern.compile("^@(if|else)$");
  private static final Pattern assignmentPattern = Pattern.compile("^=:$");
  private static final Pattern equalsPattern = Pattern.compile("^=\\?$");
  private static final Pattern rightParenthesisPattern = Pattern.compile("^\\)$");
  private static final Pattern leftParenthesisPattern = Pattern.compile("^\\($");
  private static final Pattern semicolonPattern = Pattern.compile("^;$");

  // ^\[-?[0-9]+\]$
  private static final Pattern numericLiteralPattern = Pattern.compile("^\\[-?[0-9]+\\]$");

  public TokenType getLexemeType(String lexeme) {
    if(identifierPattern.matcher(lexeme).matches())
      return TokenType.IDENTIFIER;

    if(keywordPattern.matcher(lexeme).matches())
      return TokenType.KEY_WORD;

    if(assignmentPattern.matcher(lexeme).matches())
      return TokenType.ASIGN;

    if(equalsPattern.matcher(lexeme).matches())
      return TokenType.EQUAL;

    if(rightParenthesisPattern.matcher(lexeme).matches())
      return TokenType.RIGHT_PARENTHESIS;

    if(leftParenthesisPattern.matcher(lexeme).matches())
      return TokenType.LEFT_PARENTHESIS;

    if(semicolonPattern.matcher(lexeme).matches())
      return TokenType.SEMICOLON;

    if(numericLiteralPattern.matcher(lexeme).matches())
      return TokenType.LITERAL;

    return TokenType.INVALID;
  }
}
