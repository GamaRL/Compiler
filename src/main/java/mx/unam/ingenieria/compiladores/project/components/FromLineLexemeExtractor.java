package mx.unam.ingenieria.compiladores.project.components;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class FromLineLexemeExtractor implements ILexemeExtractor {

  @Override
  public List<String> getLexemes(String line) {
    List<Integer> possibleLexemesIndexes = new ArrayList<>();
    List<String> possibleLexemesList = new ArrayList<>();

    List<Integer> spaces = new ArrayList<>();

    // Identifies the possible lexemes' starting positions
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
        // One character length lexemes
        case '(': // Right Parenthesis
        case ')': // Left Parenthesis
        case ';': // Semicolon
        case '+': // Addition
        case '-': // Minus
        case '*': // Product
        case '/': // Divison
        case '%': // Modulo
          possibleLexemesIndexes.addAll(spaces);
          spaces.clear();
          if(!possibleLexemesIndexes.contains(i))
            possibleLexemesIndexes.add(i);
          possibleLexemesIndexes.add(i + 1);
      }
    }

    if(!possibleLexemesIndexes.contains(line.length()))
      possibleLexemesIndexes.add(line.length());

    // Create substrings from indexes' list
    for (int i = 0; i < possibleLexemesIndexes.size() - 1; i++) {
      String lexeme = line
        .substring(possibleLexemesIndexes.get(i), possibleLexemesIndexes.get(i+1))
        .trim();
      
      // Filter non-empty lexemes
      if(!lexeme.isEmpty())
        possibleLexemesList.add(lexeme);
    }

    return possibleLexemesList;
  }
}
