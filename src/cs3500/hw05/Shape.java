package cs3500.hw05;

import java.util.ArrayList;
import java.util.Objects;

import javafx.util.Pair;

/**
 * A class that represents a shape and all the characteristics and features of the shape.
 */
public class Shape implements IShape {
  private String name;
  private ShapeType type;
  private Pair<Double, Double> position;
  private String positionName;
  private ArrayList<Pair<String, Double>> parameters;
  private double red;
  private double green;
  private double blue;
  private int appears;
  private int disappears;
  private ArrayList<Action> actions;
  private Shape original;
  private boolean isVisible;

  /**
   * Constructs a shape.
   *
   * @param name       - the name of the shape
   * @param type       - the type of shape
   * @param position   - the position of the shape
   * @param parameters - the specific parameters for the shape
   * @param red        - amount of red color
   * @param green      - amount of green color
   * @param blue       - amount of blue color
   * @param appears    - time at which shape appears
   * @param disappears - time at which shape disappears
   */
  public Shape(String name, ShapeType type, Pair<Double, Double> position, String positionName,
               ArrayList<Pair<String, Double>> parameters, double red, double green, double blue,
               int appears, int disappears)
          throws IllegalArgumentException {

    if (Objects.isNull(type)) {
      throw new IllegalArgumentException("Must have a position");
    }
    if (Objects.isNull(position)) {
      throw new IllegalArgumentException("Must have a position");
    }
    if (Objects.isNull(parameters)) {
      throw new IllegalArgumentException("Must have atleast one parameter");
    }

    if (disappears <= appears) {
      throw new IllegalArgumentException("Shape must appear before disappear");
    }

    if (name.isEmpty()) {
      throw new IllegalArgumentException("Shape must have a name");
    }

    this.name = name;
    this.type = type;
    this.position = position;
    this.positionName = positionName;
    this.parameters = parameters;
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.appears = appears;
    this.disappears = disappears;
    this.actions = new ArrayList<>();
    this.original = new Shape(this);
    this.isVisible = true;

  }

  /**
   * Copy constructor for Shape.
   * @param clone - The given Shape to clone
   */
  public Shape(Shape clone) {
    this.name = clone.getName();
    this.type = clone.getShapeType();
    this.position = clone.getPosition();
    this.positionName = clone.getPositionName();
    this.parameters = clone.getParameters();
    this.red = clone.getRed();
    this.green = clone.getGreen();
    this.blue = clone.getBlue();
    this.appears = clone.getAppears();
    this.disappears = clone.getDisappears();
    this.actions = new ArrayList<>();
  }

  @Override
  public void move(Pair<Double, Double> endPos) {
    this.position = endPos;
  }

  @Override
  public void changeColor(double newRed, double newGreen,
                          double newBlue) {
    this.red = newRed;
    this.green = newGreen;
    this.blue = newBlue;
  }

  @Override
  public void resize(ArrayList<Pair<String, Double>> newParms) {
    for (int i = 0; i < this.parameters.size() - 1; i++) {
      this.parameters.set(i, newParms.get(i));
    }
  }

  @Override
  public String printShape() {
    String ans = "";
    ans = ans.concat("Name: " + this.name + "\n"
            + "Type: " + this.type.toString().toLowerCase() + "\n"
            + this.positionName + ": (" + this.position.getKey() + "," + this.position.getValue()
            + "), ");

    String parms = "";
    //returns all the parameters and values of the shape
    for (int i = 0; i < parameters.size(); i++) {
      parms = parms.concat(parameters.get(i).getKey() + ": " + parameters.get(i).getValue() + ", ");
    }
    ans = ans.concat(parms);

    String colors = "Color: (" + this.red + "," + this.green + "," + this.blue + ")\n";
    ans = ans.concat(colors);

    ans = ans.concat("Appears at t=" + this.appears + "\n"
            + "Disappears at t=" + this.disappears);
    return ans;
  }

  @Override
  public void addAction(Action a) {
    this.actions.add(a);
  }

  @Override
  public void reset() {
    this.position = this.original.getPosition();
    this.parameters = this.original.getParameters();
    this.red = this.original.red;
    this.green = this.original.green;
    this.blue = this.original.blue;
  }

  @Override
  public void changeVisibility() {
    this.isVisible = !this.isVisible;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public ArrayList<Action> getActions() {
    return (ArrayList<Action>) this.actions.clone();
  }

  @Override
  public double getRed() {
    return this.red;
  }

  @Override
  public double getGreen() {
    return this.green;
  }

  @Override
  public double getBlue() {
    return this.blue;
  }

  @Override
  public Pair<Double, Double> getPosition() {
    return this.position;
  }

  @Override
  public ArrayList<Pair<String, Double>> getParameters() {
    return (ArrayList<Pair<String, Double>>) this.parameters.clone();
  }

  @Override
  public int getAppears() {
    return this.appears;
  }

  @Override
  public int getDisappears() {
    return this.disappears;
  }

  @Override
  public ShapeType getShapeType() {
    return this.type;
  }

  @Override
  public String getPositionName() {
    return this.positionName;
  }

  @Override
  public boolean getVisibility() {
    return this.isVisible;
  }

}
