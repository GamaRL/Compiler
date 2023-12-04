package mx.unam.ingenieria.compiladores.project.components;

import java.util.List;

public interface ILexemeExtractor {
  List<String> getLexemes(String line);
}
