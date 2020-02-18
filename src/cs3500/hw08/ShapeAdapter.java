package cs3500.hw08;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Scanner;

import cs3500.animator.provider.model.Shape;
import cs3500.hw05.Action;
import cs3500.hw05.IShape;
import cs3500.hw05.ShapeType;
import javafx.util.Pair;

/**
 * Adapter class for our shape and provider's shape.
 */
public class ShapeAdapter extends Shape implements IShape {

  protected cs3500.hw05.Shape adaptee;

  /**
   * Constructs an instance of a ShapeAdapter.
   *
   * @param s - The given shape to be converted to provider's shape.
   */
  public ShapeAdapter(cs3500.hw05.Shape s) {
    super(s.getName(), new Color((int) (s.getRed() * 255), (int) (s.getGreen() * 255),
                    (int) (s.getBlue() * 255)), new Point(s.getPosition().getKey().intValue(),
                    s.getPosition().getValue().intValue()), s.getAppears(), s.getDisappears(),
            s.getParameters().get(0).getValue(), s.getParameters().get(1).getValue());
    switch (s.getShapeType()) {
      case RECTANGLE:
        this.type = "rectangle";
        break;
      case OVAL:
        this.type = "oval";
        break;
      default:
        this.type = "rectangle";
    }
    this.adaptee = s;
  }

  @Override
  public Shape makeCopy() {
    return new ShapeAdapter(new cs3500.hw05.Shape(this.adaptee));
  }

  @Override
  protected String getShapeInfo() {
    String shapeInfo = "";
    Scanner scan = new Scanner(this.adaptee.printShape());
    scan.nextLine();
    while (scan.hasNextLine()) {
      shapeInfo += scan.nextLine() + "\n";
    }

    return shapeInfo + "\n";
  }

  @Override
  public String getXAttributeName() {
    return this.adaptee.getParameters().get(0).getKey();
  }

  @Override
  public String getYAttributeName() {
    return this.adaptee.getParameters().get(1).getKey();
  }

  @Override
  public String toString(int tempo, String unit) {
    return this.adaptee.printShape() + "\n\n";
  }

  @Override
  public void move(Pair<Double, Double> endPos) {
    this.adaptee.move(endPos);
  }

  @Override
  public void changeColor(double newRed, double newGreen, double newBlue) {
    this.adaptee.changeColor(newRed, newGreen, newBlue);
  }

  @Override
  public void resize(ArrayList<Pair<String, Double>> newParms) {
    this.adaptee.resize(newParms);
  }

  @Override
  public String printShape() {
    return this.adaptee.printShape();
  }

  @Override
  public void addAction(Action a) {
    this.adaptee.addAction(a);
  }

  @Override
  public void reset() {
    this.adaptee.reset();
  }

  @Override
  public void changeVisibility() {
    this.adaptee.changeVisibility();
  }

  @Override
  public ArrayList<Action> getActions() {
    return null;
  }

  @Override
  public double getRed() {
    return this.adaptee.getRed();
  }

  @Override
  public double getGreen() {
    return this.adaptee.getGreen();
  }

  @Override
  public double getBlue() {
    return this.adaptee.getBlue();
  }

  @Override
  public Pair<Double, Double> getPosition() {
    return this.adaptee.getPosition();
  }

  @Override
  public ArrayList<Pair<String, Double>> getParameters() {
    return this.adaptee.getParameters();
  }

  @Override
  public int getAppears() {
    return this.adaptee.getAppears();
  }

  @Override
  public int getDisappears() {
    return this.adaptee.getDisappears();
  }

  @Override
  public ShapeType getShapeType() {
    return this.adaptee.getShapeType();
  }

  @Override
  public String getPositionName() {
    return this.adaptee.getPositionName();
  }

  @Override
  public boolean getVisibility() {
    return this.adaptee.getVisibility();
  }
}
