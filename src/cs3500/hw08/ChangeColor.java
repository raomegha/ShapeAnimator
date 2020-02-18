package cs3500.hw08;


import java.awt.Color;
import java.util.ArrayList;

import cs3500.animator.provider.model.Command;
import cs3500.animator.provider.model.Shape;
import cs3500.hw05.ChangeColorAction;

/**
 * Adapter for our change color action class and provider's ChangeColor class.
 */
public class ChangeColor extends Command {

  protected ChangeColorAction adaptee;

  /**
   * Constructs an instance of a ChangeColor command.
   *
   * @param a - The given ChangeColor action to be converted to ChangeColor command.
   */
  public ChangeColor(ChangeColorAction a) {
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
    return "colorChange";
  }

  //The following getter methods are called in the provider's svg view.

  /**
   * Gets the set starting color for the shape.
   *
   * @return - Color of the shape.
   */
  public Color getStartColor() {
    return new Color((int) (adaptee.getColors().get(0) * 255), (int) (adaptee.getColors().get(1)
            * 255), (int) (adaptee.getColors().get(2) * 255));
  }

  /**
   * Gets the set end color for the shape.
   *
   * @return - Color of the shape.
   */
  public Color getNewColor() {
    return new Color((int) (adaptee.getColors().get(3) * 255), (int) (adaptee.getColors().get(4)
            * 255), (int) (adaptee.getColors().get(5) * 255));
  }

  /**
   * Gets the old and new colors in one array.
   *
   * @return - An arraylist of integers representing the old and new color values.
   */
  public ArrayList<Double> getColors() {
    return this.adaptee.getColors();
  }


}

