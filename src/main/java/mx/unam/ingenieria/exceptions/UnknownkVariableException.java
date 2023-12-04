package mx.unam.ingenieria.exceptions;

public class UnknownkVariableException extends Exception {

  private String message;
  private int line;

  public UnknownkVariableException(String message, int line) {
    this.message = message;
    this.line = line;
  }

  @Override
  public String getMessage() {
    return String.format("[%s] in line %d", this.message, this.line);
  }
}
