package cs3500.animator.provider.model;

import java.awt.Point;
import java.awt.Color;
import java.util.Objects;

/**
 * Abstract class thats holds general shape info.
 */
public abstract class Shape {

  protected String name;
  protected String type;
  protected Color color;
  protected Point posn;
  protected int appearsAt;
  protected int disappearAt;
  protected double xSizeAttribute;
  protected double ySizeAttribute;

  /**
   * Constructs an abstract shape and takes in necessary info to construct.
   * @param name name of shape
   * @param color color of shape
   * @param posn start location of shape
   * @param appearsAt time shape appears at
   * @param disappearAt time shape disappears at
   */
  public Shape(String name, Color color,
               Point posn, int appearsAt, int disappearAt,
               double xSizeAttribute, double ySizeAttribute) {
    this.name = name;
    this.color = color;
    this.posn = posn;
    this.appearsAt = appearsAt;
    this.disappearAt = disappearAt;
    this.xSizeAttribute = xSizeAttribute;
    this.ySizeAttribute = ySizeAttribute;
    checkIfInvalid(name, appearsAt, disappearAt, xSizeAttribute, ySizeAttribute);
  }

  /**
   * Returns a copy of the shape.
   * @return a copy of the shape
   */
  public abstract Shape makeCopy();

  /**
   * Returns a copy of the shape's name.
   * @return a copy of the shape's name.
   */
  public String getName() {
    String copyOfName = this.name;
    return copyOfName;
  }

  /**
   * Returns a copy of the shape's posn.
   * @return a Point that represents this shape's posn.
   */
  public Point getPoint() {
    Point copyOfPoint = new Point((int) this.posn.getX(), (int) this.posn.getY());
    return copyOfPoint;
  }

  /**
   * Returns a copy of the shape's xSizeAttribute.
   * @return a double that represents the shape's xSizeAttribute
   */
  public double getXAtt() {
    return this.xSizeAttribute;
  }

  /**
   * Returns a copy of the shape's ySizeAttribute.
   * @return a double that represents the shape's ySizeAttribute
   */
  public double getYAtt() {
    return this.ySizeAttribute;
  }

  /**
   * Returns a copy of the shape's color.
   * @return a Color that represents this shape's color.
   */
  public Color getColor() {
    Color copyOfColor = new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue());
    return copyOfColor;
  }

  /**
   * Returns a copy of this shape's type.
   * @return a String that represents this shape's type.
   */
  public String getType() {
    String copyType = this.type;
    return copyType;
  }

  /**
   * Mutates posn of this shape.
   * @param destination New posn of shape
   */
  protected void move(Point destination) {
    this.posn = destination;
  }

  /**
   * Mutates color of this shape.
   * @param newColor New color of shape
   */
  protected void changeColor(Color newColor) {
    this.color = newColor;
  }

  /**
   * Mutates dimension of this shape.
   * @param newXDim new value of X dimension
   * @param newYDim new value of Y dimension
   */
  protected void scale(double newXDim, double newYDim) {
    this.xSizeAttribute = newXDim;
    this.ySizeAttribute = newYDim;
  }

  /**
   * Checks if parameters for shape being created are valid.
   * Checks if name is null.
   * Checks if appears and disappears is greater than 0.
   * Checks if disappear time is greater than or equal to appear time.
   * @param name name of shape
   * @param appearsAt time shape appears
   * @param disappearAt time shape disappears
   * @param xSizeAttribute X size of shape
   * @param ySizeAttribute Y size of shape
   */
  private void checkIfInvalid(String name, int appearsAt, int disappearAt, double xSizeAttribute,
                              double ySizeAttribute) {
    if (name == null) {
      throw new IllegalArgumentException("Shape name cannot be null.");
    } else if (appearsAt < 0 || disappearAt < 0) {
      throw new IllegalArgumentException("Shape appear and disappear times cannot be negative.");
    } else if (appearsAt > disappearAt) {
      throw new IllegalArgumentException("Shape disappear after it appears.");
    } else if (xSizeAttribute <= 0 || ySizeAttribute <= 0) {
      throw new IllegalArgumentException("X val and Y val must be greater than 0.");
    }
  }


  @Override
  public boolean equals(Object o) {
    return o instanceof Shape && this.name.equals(((Shape) o).name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.name);
  }


  /**
   * Create a String that describes the shape.
   * @param tempo tempo for the animation
   * @param unit unit for time
   * @return a description of the shape
   */
  public String toString(int tempo, String unit) {
    String shapeReadBack = "";

    shapeReadBack = shapeReadBack
        + "Name: " + name + "\n"
        + this.getShapeInfo() + "Color: (" + color.getRed() + ","
        + color.getGreen() + "," + color.getBlue() + ")" + "\n"
        + "Appears at t=" + (appearsAt / tempo) + unit + "\n"
        + "Disappears at t=" + (disappearAt / tempo) + unit + "\n\n";
    return shapeReadBack;
  }

  /**
   * Gets shape info from this shape.
   * @return String of shape info
   */
  protected abstract String getShapeInfo();

  /**
   * Return a copy of the shape's xAttributeName.
   * @return a copy of the shape's xAttributeName
   */
  public abstract String getXAttributeName();

  /**
   * Return a copy of the shape's yAttributeName.
   * @return a copy of the shape's yAttributeName
   */
  public abstract String getYAttributeName();
}

