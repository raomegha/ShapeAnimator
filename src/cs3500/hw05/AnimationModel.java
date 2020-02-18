package cs3500.hw05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import javafx.util.Pair;

/**
 * Represents an animation model.
 */
public class AnimationModel implements AnimationOperations {
  protected ArrayList<Shape> shapes;
  protected ArrayList<Action> actions;
  //CHANGE
  protected ArrayList<Double> background;

  /**
   * Constructs an instance of an animation model.
   */
  public AnimationModel() {
    this.shapes = new ArrayList<>();
    this.actions = new ArrayList<>();
    this.background = new ArrayList<>(Arrays.asList(1.0, 1.0, 1.0));
//    ArrayList<Pair<String, Double>> parmsList1 = new ArrayList<>();
//    Pair<String, Double> width = new Pair("Width", 8000.0);
//    Pair<String, Double> height = new Pair("Height", 8000.0);
//    Pair<String, Double> zValue = new Pair("zValue", 0.0);
//
//    parmsList1.add(width);
//    parmsList1.add(height);
//    parmsList1.add(zValue);
//    this.background = new Shape("animationBackground", ShapeType.RECTANGLE, new Pair(-600.0, -350.0),
//              "Lower-left corner", parmsList1, 1, 1, 1, 0,
//              200);
//      this.shapes.add(background);
  }

  /**
   * Copy constructor for AnimationModel.
   *
   * @param clone - The given AnimmationOperations to clone
   */
  public AnimationModel(AnimationOperations clone) {
    this.shapes = clone.getShapes();
    this.actions = clone.getActions();
    this.background = clone.getBackground();
  }

  @Override
  public void createShape(String name, ShapeType type, Pair<Double, Double> position,
                          String positionName, ArrayList<Pair<String, Double>> parameters,
                          double red, double green, double blue, int appears, int disappears)
          throws IllegalArgumentException {

    Shape s = new Shape(name, type, position, positionName, parameters, red, green,
            blue, appears, disappears);

    if (name.equals("animationBackground")) {
      this.background = new ArrayList<>(Arrays.asList(red, green, blue));
    }
    else {
      for (int i = 0; i < this.shapes.size(); i++) {
        if (this.shapes.get(i).getName().compareTo(name) == 0) {
          throw new IllegalArgumentException("Cannot add shape with same name");
        }
      }
      this.shapes.add(s);
    }
  }

  @Override
  public void storeAction(String shapeName, Action a) throws IllegalArgumentException {

    if (Objects.isNull(a)) {
      throw new IllegalArgumentException("Action cannot be null");
    }

    Shape s = this.findShape(shapeName);
    if (a.getStartTime() < s.getAppears() || a.getEndTime() > s.getDisappears()) {
      throw new IllegalArgumentException("Action must be within the life of the shape");
    }

    if (this.isOverLapping(s.getActions(), a)) {
      throw new IllegalArgumentException("Invalid animation,  cannot have two incompatible "
              + "moves for the same shape during overlapping time intervals");
    }

    a.saveShape(s);
    s.addAction(a);
    this.actions.add(a);
  }

  @Override
  public String readBack() {
    String ans = "Shapes: \n";
    //prints out all the shapes
    for (int i = 0; i < this.shapes.size(); i++) {
      ans = ans.concat(this.shapes.get(i).printShape() + "\n");
      if (i < this.shapes.size() - 1) {
        ans = ans.concat("\n");
      }
    }

    if (actions.size() > 0) {
      ans = ans.concat("\n");
    }

    //prints out all the actions
    for (int i = 0; i < actions.size(); i++) {
      ans = ans.concat(actions.get(i).printAction());
      if (i < actions.size() - 1) {
        ans = ans.concat("\n");
      }
    }
    return ans;
  }

  @Override
  public void animate(Action action) {
    if (Objects.isNull(action)) {
      throw new IllegalArgumentException("Action cannot be null");
    }
    action.apply();
  }

  @Override
  public ArrayList<Action> getActions() {
    return (ArrayList<Action>) this.actions.clone();
  }

  @Override
  public ArrayList<Shape> getShapes() {
    return (ArrayList<Shape>) this.shapes.clone();
  }

  @Override
  public void restart() {
    for (int i = 0; i < this.shapes.size(); i++) {
      this.shapes.get(i).reset();
    }
  }

  @Override
  public void changeShapeVisibility(String name) {
    Shape s = this.findShape(name);
    s.changeVisibility();
  }

  @Override
  public ArrayList<Double> getBackground() {
    return new ArrayList<>(Arrays.asList(this.background.get(0),
            this.background.get(1), this.background.get(2)));
  }

  @Override
  public void updateBackground(ArrayList<Double> newBackground) {
    this.background = newBackground;
  }


  /**
   * Finds the shape in the model list given the shape name.
   *
   * @param name - the name of the shape
   * @return returns the found shape
   * @throws IllegalArgumentException if shape is not found
   */
  private Shape findShape(String name) throws IllegalArgumentException {
    for (int i = 0; i < this.shapes.size(); i++) {
      if (this.shapes.get(i).getName().compareTo(name) == 0) {
        return this.shapes.get(i);
      }
    }
    throw new IllegalArgumentException("Shape cannot be found");
  }

  /**
   * Checks if the action overlaps with an action of the shape.
   *
   * @param actions - List of actions of the shape
   * @param action  - The action to be added
   * @return true if action is overlapping else returns false
   */
  private boolean isOverLapping(ArrayList<Action> actions, Action action) {
    for (int i = 0; i < actions.size(); i++) {
      if (action.getClass().getName().compareTo(actions.get(i).getClass().getName()) == 0) {
        Action oldAction = actions.get(i);
        if (!(action.startTime > oldAction.endTime || action.endTime < oldAction.startTime)) {
          return true;
        }
      }
    }
    return false;
  }
}
