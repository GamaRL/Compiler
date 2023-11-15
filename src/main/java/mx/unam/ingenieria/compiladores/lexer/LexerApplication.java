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

import mx.unam.ingenieria.compiladores.lexer.components.IParser;

@SpringBootApplication
public class LexerApplication implements ApplicationRunner {

	private static Logger LOG = LoggerFactory.getLogger(LexerApplication.class);

	//private ILexer lexer;
	private IParser parser;

	public static void main(String[] args) {
		SpringApplication.run(LexerApplication.class, args);
	}

	public LexerApplication(IParser parser) {
		this.parser = parser;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		// Verifies the `file-path` argument
		if (args.containsOption("file-path")) {

			Path p = Paths.get(args.getOptionValues("file-path").get(0));

			// Verifies if the provided path is an existing file
			if (Files.exists(p)) {
				LOG.info("args[{}]: {}", "file-path", "'" + args.getOptionValues("file-path").get(0) + "'");

				parser.validate();
			} else {
				LOG.error("{} : {}", "Not found", "The sepecified file couldn't be found");
			}
		} else {
			LOG.error("{} : {}", "Failing", "The file-path option is not provided");
		}
	}
}
