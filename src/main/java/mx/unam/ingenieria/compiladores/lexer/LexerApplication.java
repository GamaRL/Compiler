package mx.unam.ingenieria.compiladores.lexer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import mx.unam.ingenieria.compiladores.lexer.components.ILexer;
import mx.unam.ingenieria.compiladores.lexer.models.Token;
import mx.unam.ingenieria.compiladores.lexer.models.TokenType;

@SpringBootApplication
public class LexerApplication implements ApplicationRunner {

	private static Logger LOG = LoggerFactory.getLogger(LexerApplication.class);

	private ILexer lexer;

	public static void main(String[] args) {
		LOG.info("STARTING THE APPLICATION");

		SpringApplication.run(LexerApplication.class, args);

		LOG.info("APPLICATION FINISHED");
	}

	public LexerApplication(ILexer lexer) {
		this.lexer = lexer;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
    if (args.containsOption("file-path")) {

      Path p = Paths.get(args.getOptionValues("file-path").get(0));
			if(Files.exists(p)) {
      	LOG.info("args[{}]: {}", "file-path", "'" + args.getOptionValues("file-path").get(0) + "'");
      	while (lexer.hasAnotherToken()) {
        	Token t = lexer.getNextToken();
        	if (t.getType() == TokenType.INVALID)
          	LOG.error("{} : {}", "INVALID LEXEME", "'" + t.getValue() + "'");
        	else
          	LOG.info("{} : {}", "NEW LEXEME", "'" + t.getValue() + "'");
				}
      } else {
      	LOG.info("{} : {}", "Not found", "The sepecified file couldn't be found");
			}
    } else {
      LOG.error("{} : {}", "Failing", "The file-path option is not provided");
    }
	}
}
