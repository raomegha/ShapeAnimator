package cs3500.hw05;

import java.util.ArrayList;
import javafx.util.Pair;

/**
 * Interface for animation model.
 */
public interface AnimationOperations {

  /**
   * Creates a shape given all the arguments and adds the shape to the list in the model.
   *
   * @param name          - The name of the shape
   * @param type          - The type of the shape
   * @param position      - The position of the shape
   * @param positionName  - The type of the shape
   * @param parameters    - The parameters of the shape
   * @param red           - The amount of red in the shape
   * @param green         - The amount of green in the shape
   * @param blue          - The amount of blue in the shape
   * @param appears       - The time the shape appears
   * @param disappears    - The time the shape disappears
   * @throws IllegalArgumentException - if the shape cannot be created
   */
  void createShape(String name, ShapeType type, Pair<Double, Double> position, String positionName,
                   ArrayList<Pair<String, Double>> parameters, double red, double green,
                   double blue, int appears, int disappears) throws IllegalArgumentException;


  /**
   * Stores an action in the model's actions list and in the respective shape's list.
   *
   * @param shapeName - The name of the shape on which the animation will be executed
   * @param a         - The action to be executed
   * @throws IllegalArgumentException if shapeName doesn't exist in model's shapes list or if action
   *                                  is overlapping the same actions of the shape
   */
  void storeAction(String shapeName, Action a) throws IllegalArgumentException;

  /**
   * Prints a list of all the shapes and the corresponding features associated with each shape. Each
   * shape will displays its name, type of shape, center point and parameters, and the points at
   * which it appears and disappears. After the shapes are listed, all the animations/actions are
   * listed in sequential order of how they are performed. Each action displays the name of the
   * shape, the action being performed, and the time interval in which it is being executed.
   */
  String readBack();

  /**
   * Animates the shape based on the given action.
   *
   * @param action - the action to apply on the shape.
   */
  void animate(Action action);

  /**
   * Returns the model's list of actions.
   * @return - An ArrayList of actions.
   */
  ArrayList<Action> getActions();

  /**
   * Returns the model's list of shapes.
   * @return - An ArrayList of shapes
   */
  ArrayList<Shape> getShapes();

  /**
   * Restarts the animation.
   */
  void restart();


  /**
   * Finds the given shape and changes its visibility.
   * @param name - The name of the shape.
   */
  void changeShapeVisibility(String name);

  //CHANGE
  /**
   * Gets the current background color.
   */
  ArrayList<Double> getBackground();

  void updateBackground(ArrayList<Double> newBackground);
}

