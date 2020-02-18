package cs3500.hw07;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;

import javax.swing.event.ChangeListener;

import cs3500.hw06.IView;

/**
 * Interface for a view that requires human interaction.
 */
public interface InteractiveView extends IView {

  /**
   * Sets the keyboard and buttons of the view to the listeners passed in.
   *
   * @param clicks - Represents the mouse input
   * @param keys   - Represents the keyboard input
   * @param items - Represents the checkbox input
   * @param change - Represents the slider.
   */
  void setListeners(ActionListener clicks, KeyListener keys,
                    ItemListener items, ChangeListener change);

  /**
   * Changes the focus back to the view.
   */
  void revertFocus();

  //CHANGE

  /**
   * Adds the slider to the hybrid view.
   * @param t - The maximum value of the slider.
   */
  void addSlider(int t);
}
