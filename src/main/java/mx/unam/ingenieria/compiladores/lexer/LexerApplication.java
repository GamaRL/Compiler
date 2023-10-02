package mx.unam.ingenieria.compiladores.lexer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import mx.unam.ingenieria.compiladores.lexer.components.ILexemeClassifier;
import mx.unam.ingenieria.compiladores.lexer.components.ILexemeExtractor;
import mx.unam.ingenieria.compiladores.lexer.models.TokenType;

@SpringBootApplication
public class LexerApplication implements CommandLineRunner {

	private static Logger LOG = LoggerFactory.getLogger(LexerApplication.class);
	@Autowired
	private ILexemeExtractor extractor;
	@Autowired
	private ILexemeClassifier classifier;

	public static void main(String[] args) {
		LOG.info("STARTING THE APPLICATION");
		SpringApplication.run(LexerApplication.class, args);

		LOG.info("APPLICATION FINISHED");
	}

	@Override
	public void run(String... args) throws Exception {
		LOG.info("EXECUTING: command line runner");

		var lexemes = extractor.getLexemes("$hola $mundo =? @if ( ) [-12];");
		lexemes.stream().forEach(l -> {
			if(classifier.getLexemeType(l) == TokenType.INVALID)
			LOG.error("args[{}]: {}", "INVALID LEXEME", "'" + l + "'");
			else
			LOG.info("args[{}]: {}", "NEW LEXEME", "'" + l + "'");
		}
		);
	}
}
