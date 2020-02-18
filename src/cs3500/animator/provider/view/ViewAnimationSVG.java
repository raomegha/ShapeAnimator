package cs3500.animator.provider.view;

import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

import cs3500.animator.provider.model.AnimationOperations;
import cs3500.animator.provider.model.Command;
import cs3500.animator.provider.model.Shape;
import cs3500.hw08.ChangeColor;
import cs3500.hw08.Move;
import cs3500.hw08.Scale;

/**
 * Creates the SVG view.  This view either creates a new SVG file with the svg script for the
 * animation or displays svg script in the window.
 */
public class ViewAnimationSVG implements IView {

  private final int tempo;
  private AnimationOperations model;
  private String out = "";
  protected Appendable ap;
  private boolean loopingIsOn;
  private ArrayList<String> invisibleShapeNames;

  /**
   * Construct the SVG view. Initialize the model, tempo and output for the view.
   * @param model animation model
   * @param tempo tempo for the animation
   * @param ap output for the view
   */
  public ViewAnimationSVG(AnimationOperations model, int tempo, Appendable ap) {
    this.model = model;
    this.tempo = tempo;
    this.out = out;
    this.ap = ap;
    this.loopingIsOn = false;
    this.invisibleShapeNames = new ArrayList<>();
  }

  /**
   * Construct the SVG view. Initialize the model, tempo and output for the view.
   * Also stores information necessary for looping/invisible shapes.
   * @param model animation model
   * @param tempo tempo for animation
   * @param ap output for view
   * @param loopingIsOn boolean for whether SVG should loop
   * @param invisibleShapeNames shapes that should not be in SVG
   */
  public ViewAnimationSVG(AnimationOperations model, int tempo, Appendable ap, boolean loopingIsOn,
                          ArrayList<String> invisibleShapeNames) {
    this.model = model;
    this.tempo = tempo;
    this.ap = ap;
    this.loopingIsOn = loopingIsOn;
    this.invisibleShapeNames = invisibleShapeNames;
  }

  @Override
  public void startView() {
    ArrayList<Shape> shapes = model.getListOfShapes();
    ArrayList<Command> commands = model.getCommands();
    String resultSVG = "";
    for (Shape s : shapes) {
      if (!invisibleShapeNames.contains(s.getName())) {
        String shapeSVG = "";
        String commandsForSSVG = "";
        ArrayList<Command> commandsForS = new ArrayList<>();
        for (Command c : commands) {
          if (c.getName().equals(s.getName())) {
            commandsForS.add(c);
          }
        }
        String type = "";
        String xAttName = "";
        String yAttName = "";
        String xCoordName = "";
        String yCoordName = "";
        String isActive = "visible";
        if (s.getType().equals("rectangle")) {
          type = "rect";
          xAttName = "width";
          yAttName = "height";
          xCoordName = "x";
          yCoordName = "y";

        }
        if (s.getType().equals("oval")) {
          type = "ellipse";
          xAttName = "rx";
          yAttName = "ry";
          xCoordName = "cx";
          yCoordName = "cy";
        }
        shapeSVG = "\n <" + type + " id=\"" + s.getName() + "\" " + xCoordName + "=\""
                + s.getPoint().getX() + "\" " + yCoordName + "=\"" + s.getPoint().getY()
                + "\" " + xAttName + "=\"" + s.getXAtt() + "\" " + yAttName + "=\"" + s.getYAtt()
                + "\" fill=\"rgb(" + s.getColor().getRed() + "," + s.getColor().getGreen()
                + "," + s.getColor().getBlue() + ")\" visibility=\"" + isActive + "\" >";
        for (Command c : commandsForS) {
          if (c.getType().equals("colorChange")) {
            if (loopingIsOn) {
              commandsForSSVG = commandsForSSVG
                      + this.getSVGForChangeColorWithLooping((ChangeColor) c, s);
            } else {
              commandsForSSVG = commandsForSSVG + this.getSVGForChangeColor((ChangeColor) c, s);
            }
          }
          if (c.getType().equals("move")) {
            if (loopingIsOn) {
              commandsForSSVG = commandsForSSVG + this.getSVGForMoveWithLooping((Move) c, s);
            } else {
              commandsForSSVG = commandsForSSVG + this.getSVGForMove((Move) c, s);
            }
          }
          if (c.getType().equals("scale")) {
            if (loopingIsOn) {
              commandsForSSVG = commandsForSSVG + this.getSVGForScaleWithLooping((Scale) c, s);
            } else {
              commandsForSSVG = commandsForSSVG + this.getSVGForScale((Scale) c, s);
            }
          }
        }
        shapeSVG = shapeSVG + commandsForSSVG + " </" + type + ">";
        resultSVG = resultSVG + shapeSVG;
      }
    }
    String dummyRectangle = "";
    double animationDuration = ((double) model.getLastEndTimeOfShapes()
            / (double) this.tempo) * 1000;
    if (loopingIsOn) {
      dummyRectangle = "\n <rect> \n <animate id=\"base\" begin=\"0;base.end\" dur=\""
              + animationDuration + "ms\" attributeName=\"visibility\" from=\"hide\" to=\"hide\"/> "
              + "</rect>";
    }
    resultSVG = "<svg width=\"1500\" height=\"1500\" version=\"1.1\""
            + " xmlns=\"http://www.w3.org/2000/svg\">" + dummyRectangle + resultSVG + "</svg>";

    try {
      ap.append(resultSVG);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  @Override
  public void setPause() {
    // do nothing
  }

  /**
   * Create the svg script for the a changeColor command.
   * @param c the Changecolor command
   * @param s the shape the command is changing
   * @return the SVG script
   */
  private String getSVGForChangeColor(ChangeColor c, Shape s) {
    double startTime = ((double) c.getStartTime() / (double) this.tempo) * 1000;
    double duration = ((double) (c.getFinTime() - c.getStartTime()) / (double) this.tempo) * 1000;
    return "\n <animate attributeType=\"css\" begin=\"" + startTime + "ms\" dur=\"" + duration
            + "ms\" attributeName=\"fill\" from=\"rgb(" + c.getStartColor().getRed() + ","
            + c.getStartColor().getGreen() + "," + c.getStartColor().getBlue()
            + ")\" to=\"rgb(" + c.getNewColor().getRed() + "," + c.getNewColor().getGreen()
            + "," + c.getNewColor().getBlue() + ")\" fill=\"freeze\" />";
  }

  /**
   * Create the svg script for a move command.
   * @param m the move command
   * @param s the shape the command is changing
   * @return the SVG script
   */
  private String getSVGForMove(Move m, Shape s) {
    double startTime = ((double) m.getStartTime() / (double) this.tempo) * 1000;
    double duration = ((double) (m.getFinTime() - m.getStartTime()) / (double) this.tempo) * 1000;
    String xCoordName = "";
    String yCoordName = "";
    if (s.getType().equals("oval")) {
      xCoordName = "cx";
      yCoordName = "cy";
    }
    if (s.getType().equals("rectangle")) {
      xCoordName = "x";
      yCoordName = "y";
    }
    return "\n <animate attributeType=\"css\" begin=\"" + startTime + "ms\" dur=\"" + duration
            + "ms\" attributeName=" + "\"" + xCoordName + "\"" + " from=\"" + m.getStartLoc().getX()
            + "\" to=\"" + m.getDestination().getX() + "\" fill=\"freeze\" />"
            + "\n <animate attributeType=\"css\" begin=\"" + startTime + "ms\" dur=\"" + duration
            + "ms\" attributeName=\"" + yCoordName + "\" from=\"" + m.getStartLoc().getY()
            + "\" to=\"" + m.getDestination().getY() + "\" fill=\"freeze\" />";
  }

  /**
   * Create the svg script for a scale command.
   * @param scale the scale command
   * @param s the shape the command is changing
   * @return the SVG script
   */
  private String getSVGForScale(Scale scale, Shape s) {
    double startTime = ((double) scale.getStartTime() / (double) this.tempo) * 1000;
    double duration = ((double) (scale.getFinTime() - scale.getStartTime()) / (double) this.tempo)
            * 1000;
    String xAttName = "";
    String yAttName = "";
    if (s.getType().equals("oval")) {
      xAttName = "rx";
      yAttName = "ry";
    }
    if (s.getType().equals("rectangle")) {
      xAttName = "width";
      yAttName = "height";
    }
    return "\n <animate attributeType=\"css\" begin=\"" + startTime + "ms\" dur=\"" + duration
            + "ms\" attributeName=\"" + xAttName + "\" from=\"" + scale.getStartXDimension()
            + "\" to=\"" + scale.getNewXDimension() + "\" fill=\"freeze\" />"
            + "\n <animate attributeType=\"css\" begin=\"" + startTime + "ms\" dur=\"" + duration
            + "ms\" attributeName=" + "\"" + yAttName + "\"" + " from=\""
            + scale.getStartYDimension() + "\" to=\"" + scale.getNewYDimension()
            + "\" fill=\"freeze\" />";
  }

  private String getSVGForChangeColorWithLooping(ChangeColor c, Shape s) {
    double startTime = ((double) c.getStartTime() / (double) this.tempo) * 1000;
    double duration = ((double) (c.getFinTime() - c.getStartTime()) / (double) this.tempo) * 1000;
    return "\n <animate attributeType=\"css\" begin=\"base.begin+" + startTime + "ms\" dur=\""
            + duration
            + "ms\" attributeName=\"fill\" from=\"rgb(" + c.getStartColor().getRed() + ","
            + c.getStartColor().getGreen() + "," + c.getStartColor().getBlue()
            + ")\" to=\"rgb(" + c.getNewColor().getRed() + "," + c.getNewColor().getGreen()
            + "," + c.getNewColor().getBlue() + ")\" fill=\"freeze\" />"
            + "\n <animate attributeType=\"css\" begin=\"base.end\" dur=\"1ms\" " +
            "attributeName=\"fill\" "
            + "to=\"rgb(" + s.getColor().getRed() + ","
            + s.getColor().getGreen() + "," + s.getColor().getBlue()
            + ")\" fill=\"freeze\" />";
  }

  private String getSVGForMoveWithLooping(Move m, Shape s) {
    double startTime = ((double) m.getStartTime() / (double) this.tempo) * 1000;
    double duration = ((double) (m.getFinTime() - m.getStartTime()) / (double) this.tempo) * 1000;
    String xCoordName = "";
    String yCoordName = "";
    if (s.getType().equals("oval")) {
      xCoordName = "cx";
      yCoordName = "cy";
    }
    if (s.getType().equals("rectangle")) {
      xCoordName = "x";
      yCoordName = "y";
    }
    return "\n <animate attributeType=\"css\" begin=\"base.begin+" + startTime + "ms\" dur=\""
            + duration
            + "ms\" attributeName=" + "\"" + xCoordName + "\"" + " from=\"" + m.getStartLoc().getX()
            + "\" to=\"" + m.getDestination().getX() + "\" fill=\"freeze\" />"
            + "\n <animate attributeType=\"css\" begin=\"base.begin+" + startTime + "ms\" dur=\""
            + duration
            + "ms\" attributeName=\"" + yCoordName + "\" from=\"" + m.getStartLoc().getY()
            + "\" to=\"" + m.getDestination().getY() + "\" fill=\"freeze\" />"
            + "\n <animate attributeType=\"css\" begin=\"base.end\" dur=\"1ms\" attributeName=\""
            + xCoordName + "\" "
            + "to=\"" + s.getPoint().getX() + "\" fill=\"freeze\" />"
            + "\n <animate attributeType=\"css\" begin=\"base.end\" dur=\"1ms\" attributeName=\""
            + yCoordName + "\" "
            + "to=\"" + s.getPoint().getY() + "\" fill=\"freeze\" />";
  }

  private String getSVGForScaleWithLooping(Scale scale, Shape s) {
    double startTime = ((double) scale.getStartTime() / (double) this.tempo) * 1000;
    double duration = ((double) (scale.getFinTime() - scale.getStartTime()) / (double) this.tempo)
            * 1000;
    String xAttName = "";
    String yAttName = "";
    if (s.getType().equals("oval")) {
      xAttName = "rx";
      yAttName = "ry";
    }
    if (s.getType().equals("rectangle")) {
      xAttName = "width";
      yAttName = "height";
    }
    return "\n <animate attributeType=\"css\" begin=\"base.begin+" + startTime + "ms\" dur=\""
            + duration
            + "ms\" attributeName=\"" + xAttName + "\" from=\"" + scale.getStartXDimension()
            + "\" to=\"" + scale.getNewXDimension() + "\" fill=\"freeze\" />"
            + "\n <animate attributeType=\"css\" begin=\"base,begin+" + startTime + "ms\" dur=\""
            + duration
            + "ms\" attributeName=" + "\"" + yAttName + "\"" + " from=\""
            + scale.getStartYDimension() + "\" to=\"" + scale.getNewYDimension()
            + "\" fill=\"freeze\" />"
            + "\n <animate attributeType=\"css\" begin=\"base.end\" dur=\"1ms\" attributeName=\""
            + xAttName + "\" "
            + "to=\"" + s.getXAtt() + "\" fill=\"freeze\" />"
            + "\n <animate attributeType=\"css\" begin=\"base.end\" dur=\"1ms\" attributeName=\""
            + yAttName + "\" "
            + "to=\"" + s.getYAtt() + "\" fill=\"freeze\" />";
  }

  public void restart(AnimationOperations newModel) {
    // do nothing
  }

  @Override
  public void increaseTempo() {
    // do nothing
  }

  @Override
  public void decreaseTempo() {
    // do nothing
  }

  @Override
  public void setLooping(AnimationOperations newModel) {
    // do nothing
  }

  @Override
  public void createSVG(AnimationOperations newModel) {
    // do nothing
  }

  @Override
  public void addListener(KeyListener listener) {
    // Do nothing.  Added for customer purposes.
  }
}


