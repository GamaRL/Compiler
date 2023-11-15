package mx.unam.ingenieria.compiladores.lexer.components;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import mx.unam.ingenieria.compiladores.lexer.models.Token;
import mx.unam.ingenieria.compiladores.lexer.models.TokenType;

@Component
public class FromFileLexer implements ILexer {
  private ILexemeExtractor extractor;
	private ILexemeClassifier classifier;

  @Value("${file-path:''}") // Injected value
  private String filePath;

  /**
   * The current analyzed file line
   */
  private int currentLineNumber = 0;

  /**
   * In-memory file lines list
   */
  private List<String> lines;

  /**
   * Possible tokens of the last analized tokens
   */
  private List<String> currentLineTokens = new ArrayList<>();;

  public FromFileLexer(
      ILexemeExtractor extractor,
      ILexemeClassifier classifier
  ) {
    this.extractor = extractor;
    this.classifier = classifier;
  }

  /**
   * Getter for the currentLineNumber attribute
   */
  @Override
  public int getCurrentLineNumber() {
    return this.currentLineNumber;
  }

  @Override
  public boolean hasAnotherToken() {
    if(currentLineNumber == 0)
      return true;
    return !(currentLineTokens.isEmpty() && lines.isEmpty());
  }

  @Override
  public Token getNextToken() {
    // If the lexer is in the line number 0
    if(currentLineNumber == 0) {
      // Load file lines into memmory using a list
      Path p = Paths.get(filePath);
      try {
        this.lines = Files.lines(p).collect(Collectors.toList());
        currentLineNumber = 0;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    // Takes the next file line
    if(currentLineTokens.isEmpty()) {
      String currentLine = this.lines.remove(0);
      currentLineNumber++;
      currentLineTokens = extractor.getLexemes(currentLine);
    }

    // Classifies the first possible token of the list
    String currentToken = currentLineTokens.remove(0);
    TokenType type = classifier.getLexemeType(currentToken);
    return new Token(type, currentToken);
  }
}

