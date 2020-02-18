package cs3500.hw05;

import java.util.Objects;

/**
 * Represents a single animation move.
 */
public abstract class Action implements Comparable<Action> {
  protected Shape shape;
  protected int startTime;
  protected int endTime;

  /**
   * Creates an animation move.
   *
   * @param startTime - the time when the animation move starts
   * @param endTime   - the time when the animation move ends
   */
  public Action(int startTime, int endTime) throws IllegalArgumentException {
    if (endTime <= startTime) {
      throw new IllegalArgumentException("End time must start after start time");
    }

    this.shape = null;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  /**
   * Applies the animation to the shape.
   */
  public abstract void apply();

  /**
   * Prints the animation.
   */
  public abstract String printAction();

  /**
   * Getter for start time.
   *
   * @return int - start time
   */
  public int getStartTime() {
    return this.startTime;
  }

  /**
   * Assigns the given shape to the shape field.
   *
   * @param s - the given shape
   */
  public void saveShape(Shape s) throws IllegalArgumentException {
    if (Objects.isNull(s)) {
      throw new IllegalArgumentException("Shape cannot be null");
    }
    this.shape = s;
  }

  /**
   * Getter for end time.
   *
   * @return int - end time
   */
  public int getEndTime() {
    return this.endTime;
  }

  /**
   * Getter for shape name.
   * @return - String representing the shape name.
   */
  public String getShapeName() {
    return this.shape.getName();
  }

  /**
   * Method that will tween the action in each separate class.
   *
   * @param time - represents the time.
   * @return - an Action that is tweened.
   */
  public abstract Action update(double time);

  /**
   * Method that will calculate the new value.
   *
   * @param time - represents the time.
   * @param a    - represents the value a at time t(a).
   * @param b    - represents the value b at time t(b).
   * @return - returns a double representing the intermediate stage.
   */
  public double tweening(double time, double a, double b) {
    double aTime = (this.endTime - time) / (this.endTime - this.startTime);
    double bTime = (time - this.startTime) / (this.endTime - this.startTime);
    return (a * aTime) + (b * bTime);
  }

  /**
   * Comparator for action class based on startTime.
   *
   * @param other - the action to be compared to
   * @return an integer representing if both actions are same or not.
   */
  @Override
  public int compareTo(Action other) {
    if (this.startTime == other.startTime) {
      return 0;
    } else if (this.startTime > other.startTime) {
      return 1;
    } else {
      return -1;
    }
  }

}
