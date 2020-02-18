package cs3500.hw08;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

import cs3500.animator.provider.model.Command;
import cs3500.animator.provider.model.Shape;
import cs3500.hw05.Action;
import cs3500.hw05.AnimationOperations;
import cs3500.hw05.ChangeColorAction;
import cs3500.hw05.MoveAction;
import cs3500.hw05.ResizeAction;
import cs3500.hw05.ShapeType;
import javafx.util.Pair;

/**
 * Adapter class for Animation Operations so provider model can work with our model.
 */
public class AnimationOperationsAdapter implements AnimationOperations,
        cs3500.animator.provider.model.AnimationOperations {

  protected AnimationOperations model;
  protected int time;

  /**
   * Constructs an instance of an AnimationOperationsAdapter.
   *
   * @param model - The given model.
   */
  public AnimationOperationsAdapter(AnimationOperations model) {
    this.model = model;
    this.time = 0;
  }

  @Override
  public void nextPhase(int tempo) {
    this.time += 1;

    ArrayList<Action> allActions = this.model.getActions();
    ArrayList<Action> filteredActions = new ArrayList<>();
    ArrayList<Action> actionLeft = new ArrayList<>();

    this.filterActions(allActions, filteredActions, actionLeft);

    Collections.sort(filteredActions);

    for (int i = 0; i < filteredActions.size(); i++) {
      Action a = filteredActions.get(i).update(time);
      this.model.animate(a);
    }

    ArrayList<cs3500.hw05.Shape> shapes = this.model.getShapes();

    filterShapes(shapes);
  }

  /**
   * Filters the actions that apply at the current time.
   *
   * @param allActions      - represents all the actions.
   * @param filteredActions - represents the filtered actions.
   * @param actionLeft      - represents the actions left to animate.
   */
  private void filterActions(ArrayList<Action> allActions, ArrayList<Action> filteredActions,
                             ArrayList<Action> actionLeft) {
    for (int i = 0; i < allActions.size(); i++) {
      Action a = allActions.get(i);
      if (this.time >= a.getStartTime() && this.time <= a.getEndTime()) {
        filteredActions.add(a);
      }
      if (a.getEndTime() >= this.time) {
        actionLeft.add(a);
      }
    }
  }

  /**
   * Filters out the shapes that do not exist at the certain time.
   *
   * @param shapes - The list of shapes in the model
   */
  private void filterShapes(ArrayList<cs3500.hw05.Shape> shapes) {
    if (this.time > 0) {
      for (int i = 0; i < shapes.size(); i++) {
        cs3500.hw05.Shape s = shapes.get(i);
        if (this.time < s.getAppears() || this.time > s.getDisappears() || !s.getVisibility()) {
          shapes.remove(i);
          i--;
        }
      }
    }
  }

  @Override
  public void startGame(ArrayList<Shape> shape, ArrayList<Command> command) {
    for (Shape x : shape) {
      ShapeAdapter s = (ShapeAdapter) x;
      this.model.createShape(s.getName(), s.getShapeType(), s.getPosition(), s.getPositionName(),
              s.getParameters(), s.getRed(), s.getGreen(), s.getBlue(), s.getAppears(),
              s.getDisappears());
    }

    for (Command x : command) {
      switch (x.getType()) {
        case "colorChange":
          ChangeColor c = (ChangeColor) x;
          ChangeColorAction cAction = new ChangeColorAction(x.getStartTime(), x.getFinTime(),
                  c.getColors().get(0), c.getColors().get(1),
                  c.getColors().get(2),
                  c.getColors().get(3),
                  c.getColors().get(4),
                  c.getColors().get(5));
          this.model.storeAction(c.getName(), cAction);
          break;
        case "move":
          Move m = (Move) x;
          MoveAction mAction = new MoveAction(m.getStartTime(), m.getFinTime(),
                  new Pair<Double, Double>((double) m.getStartLoc().getX(),
                          (double) m.getStartLoc().getY()),
                  new Pair<Double, Double>((double) m.getDestination().getX(),
                          (double) m.getDestination().getY()));
          this.model.storeAction(m.getName(), mAction);
          break;
        case "scale":
          Scale s = (Scale) x;
          ResizeAction rAction = new ResizeAction(s.getStartTime(), s.getFinTime(),
                  s.getOldParms(), s.getNewParms());
          this.model.storeAction(s.getName(), rAction);
          break;
        default:
          break;
      }
    }
  }

  @Override
  public String getReadBack(int tempo, String unit) {
    //unused provider's method
    return null;
  }

  @Override
  public void addRectangle(String name, Color color, Point posn, int appearsAt, int disappearAt,
                           double width, double height) {
    //unused provider's method
  }

  @Override
  public void addOval(String name, Color color, Point posn, int appearsAt, int disappearAt,
                      double xRadius, double yRadius) {
    //unused provider's method
  }

  @Override
  public void addMoveCommand(String name, Point startLoc, Point destination, int startTime,
                             int finTime) {
    //unused provider's method
  }

  @Override
  public void addChangeColorCommand(String name, Color startColor, Color newColor, int startTime,
                                    int finTime) {
    //unused provider's method
  }

  @Override
  public void addScaleCommand(String name, double startXDimension, double newXDimension,
                              double startYDimension, double newYDimension, int startTime,
                              int finTime) {
    //unused provider's method
  }

  @Override
  public ArrayList<Shape> getListOfShapes() {
    return this.getTargetShapes();
  }

  @Override
  public ArrayList<Command> getCommands() {
    return this.getTargetCommands();
  }

  @Override
  public boolean isActiveShape(Shape s) {
    return false;
  }

  @Override
  public ArrayList<Shape> getListOfActiveShapes() {
    return this.getTargetShapes();
  }

  @Override
  public int getLastEndTimeOfShapes() {

    int lastEndTime = 0;
    for (int i = 0; i < this.model.getShapes().size(); i++) {
      if (this.model.getShapes().get(i).getDisappears() > lastEndTime) {
        lastEndTime = this.model.getShapes().get(i).getDisappears();
      }
    }
    return lastEndTime;
  }

  @Override
  public int getT() {
    return this.time;
  }

  @Override
  public void createShape(String name, ShapeType type, Pair<Double, Double> position,
                          String positionName, ArrayList<Pair<String, Double>> parameters,
                          double red, double green, double blue, int appears, int disappears)
          throws IllegalArgumentException {
    this.model.createShape(name, type, position, positionName, parameters, red,
            green, blue, appears, disappears);
  }

  @Override
  public void storeAction(String shapeName, Action a) throws IllegalArgumentException {
    this.model.storeAction(shapeName, a);
  }

  @Override
  public String readBack() {
    return this.model.readBack();
  }

  @Override
  public void animate(Action action) {
    this.model.animate(action);
  }

  @Override
  public ArrayList<Action> getActions() {
    return this.model.getActions();
  }

  @Override
  public ArrayList<cs3500.hw05.Shape> getShapes() {
    return this.model.getShapes();
  }

  @Override
  public void restart() {
    this.model.restart();
  }

  @Override
  public void changeShapeVisibility(String name) {
    this.model.changeShapeVisibility(name);
  }

  @Override
  public ArrayList<Double> getBackground() {
    return null;
  }

  @Override
  public void updateBackground(ArrayList<Double> newBackground) {

  }

  /**
   * Returns all the active shapes in the model.
   *
   * @return - List of shapes.
   */
  private ArrayList<Shape> getTargetShapes() {
    ArrayList<Shape> targetShapes = new ArrayList<>();
    ArrayList<cs3500.hw05.Shape> adapteeShapes = this.model.getShapes();
    filterShapes(adapteeShapes);

    //converts shapes for provider's view
    for (int i = 0; i < adapteeShapes.size(); i++) {
      ShapeAdapter target = new ShapeAdapter(adapteeShapes.get(i));
      targetShapes.add(target);
    }

    return targetShapes;
  }

  /**
   * Returns all the active commands in the model.
   *
   * @return - List of commands.
   */
  private ArrayList<Command> getTargetCommands() {
    ArrayList<Command> targetCommands = new ArrayList<>();
    ArrayList<Action> adapteeCommands = this.model.getActions();
    for (int i = 0; i < adapteeCommands.size(); i++) {
      Action a = adapteeCommands.get(i);
      //converts the Action to commands for the provider view.
      switch (a.getClass().getName()) {
        case "cs3500.hw05.MoveAction":
          Move moveCommand = new Move((MoveAction) a);
          targetCommands.add(moveCommand);
          break;
        case "cs3500.hw05.ChangeColorAction":
          ChangeColor changeColor = new ChangeColor((ChangeColorAction) a);
          targetCommands.add(changeColor);
          break;
        case "cs3500.hw05.ResizeAction":
          Scale scale = new Scale((ResizeAction) a);
          targetCommands.add(scale);
          break;
        default:
          break;
      }
    }
    return targetCommands;
  }
}
