package cs3500.hw06;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Class for methods that are used by more than one class.
 */
public final class Utilities {

  /**
   * Prints the error on the screen and exits.
   *
   * @param message - The message to be displayed.
   */
  public static void printErrorOnScreen(String message) {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JOptionPane.showMessageDialog(frame,
            message,
            "Invalid Argument Error",
            JOptionPane.ERROR_MESSAGE);
    System.exit(0);
  }
}
