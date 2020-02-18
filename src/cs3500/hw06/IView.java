package cs3500.hw06;

import java.awt.*;
import java.util.ArrayList;

import cs3500.hw05.Shape;

/**
 * Represents the view for the animation.
 */
public interface IView {

  /**
   * Draws the given shapes.
   * @param shapes - An arraylist of shapes
   */
  void drawShapes(ArrayList<Shape> shapes);

  /**
   * Checks if the view repeatedly prints/draws shape and actions.
   * @return boolean representing if repeating or not
   */
  boolean isVisual();

  /**
   * Changes the background color of the animation to the given color.
   * @param c - The given color.
   */
  void changeBackgroundColor(Color c);

}
