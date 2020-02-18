package cs3500.hw08;

/**
 * Class that represents a position.
 */
public class Posn {

  protected int x;
  protected int y;

  /**
   * Creates an instance of a Posn.
   *
   * @param x - integer to represent the x position.
   * @param y - integer to represent the y position.
   */
  public Posn(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Getter for x position.
   *
   * @return - int representing the x posn
   */
  public int getX() {
    return this.x;
  }

  /**
   * Getter for y position.
   *
   * @return - int representing the y posn
   */
  public int getY() {
    return this.y;
  }
}
