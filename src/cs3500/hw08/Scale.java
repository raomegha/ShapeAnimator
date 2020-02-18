package cs3500.hw08;

import java.util.ArrayList;

import cs3500.animator.provider.model.Command;
import cs3500.animator.provider.model.Shape;
import cs3500.hw05.ResizeAction;
import javafx.util.Pair;

/**
 * Adapter for our scale action class and provider's scale class.
 */
public class Scale extends Command {

  protected ResizeAction adaptee;

  /**
   * Constructs an instance of a Scale command.
   *
   * @param a - The given resize action to be converted to Scale command.
   */
  public Scale(ResizeAction a) {
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
  public String getType() {
    return "scale";
  }

  @Override
  public String toString(Shape shape, int tempo, String unit) {
    return this.adaptee.printAction() + "\n";
  }

  //The following getter methods are called in the provider's svg view.

  /**
   * Getter for the Starting x dimension.
   *
   * @return - int representing the starting x dimension
   */
  public int getStartXDimension() {
    return this.adaptee.getOldParms().get(0).getValue().intValue();
  }

  /**
   * Getter for the new x dimension.
   *
   * @return - int representing the new x dimension
   */
  public int getNewXDimension() {
    return this.adaptee.getNewParms().get(0).getValue().intValue();
  }

  /**
   * Getter for the Starting y dimension.
   *
   * @return - int representing the starting y dimension
   */
  public int getStartYDimension() {
    return this.adaptee.getOldParms().get(1).getValue().intValue();
  }

  /**
   * Getter for the new y dimension.
   *
   * @return - int representing the new y dimension
   */
  public int getNewYDimension() {
    return this.adaptee.getNewParms().get(1).getValue().intValue();
  }

  /**
   * Getter for the old paramters of the shape.
   *
   * @return - An arraylist of Pairs representing the parameters.
   */
  public ArrayList<Pair<String, Double>> getOldParms() {
    return this.adaptee.getOldParms();
  }


  /**
   * Getter for the new paramters of the shape.
   *
   * @return - An arraylist of Pairs representing the parameters.
   */
  public ArrayList<Pair<String, Double>> getNewParms() {
    return this.adaptee.getNewParms();
  }

}
