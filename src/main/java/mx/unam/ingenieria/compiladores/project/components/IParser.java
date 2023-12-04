package mx.unam.ingenieria.compiladores.project.components;

public interface IParser {
  void parseNextLine();
  boolean hasNextLine();
  int getCurrentLineNumber();
}
