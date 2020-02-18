package cs3500.hw06;

import java.util.ArrayList;

import cs3500.hw05.Action;
import cs3500.hw05.AnimationModel;
import cs3500.hw05.ChangeColorAction;
import cs3500.hw05.MoveAction;
import cs3500.hw05.ResizeAction;
import cs3500.hw05.Shape;
import cs3500.hw05.ShapeType;
import javafx.util.Pair;

/**
 * Builds the model given data parsed from FileReader.
 */
public class TweenAnimationModelBuilder implements TweenModelBuilder<AnimationModel> {

  protected AnimationModel model;

  /**
   * Creates an instance of a TweenAnimationModelBuilder.
   * @param model - The given model to build on.
   * @throws IllegalArgumentException if model is null
   */
  public TweenAnimationModelBuilder(AnimationModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
  }

  @Override
  public TweenModelBuilder<AnimationModel> addOval(String name, float cx, float cy, float xRadius,
                                                   float yRadius, int zValue, float red,
                                                   float green, float blue, int startOfLife,
                                                   int endOfLife) {

    Pair<Double, Double> position = new Pair((double) cx, (double) cy);
    ArrayList<Pair<String, Double>> parms = new ArrayList<>();
    parms.add(new Pair("xRadius", (double) xRadius));
    parms.add(new Pair("yRadius", (double) yRadius));
    //CHANGE
    parms.add(new Pair("zValue", (double) zValue));

    this.model.createShape(name, ShapeType.OVAL, position, "Center", parms, red, green,
            blue, startOfLife, endOfLife);

    return this;
  }

  @Override
  public TweenModelBuilder<AnimationModel> addRectangle(String name, float lx, float ly,
                                                        float width, float height, int zValue,
                                                        float red, float green, float blue,
                                                        int startOfLife, int endOfLife) {
    Pair<Double, Double> position = new Pair((double) lx, (double) ly);
    ArrayList<Pair<String, Double>> parms = new ArrayList<>();
    parms.add(new Pair("width", (double) width));
    parms.add(new Pair("height", (double) height));
    //CHANGE -
    parms.add(new Pair("zValue", (double) zValue));


    this.model.createShape(name, ShapeType.RECTANGLE, position, "Lower-left",
            parms, red, green, blue, startOfLife, endOfLife);


    return this;

  }

  @Override
  public TweenModelBuilder<AnimationModel> addMove(String name, float moveFromX, float moveFromY,
                                                   float moveToX, float moveToY, int startTime,
                                                   int endTime) {
    Pair<Double, Double> startPos = new Pair((double) moveFromX, (double) moveFromY);
    Pair<Double, Double> endPos = new Pair((double) moveToX, (double) moveToY);
    Action newMove = new MoveAction(startTime, endTime, startPos, endPos);

    this.model.storeAction(name, newMove);

    return this;
  }

  @Override
  public TweenModelBuilder<AnimationModel> addColorChange(String name, float oldR, float oldG,
                                                          float oldB, float newR, float newG,
                                                          float newB, int startTime, int endTime) {

    Action newColor = new ChangeColorAction(startTime, endTime, (double) oldR,
            (double) oldG, (double) oldB, (double) newR, (double) newG, (double) newB);
    this.model.storeAction(name, newColor);

    return this;
  }

  @Override
  public TweenModelBuilder<AnimationModel> addScaleToChange(String name, float fromSx, float fromSy,
                                                            float toSx, float toSy, int startTime,
                                                            int endTime) {
    ArrayList<Shape> shapes = this.model.getShapes();
    ShapeType type = ShapeType.RECTANGLE;
    String parm1Name = "";
    String parm2Name = "";

    for (int i = 0; i < shapes.size(); i++) {
      if (shapes.get(i).getName().compareTo(name) == 0) {
        type = shapes.get(i).getShapeType();
      }
    }

    switch (type) {
      case RECTANGLE:
        parm1Name = "width";
        parm2Name = "height";
        break;
      case OVAL:
        parm1Name = "xRadius";
        parm2Name = "yRadius";
        break;
      default:
        break;
    }

    ArrayList<Pair<String, Double>> ogParms = new ArrayList<>();
    ogParms.add(new Pair(parm1Name, (double) fromSx));
    ogParms.add(new Pair(parm2Name, (double) fromSy));

    ArrayList<Pair<String, Double>> newParms = new ArrayList<>();
    newParms.add(new Pair(parm1Name, (double) toSx));
    newParms.add(new Pair(parm2Name, (double) toSy));

    Action resize = new ResizeAction(startTime, endTime, ogParms, newParms);

    this.model.storeAction(name, resize);

    return this;
  }

  @Override
  public AnimationModel build() {
    return new AnimationModel(this.model);
  }

}
