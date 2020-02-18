package cs3500.animator.provider.model;

import java.awt.Point;
import java.awt.Color;
import java.util.ArrayList;

/**
 * Interface for animation model. Implemented by the Animation Model. Represents all the operations
 * needed for a working animation model.
 */
public interface AnimationOperations {

  /**
   * Gets the next phase (e.g. gamestate, next incrementation of t)
   * in the animation.  Updates the active commands and shapes,
   * executes active commands at that t, goes to next t.
   */
  void nextPhase(int tempo);

  /**
   * Given a list of shapes and commands, starts the animation
   * with those shapes and commands.
   * @param shape shapes starting in animation
   * @param command commands starting in animation
   */
  void startGame(ArrayList<Shape> shape, ArrayList<Command> command);

  /**
   * Returns string of all shapes and commands that will be present in
   * the animation.  Displays all info for shapes and commands.
   * @return String of shapes and commands in animation
   */
  String getReadBack(int tempo, String unit);

  /**
   * Adds a rectangle to the list of shapes in animation.
   * @param name name of shape
   * @param color color of shape
   * @param posn start location of shape
   * @param appearsAt time shape appears at
   * @param disappearAt time shape disappears at
   * @param width initial width of shape
   * @param height initial height of shape
   */
  void addRectangle(String name, Color color,
                    Point posn, int appearsAt, int disappearAt, double width, double height);

  /**
   * Adds an oval to the list of shapes in animation.
   * @param name name of shape
   * @param color color of shape
   * @param posn start location of shape
   * @param appearsAt time shape appears at
   * @param disappearAt time shape disappears at
   * @param xRadius initial x-radius of oval
   * @param yRadius initial y-radius of oval
   */
  void addOval(String name, Color color,
               Point posn, int appearsAt, int disappearAt, double xRadius, double yRadius);

  /**
   * Adds move command to list of commands in animation.
   * @param name name of shape command is being called on
   * @param startLoc start location of shape to be moved
   * @param destination destination of shape after move
   * @param startTime start time of command
   * @param finTime finish time of command
   */
  void addMoveCommand(String name, Point startLoc, Point destination, int startTime, int finTime);

  /**
   * Adds color change command to list of commands in animation.
   * @param name name of shape command is being called on
   * @param startColor start color of shape to be changed
   * @param newColor new color of shape after color change
   * @param startTime start time of command
   * @param finTime finish time of command
   */
  void addChangeColorCommand(String name, Color startColor, Color newColor,
                             int startTime, int finTime);

  /**
   * Adds scale command to list of commands in animation.
   * @param name name of shape command is being called on
   * @param startXDimension start X dimension of shape to be changed
   * @param newXDimension new X dimension after scale
   * @param startYDimension start X dimension of shape to be changed
   * @param newYDimension new Y dimension after scale
   * @param startTime start time of command
   * @param finTime finish time of command
   */
  void addScaleCommand(String name,
                       double startXDimension, double newXDimension,
                       double startYDimension, double newYDimension,
                       int startTime, int finTime);

  /**
   * Gets a copy of the list of shapes in the animation.
   * @return ArrayList of all shapes in animation
   */
  ArrayList<Shape> getListOfShapes();

  /**
   * Gets a copy of the list of commands in the animation.
   * @return ArrayList of all the commands in animation
   */
  ArrayList<Command> getCommands();

  /**
   * Returns true is the shape is in the list of active shapes.
   * @param s given Shape
   * @return whether or not the shape is active
   */
  boolean isActiveShape(Shape s);

  /**
   * Returns the list of active shapes.
   * @return the list of active shapes.
   */
  ArrayList<Shape> getListOfActiveShapes();

  /**
   * Returns the last end time in the list of shapes.
   * @return time of last shapes departure
   */
  int getLastEndTimeOfShapes();

  /**
   * Returns the current time.
   * @return the current time
   */
  int getT();

}