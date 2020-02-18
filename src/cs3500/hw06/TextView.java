package cs3500.hw06;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import cs3500.hw05.Action;
import cs3500.hw05.Shape;

/**
 * Textual representation of an animation.
 */
public class TextView implements IView {
  protected OutputStream out;
  protected Appendable app;
  protected int tempo;

  /**
   * Constructs an instance of a Textview.
   *
   * @param out   - The output stream
   * @param app   - The given appendable
   * @param tempo - The tempo of the animation
   * @throws IllegalArgumentException if out and app are null
   */
  public TextView(OutputStream out, Appendable app, int tempo) throws IllegalArgumentException {
    if (out == null) {
      throw new IllegalArgumentException("output stream cannot be null");
    }

    if (app == null) {
      throw new IllegalArgumentException("Appendable cannot be null");
    }

    this.out = out;
    this.app = app;
    this.tempo = tempo;
  }

  /**
   * Method that prints the shape's text in the outstream.
   *
   * @param s - represents the shape
   * @return String - represents the output text
   */
  private String shapeText(Shape s) {
    double appears = s.getAppears();
    double disappears = s.getDisappears();
    String input = s.printShape();

    return this.formatText(input, appears, disappears).concat("\n");
  }

  /**
   * Method that prints the shape's action in the outstream.
   *
   * @param a - represents the action
   * @return String - represents the output text
   */
  private String actionText(Action a) {
    String input = a.printAction();
    double appears = a.getStartTime();
    double disappears = a.getEndTime();

    return this.formatText(input, appears, disappears);
  }

  /**
   * Method that outputs the text of the shapes and their actions.
   *
   * @param shapes - An arraylist of shapes
   */
  @Override
  public void drawShapes(ArrayList<Shape> shapes) {
    ArrayList<Action> filterActions = new ArrayList<>();
    String startText = "Shapes: \n";
    this.append(startText);

    //adding actions to filterActions
    for (int i = 0; i < shapes.size(); i++) {
      Shape s = shapes.get(i);
      for (int j = 0; j < s.getActions().size(); j++) {
        filterActions.add(s.getActions().get(j));
      }
    }

    //sorting the filterActions by start time
    Collections.sort(filterActions);

    //print all the shapes info
    for (int i = 0; i < shapes.size(); i++) {
      Shape s = shapes.get(i);
      append(shapeText((s)));
    }

    //print all the actions info
    for (int j = 0; j < filterActions.size(); j++) {
      this.append(actionText(filterActions.get(j)));
    }

    try {
      this.out.write(this.app.toString().getBytes());
    } catch (IOException e) {
      Utilities.printErrorOnScreen(e.getMessage());
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
   * Formats the given text and converts ticks to seconds.
   *
   * @param input      - The given text to format
   * @param appears    - The time at which the object appears.
   * @param disappears - the time at which the object dissapears.
   * @return - the formatted text
   */
  private String formatText(String input, double appears, double disappears) {
    int counter = 1;

    String text = "";
    Scanner scanner = new Scanner(input);

    while (scanner.hasNextLine()) {
      String curLine = scanner.nextLine();
      String[] words = curLine.split(" ");
      //checks where to replace the t= with the proper seconds
      for (int i = 0; i < words.length; i++) {
        if (words[i].length() > 2 && words[i].substring(0, 2).equals("t=")) {
          switch (counter) {
            //for the first t=
            case 1:
              text = text.concat(String.format("t=%.2fs ", appears / this.tempo));
              counter++;
              break;
            //for the second t=
            case 2:
              text = text.concat(String.format("t=%.2fs", disappears / this.tempo));
              counter++;
              break;
            default:
              break;
          }
        } else {
          text = text.concat(words[i] + " ");
        }
      }
      text = text.concat("\n");
    }
    return text;
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
        Utilities.printErrorOnScreen(e.getMessage());
      }
    }
  }
}