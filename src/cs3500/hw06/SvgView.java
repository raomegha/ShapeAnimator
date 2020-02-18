package cs3500.hw06;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import cs3500.hw05.Action;
import cs3500.hw05.ChangeColorAction;
import cs3500.hw05.MoveAction;
import cs3500.hw05.ResizeAction;
import cs3500.hw05.Shape;
import javafx.util.Pair;

/**
 * View that produces textual representation of animation for svg.
 */
public class SvgView implements IView {

  protected OutputStream out;
  protected Appendable app;
  protected int tempo;
  protected boolean isLooping;
  protected String loopback;
  protected ArrayList<Integer> background;


  /**
   * Constructs an instance of an SvgView.
   *
   * @param out   - The output stream
   * @param app   - The given appendable
   * @param tempo - The tempo of the animation
   * @throws IllegalArgumentException - if the output stream is null or appendable is null
   */
  public SvgView(OutputStream out, Appendable app, int tempo, boolean isLooping)
          throws IllegalArgumentException {
    if (out == null) {
      throw new IllegalArgumentException("Output streaam cannot be null");
    }

    if (app == null) {
      throw new IllegalArgumentException("Appendable cannot be null");
    }

    this.out = out;
    this.app = app;
    this.tempo = tempo;
    this.isLooping = isLooping;
    this.loopback = "base.begin+";
    this.background = new ArrayList<>(Arrays.asList(255, 255, 255));
  }

  //CHANGE
  /**
   * Constructs an instance of an SvgView with a specified background.
   *
   * @param out   - The output stream
   * @param app   - The given appendable
   * @param tempo - The tempo of the animation
   * @param background - The given background color
   * @throws IllegalArgumentException - if the output stream is null or appendable is null
   */
  public SvgView(OutputStream out, Appendable app, int tempo, boolean isLooping,
                 ArrayList<Integer> background)
          throws IllegalArgumentException {
    if (out == null) {
      throw new IllegalArgumentException("Output streaam cannot be null");
    }

    if (app == null) {
      throw new IllegalArgumentException("Appendable cannot be null");
    }

    this.out = out;
    this.app = app;
    this.tempo = tempo;
    this.isLooping = isLooping;
    this.loopback = "base.begin+";
    this.background = background;
  }


  /**
   * Adds the given rectangle shape and actions to the appendable.
   *
   * @param s - The given shape.
   */
  private void drawRectangle(Shape s) {

    String shapeInfo = String.format("<rect id=\"%s\" x=\"%d\" y=\"%d\" width=\"%d\" " +
                    "height=\"%d\" " + "fill=\"rgb(%d,%d,%d)\" visibility=\"hidden\" >\n"
                    + "    ", s.getName(), s.getPosition().getKey().intValue(),
            s.getPosition().getValue().intValue(), s.getParameters().get(0).getValue().intValue(),
            s.getParameters().get(1).getValue().intValue(),
            (int) (s.getRed() * 255), (int) (s.getGreen() * 255), (int) (s.getBlue() * 255));

    shapeInfo = shapeInfo.concat(appendShapeInfo(s));
    shapeInfo = shapeInfo.concat("    \n" + "</rect>\n" + "\n");

    this.append(shapeInfo);
  }

  /**
   * Adds the given oval shape and actions to the appendable.
   *
   * @param s - The given shape.
   */
  private void drawOval(Shape s) {
    String shapeInfo = String.format("<ellipse id=\"%s\" cx=\"%d\" cy=\"%d\" rx=\"%d\" ry=\"%d\" "
                    + "fill=\"rgb(%d,%d,%d)\" visibility=\"hidden\" >\n" + "    ", s.getName(),
            s.getPosition().getKey().intValue(), s.getPosition().getValue().intValue(),
            s.getParameters().get(0).getValue().intValue(),
            s.getParameters().get(1).getValue().intValue(), (int) (s.getRed() * 255),
            (int) (s.getGreen() * 255), (int) (s.getBlue() * 255));

    shapeInfo = shapeInfo.concat(appendShapeInfo(s));
    shapeInfo = shapeInfo.concat("\n" + "</ellipse>\n" + "\n");

    this.append(shapeInfo);

  }

  @Override
  public void drawShapes(ArrayList<Shape> shapes) {

    String svgStart = "<svg width=\"1000\" height=\"1000\" version=\"1.1\""
            + "     xmlns=\"http://www.w3.org/2000/svg\" style=\"background-color: rgb("
            + this.background.get(0) + "," + this.background.get(1) + ","
            + this.background.get(2) + ")\">\n" + "\n";

    this.append(svgStart);

    if (this.isLooping) {
      String loop = "    <rect>\n" +
              "        <animate id=\"base\" begin=\"0;base.end\" dur=\"5000.0ms\"" +
              " attributeName=\"visibility\" from=\"hide\" to=\"hide\"/>\n" +
              "    </rect>";
      this.append(loop);
    }

    //iterates through al the shape and writes the shape and its actions
    for (int i = 0; i < shapes.size(); i++) {
      Shape s = shapes.get(i);
      if (s.getVisibility()) {
        switch (s.getShapeType()) {
          case RECTANGLE:
            drawRectangle(s);
            break;
          case OVAL:
            drawOval(s);
            break;
          default:
            break;
        }
      }
    }

    String endSvg = "</svg>\n";
    this.append(endSvg);

    try {
      this.out.write(this.app.toString().getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Override
  public boolean isVisual() {
    return false;
  }

  @Override
  public void changeBackgroundColor(Color c) {
    //does nothing in this view
  }


  /**
   * Returns the string representing all the actions of the shape.
   *
   * @param s - The given shape info.
   */
  private String appendShapeInfo(Shape s) {
    String actionsInfo = appearSvg(s);
    for (int i = 0; i < s.getActions().size(); i++) {
      Action a = s.getActions().get(i);
      switch (a.getClass().getName()) {
        case "cs3500.hw05.MoveAction":
          actionsInfo = actionsInfo.concat(moveActionSvg((MoveAction) a, s));
          actionsInfo = actionsInfo.concat("    ");
          break;
        case "cs3500.hw05.ChangeColorAction":
          actionsInfo = actionsInfo.concat(changeColorActionSvg((ChangeColorAction) a));
          actionsInfo = actionsInfo.concat("    ");
          break;
        case "cs3500.hw05.ResizeAction":
          actionsInfo = actionsInfo.concat(resizeActionSvg((ResizeAction) a));
          actionsInfo = actionsInfo.concat("    ");
          break;
        default:
          break;
      }
    }
    return actionsInfo;
  }

  /**
   *Returns the the string representing the moveAction in svg.
   * @param a - the given move action
   * @param s - The shape the move action is applied on
   * @return - The string format for moveAction in svg
   */
  private String moveActionSvg(MoveAction a, Shape s) {
    String parm1 = "";
    String parm2 = "";
    int startTime = a.getStartTime() * 100 / tempo;
    int duration = (a.getEndTime() - a.getStartTime()) * 100 / tempo;
    int xFrom = a.getStartPos().getKey().intValue();
    int xTo = a.getEndPos().getKey().intValue();
    int yFrom = a.getStartPos().getValue().intValue();
    int yTo = a.getEndPos().getValue().intValue();

    switch (s.getShapeType()) {
      case RECTANGLE:
        parm1 = "x";
        parm2 = "y";
        break;
      case OVAL:
        parm1 = "cx";
        parm2 = "cy";
        break;
      default:
        break;
    }
    String base = this.loopback;
    if (!this.isLooping) {
      base = "";
    }

    String xMove = String.format("<animate attributeType=\"" +
            "xml\" begin=\"%s%dms\" dur=\"%dms\" attributeName=\"%s\" from=\"%d\" to=\"%d\"" +
            " fill=\"freeze\" />\n" + "    ", base, startTime, duration, parm1, xFrom, xTo);

    String yMove = String.format("<animate attributeType=\"" +
            "xml\" begin=\"%s%dms\" dur=\"%dms\" attributeName=\"%s\" from=\"%d\" to=\"%d\"" +
            " fill=\"freeze\" />\n", base, startTime, duration, parm2, yFrom, yTo);

    return xMove.concat(yMove);
  }

  /**
   * Returns the string representing the changeolor action in svg.
   *
   * @param a - The given ChangeColorAction
   * @return String - The svg code
   */
  private String changeColorActionSvg(ChangeColorAction a) {
    int startTime = a.getStartTime() * 100 / tempo;
    int duration = (a.getEndTime() - a.getStartTime()) * 100 / tempo;
    ArrayList<Double> colors = a.getColors();

    String base = this.loopback;
    if (!this.isLooping) {
      base = "";
    }
    return String.format("<animate attributeType=\"CSS\" begin=\"%s%dms\" dur=\"%dms\" " +
                    "attributeName=\"fill\" from=\"rgb(%d,%d,%d)\" to=\"rgb(%d,%d,%d)\" " +
                    " fill=\"freeze\" />\n", base, startTime, duration, (int) (colors.get(0) * 255),
            (int) (colors.get(1) * 255), (int) (colors.get(2) * 255), (int) (colors.get(3) * 255),
            (int) (colors.get(4) * 255), (int) (colors.get(5) * 255));
  }

  /**
   * Returns the string representing the resize action in svg.
   *
   * @param a - The given ResizeAction
   * @return String - the svg code
   */
  private String resizeActionSvg(ResizeAction a) {
    int begin = a.getStartTime() * 100 / tempo;
    int duration = (a.getEndTime() - a.getStartTime()) * 100 / tempo;
    ArrayList<Pair<String, Double>> oldParms = a.getOldParms();
    ArrayList<Pair<String, Double>> newParms = a.getNewParms();

    String base = this.loopback;
    if (!this.isLooping) {
      base = "";
    }
    String resizeParm1 = String.format("<animate attributeType=\"xml\" begin=\"%s%dms\" " +
                    "dur=\"%dms\"  attributeName=\"%s\" from=\"%d\" to=\"%d\" " +
                    "fill=\"freeze\"" + " />\n", base, begin, duration, oldParms.get(0).getKey(),
            oldParms.get(0).getValue().intValue(), newParms.get(0).getValue().intValue());
    String resizeParm2 = String.format("<animate attributeType=\"xml\" begin=\"%s%dms\" " +
                    "dur=\"%dms\"  attributeName=\"%s\" from=\"%d\" to=\"%d\" " +
                    "fill=\"freeze\"" + " />\n", base, begin, duration, oldParms.get(1).getKey(),
            oldParms.get(1).getValue().intValue(), newParms.get(1).getValue().intValue());

    return resizeParm1.concat(resizeParm2);
  }

  /**
   * Creates an animation for a shape to appear.
   *
   * @param s - The given shape
   * @return String - the svg code
   */
  private String appearSvg(Shape s) {
    int begin = s.getAppears() * 100 / tempo;
    int dur = (s.getDisappears() - s.getAppears()) * 100 / tempo;

    String base = this.loopback;
    if (!this.isLooping) {
      base = "";
    }

    String dissapears = String.format("<animate attributeType=\"xml\" begin=\"%s%dms\" " +
            "dur=\"%dms\"  attributeName=\"visibility\" from=\"hidden\" to=\"hidden\" " +
            "fill=\"freeze\"" + " />\n" + "    ", base, s.getDisappears() * 100 / tempo, 1);
    return dissapears.concat(String.format("<animate attributeType=\"xml\" begin=\"%s%dms\" " +
            "dur=\"%dms\"  attributeName=\"visibility\" from=\"visible\" to=\"visible\" " +
            "fill=\"freeze\"" + " />\n" + "    ", base, begin, dur));
  }

  /**
   * Appends the given string to the appendable.
   *
   * @param s - The given string.
   */
  private void append(String s) {
    for (int i = 0; i < s.length(); i++) {
      try {
        this.app.append(s.charAt(i));
      } catch (IOException e) {
        return;
      }
    }
  }
}


