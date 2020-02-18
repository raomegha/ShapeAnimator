package cs3500.hw08;


import cs3500.animator.provider.model.Command;
import cs3500.animator.provider.model.Shape;
import cs3500.hw05.MoveAction;

/**
 * Adapter for our move action class and provider's move class.
 */
public class Move extends Command {

  protected MoveAction adaptee;

  /**
   * Constructs an instance of a Move command.
   *
   * @param a - The given move action to be converted to move command.
   */
  public Move(MoveAction a) {
    super(a.getShapeName(), a.getStartTime(), a.getEndTime());
    this.adaptee = a;
  }

  @Override
  protected String getCmdInfo(Shape shape) {
    return this.adaptee.printAction();
  }

  @Override
  public boolean equals(Object o) {
    return false;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public void execute(Shape s, int t) {
    //unused provider's method
  }

  @Override
  public String toString(Shape shape, int tempo, String unit) {
    return this.adaptee.printAction() + "\n";
  }

  @Override
  public String getType() {
    return "move";
  }

  //The following getter methods are called in the provider's svg view.

  /**
   * Getter for starting position.
   *
   * @return Posn that represents the starting position
   */
  public Posn getStartLoc() {
    return new Posn(this.adaptee.getStartPos().getKey().intValue(),
            this.adaptee.getStartPos().getValue().intValue());
  }

  /**
   * Getter for final position.
   *
   * @return - Posn that represents the final position
   */
  public Posn getDestination() {
    return new Posn(this.adaptee.getEndPos().getKey().intValue(),
            this.adaptee.getEndPos().getValue().intValue());
  }
}
