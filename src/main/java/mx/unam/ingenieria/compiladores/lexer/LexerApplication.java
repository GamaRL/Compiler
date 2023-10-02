package mx.unam.ingenieria.compiladores.lexer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import mx.unam.ingenieria.compiladores.lexer.components.ILexer;
import mx.unam.ingenieria.compiladores.lexer.models.Token;
import mx.unam.ingenieria.compiladores.lexer.models.TokenType;

@SpringBootApplication
public class LexerApplication implements CommandLineRunner {

	private static Logger LOG = LoggerFactory.getLogger(LexerApplication.class);
	@Autowired
	private ILexer lexer;

	public static void main(String[] args) {
		LOG.info("STARTING THE APPLICATION");
		SpringApplication.run(LexerApplication.class, args);

		LOG.info("APPLICATION FINISHED");
	}

	@Override
	public void run(String... args) throws Exception {
		LOG.info("EXECUTING: command line runner");

		while(lexer.hasAnotherToken()) {
			Token t = lexer.getNextToken();
			if(t.getType() == TokenType.INVALID)
			LOG.error("args[{}]: {}", "INVALID LEXEME", "'" + t.getValue() + "'");
			else
			LOG.info("args[{}]: {}", "NEW LEXEME", "'" + t.getValue() + "'");
		}

	}
}
