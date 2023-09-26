package mx.unam.ingenieria.compiladores.lexer.components;

import java.util.List;

public interface ILexemeExtractor {
  List<String> getLexemes(String line);
}
