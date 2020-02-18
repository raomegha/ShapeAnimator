package cs3500.animator.provider.view;

import java.awt.event.KeyListener;

import cs3500.animator.provider.model.AnimationOperations;

/**
 * Represents the view for the animation. Implemented by three different classes: svg, text, and
 * visual view classes. Depending on the class and the desired output the view can either start
 * an animation, create a file or print a String.
 */
public interface IView {

  /**
   * Enacts the view. Either starts the animation, creates a file, or prints a string depending on
   * the class and the desired output for the view.
   */
  void startView();

  /**
   * Set the pause field for the view.
   */
  void setPause();

  /**
   * Restart the animation.
   * @param model the new model to start the animation with
   */
  void restart(AnimationOperations model);

  /**
   * Increase the tempo for the animation.
   */
  void increaseTempo();

  /**
   * Decrease the tempo for the animation.
   */
  void decreaseTempo();

  /**
   * Set the looping field for the view.
   * @param newModel the animation to use when restarting the view
   */
  void setLooping(AnimationOperations newModel);

  /**
   * Create the svg for the current animation state.
   * @param newModel model to use for the svg file
   */
  void createSVG(AnimationOperations newModel);

  /**
   * Adds keyListener to this.  Customer requested
   * @param listener - given listener.
   */
  void addListener(KeyListener listener);
}
