package mx.unam.ingenieria.compiladores.project.exceptions;

public class InvalidFormatException extends Exception {

  private String message;
  private int line;

  public InvalidFormatException(String message, int line) {
    this.message = message;
    this.line = line;
  }

  @Override
  public String getMessage() {
    return String.format("[%s] in line %d", this.message, this.line);
  }
}
