package cs3500.hw08;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import cs3500.animator.provider.model.AnimationOperations;
import cs3500.animator.provider.model.Command;
import cs3500.animator.provider.model.Shape;

/**
 * Represents an implementation of the provider's AnimationOperations interface. Needed to create
 * this class because the provider's hybrid view has a concrete reference to this class and they
 * could not change it. No actual implementation was involved.
 */
public class AnimationModel implements AnimationOperations {

  protected AnimationOperationsAdapter adaptee;

  /**
   * Constructs an instance of an AnimationModel.
   */
  public AnimationModel() {
    this.adaptee = new AnimationOperationsAdapter(new cs3500.hw05.AnimationModel());
  }

  @Override
  public void nextPhase(int tempo) {
    this.adaptee.nextPhase(tempo);
  }

  @Override
  public void startGame(ArrayList<Shape> shape, ArrayList<Command> command) {
    this.adaptee.startGame(shape, command);
  }

  @Override
  public String getReadBack(int tempo, String unit) {
    return this.adaptee.getReadBack(tempo, unit);
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
    return this.adaptee.getListOfShapes();
  }

  @Override
  public ArrayList<Command> getCommands() {
    return this.adaptee.getCommands();
  }

  @Override
  public boolean isActiveShape(Shape s) {
    return this.adaptee.isActiveShape(s);
  }

  @Override
  public ArrayList<Shape> getListOfActiveShapes() {
    return this.adaptee.getListOfActiveShapes();
  }

  @Override
  public int getLastEndTimeOfShapes() {
    return this.adaptee.getLastEndTimeOfShapes();
  }

  @Override
  public int getT() {
    return this.adaptee.getT();
  }
}
