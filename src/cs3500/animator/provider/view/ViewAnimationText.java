package cs3500.animator.provider.view;


import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

import cs3500.animator.provider.model.AnimationOperations;
import cs3500.animator.provider.model.Command;
import cs3500.animator.provider.model.Shape;

/**
 * Creates the text view.  This view either creates a new text file with description of the
 * animation or displays the description in the window.
 */
public class ViewAnimationText implements IView {

  private final int tempo;
  private String unit = "";
  private AnimationOperations model;
  private String out = "";
  protected Appendable ap;

  /**
   * Constructs a text view by initializing the model, tempo and output for the view.
   * @param model animation model
   * @param tempo tempo for the animation
   * @param ap output of the view
   */
  public ViewAnimationText(AnimationOperations model, int tempo, Appendable ap) {
    this.model = model;
    this.unit = "s";
    this.tempo = tempo;
    this.out = out;
    this.ap = ap;
  }

  @Override
  public void startView() {

    ArrayList<Shape> listShapes = new ArrayList<>();
    listShapes.addAll(model.getListOfShapes());
    ArrayList<Command> commands = new ArrayList<>();
    commands.addAll(model.getCommands());

    try {
      ap.append("Shapes:" + "\n");
    } catch (IOException e) {
      e.printStackTrace();
    }

    for (Shape shape : listShapes) {
      try {
        ap.append(shape.toString(tempo, unit));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    for (Command c : commands) {
      try {
        ap.append(c.toString(find(c.getName(), listShapes, commands), tempo, unit));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void setPause() {
    // do nothing
  }

  @Override
  public void restart(AnimationOperations model) {
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

  /**
   * Finds the shape with the given name.
   * @param name name of shape
   * @return returns shape with given name
   */
  private Shape find(String name, ArrayList<Shape> listShapes, ArrayList<Command> commands) {
    for (Shape shape : listShapes) {
      if (shape.getName().equals(name)) {
        return shape;
      }
    }
    throw  new IllegalArgumentException("Cannot find shape.");
  }
}
