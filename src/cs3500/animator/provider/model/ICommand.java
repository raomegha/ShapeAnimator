package cs3500.animator.provider.model;

/**
 * Interface for all commands. Implemented by the Command class and contains the method execute
 * that executes the given command on a shape.
 */
public interface ICommand {

  /**
   * Executes command on given shape.
   * @param shape Shape to execute command on
   * @param t animation time
   */
  void execute(Shape shape, int t);

  /**
   * Returns this commands type.
   * @return a String that represents this shape's type.
   */
  public String getType();

}








