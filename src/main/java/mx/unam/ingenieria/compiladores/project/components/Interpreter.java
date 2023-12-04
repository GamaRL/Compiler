package mx.unam.ingenieria.compiladores.project.components;

public class Interpreter implements ISemantics {

  private IParser parser;

  public Interpreter(IParser parser) {
    this.parser = parser;
  }

  @Override
  public void analizeNextLine() {
    while (parser.hasNextLine()) {
      
    }
  }
  
}
