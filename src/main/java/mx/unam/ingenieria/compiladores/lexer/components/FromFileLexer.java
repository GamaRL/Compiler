
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

  @Value("${file-path}")
  private String filePath;

  private int currentLineNumber = 0;
  private List<String> lines;
  private List<String> currentLineTokens = new ArrayList<>();;

  public FromFileLexer(
      ILexemeExtractor extractor,
      ILexemeClassifier classifier
  ) {
    this.extractor = extractor;
    this.classifier = classifier;
  }

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
    if(currentLineNumber == 0) {
      Path p = Paths.get(filePath);
      try {
        this.lines = Files.lines(p).collect(Collectors.toList());
        currentLineNumber = 0;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    if(currentLineTokens.isEmpty()) {
      String currentLine = this.lines.remove(0);
      currentLineNumber++;
      currentLineTokens = extractor.getLexemes(currentLine);
    }

    String currentToken = currentLineTokens.remove(0);
    TokenType type = classifier.getLexemeType(currentToken);
    return new Token(type, currentToken);
  }
}

