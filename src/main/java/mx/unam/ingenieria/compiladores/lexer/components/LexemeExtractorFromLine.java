package mx.unam.ingenieria.compiladores.lexer.components;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Service
@AllArgsConstructor
@Setter
public class LexemeExtractorFromLine implements ILexemeExtractor {

  @Override
  public List<String> getLexemes(String line) {
    List<Integer> possibleLexemesIndexes = new ArrayList<>();
    List<String> possibleLexemesList = new ArrayList<>();

    for (int i = 0; i < line.length(); i++) {
      switch (line.charAt(i)) {
        case '$': // Identifier
        case '@': // Keyword
        case '(': // Right Parenthesis
        case ')': // Left Parenthesis
        case ';': // Semicolon
        case '=': // Equals operator
        case ':': // Assignment operator
          possibleLexemesIndexes.add(i);
      }
    }

    possibleLexemesIndexes.add(line.length());

    for (int i = 0; i < possibleLexemesIndexes.size() - 1; i++) {
      String lexeme = line
        .substring(possibleLexemesIndexes.get(i), possibleLexemesIndexes.get(i+1))
        .trim();
      possibleLexemesList.add(lexeme);
    }

    return possibleLexemesList;
  }
}
