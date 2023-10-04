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

    List<Integer> spaces = new ArrayList<>();
    for (int i = 0; i < line.length(); i++) {
      switch (line.charAt(i)) {
        case ' ': // Blank space
          spaces.add(i);
        case '@': // Keyword
        case '$': // Identifier
        case '=': // Equals or assignment operator
        case '[': // Numeric literal
          possibleLexemesIndexes.addAll(spaces);
          spaces.clear();
          if(!possibleLexemesIndexes.contains(i))
            possibleLexemesIndexes.add(i);
          break;
        case '(': // Right Parenthesis
        case ')': // Left Parenthesis
        case ';': // Semicolon
          possibleLexemesIndexes.addAll(spaces);
          spaces.clear();
          if(!possibleLexemesIndexes.contains(i))
            possibleLexemesIndexes.add(i);
          possibleLexemesIndexes.add(i + 1);
      }
    }

    if(!possibleLexemesIndexes.contains(line.length()))
      possibleLexemesIndexes.add(line.length());

    for (int i = 0; i < possibleLexemesIndexes.size() - 1; i++) {
      String lexeme = line
        .substring(possibleLexemesIndexes.get(i), possibleLexemesIndexes.get(i+1))
        .trim();
      
      if(!lexeme.isEmpty())
        possibleLexemesList.add(lexeme);
    }

    return possibleLexemesList;
  }
}
