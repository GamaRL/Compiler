package mx.unam.ingenieria.compiladores.lexer.components;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import mx.unam.ingenieria.compiladores.lexer.models.TokenType;

@Component
public class RegexLexemeClassifier implements ILexemeClassifier {

  private static final Pattern identifierPattern = Pattern.compile("^\\$[A-Za-z][A-Za-z0-9]*$");
  private static final Pattern keywordPattern = Pattern.compile("^@(if|else)$");
  private static final Pattern assignmentPattern = Pattern.compile("^=:$");
  private static final Pattern equalsPattern = Pattern.compile("^=\\?$");
  private static final Pattern plusPattern = Pattern.compile("^\\+$");
  private static final Pattern minusPattern = Pattern.compile("^\\-$");
  private static final Pattern productPattern = Pattern.compile("^\\*$");
  private static final Pattern divisionPattern = Pattern.compile("^/$");
  private static final Pattern moduloPattern = Pattern.compile("^\\%$");
  private static final Pattern rightParenthesisPattern = Pattern.compile("^\\)$");
  private static final Pattern leftParenthesisPattern = Pattern.compile("^\\($");
  private static final Pattern semicolonPattern = Pattern.compile("^;$");
  private static final Pattern typePattern = Pattern.compile("^@(int)$");

  // ^\[-?[0-9]+\]$
  private static final Pattern numericLiteralPattern = Pattern.compile("^\\[-?[0-9]+\\]$");

  public TokenType getLexemeType(String lexeme) {
    if(identifierPattern.matcher(lexeme).matches())
      return TokenType.IDENTIFIER;

    if(keywordPattern.matcher(lexeme).matches())
      return TokenType.KEYWORD;

    if(assignmentPattern.matcher(lexeme).matches())
      return TokenType.ASSIGN;

    if(equalsPattern.matcher(lexeme).matches())
      return TokenType.EQUAL;

    if(plusPattern.matcher(lexeme).matches())
      return TokenType.PLUS;

    if(minusPattern.matcher(lexeme).matches())
      return TokenType.MINUS;

    if(productPattern.matcher(lexeme).matches())
      return TokenType.PRODUCT;

    if(divisionPattern.matcher(lexeme).matches())
      return TokenType.DIVISION;

    if(moduloPattern.matcher(lexeme).matches())
      return TokenType.MODULO;

    if(rightParenthesisPattern.matcher(lexeme).matches())
      return TokenType.RIGHT_PARENTHESIS;

    if(leftParenthesisPattern.matcher(lexeme).matches())
      return TokenType.LEFT_PARENTHESIS;

    if(semicolonPattern.matcher(lexeme).matches())
      return TokenType.SEMICOLON;

    if(numericLiteralPattern.matcher(lexeme).matches())
      return TokenType.LITERAL;

    if(typePattern.matcher(lexeme).matches())
    return TokenType.TYPE;

    return TokenType.INVALID;
  }
}
