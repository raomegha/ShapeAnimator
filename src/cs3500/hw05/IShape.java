package cs3500.hw05;

import java.util.ArrayList;

import javafx.util.Pair;

/**
 * Interface for a shape.
 */
public interface IShape {

  /**
   * Moves the shape to the given position.
   * @param endPos - The destination of the shape
   */
  void move(Pair<Double, Double> endPos);


  /**
   * Changes the shapes color.
   *
   * @param newRed   - The new amount of red
   * @param newGreen - the new amount of green
   * @param newBlue  - the new amount of blue
   */
  void changeColor(double newRed, double newGreen,
                   double newBlue);

  /**
   * Resizes the parameters of the shape given a set of parameters.
   *
   * @param newParms - The new parameters for the shape
   */
  void resize(ArrayList<Pair<String, Double>> newParms);

  /**
   * Prints the features of the shape.
   *
   * @return A string representing the features of the shape
   */
  String printShape();


  /**
   * Adds the given action to the list of actions in the shape.
   *
   * @param a - the action to add to the shape
   */
  void addAction(Action a);

  /**
   * Resets the shape to its original parameters when it was created.
   */
  void reset();

  /**
   * Changes the visibility of the shape.
   */
  void changeVisibility();

  /**
   * Returns the name of the shape.
   * @return Name of shape
   */
  String getName();

  /**
   * Returns the list of actions.
   * @return List of actions
   */
  ArrayList<Action> getActions();

  /**
   * Returns the amount of red.
   * @return Double - amount of red
   */
  double getRed();

  /**
   * Returns the amount of green.
   * @return Double - amount of green
   */
  double getGreen();

  /**
   * Returns the amount of blue.
   * @return Double - amount of blue
   */
  double getBlue();

  /**
   * Returns the position of the shape.
   * @return - the position
   */
  Pair<Double, Double> getPosition();

  /**
   * Returns the list of parameters of the shape.
   * @return - list of parameters
   */
  ArrayList<Pair<String, Double>> getParameters();

  /**
   * Returns the time at which the shape appears.
   * @return int - appear time
   */
  int getAppears();

  /**
   * Returns the time at which the shape disappears.
   * @return int - disappear time
   */
  int getDisappears();


  /**
   * Returns the shape's shape type.
   * @return - ShapeType
   */
  ShapeType getShapeType();

  /**
   * Returns the shape's position name.
   * @return - position name
   */
  String getPositionName();

  /**
   * Return's the visibility of the shape.
   * @return - boolean representing visibility
   */
  boolean getVisibility();

}
