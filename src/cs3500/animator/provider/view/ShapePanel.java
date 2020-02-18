package cs3500.animator.provider.view;


import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import cs3500.animator.provider.model.AnimationOperations;
import cs3500.animator.provider.model.Shape;

/**
 * Creates a ShapePanel that holds the animation and scrollbar.  The
 * panel is overlapping the frame.
 */
public class ShapePanel extends JPanel {
  private AnimationOperations model;

  /**
   * Constructs a ShapePanel, initializes the background, starts the timer,
   * and updates the view after every action.
   * @param model model that is being updates and viewed
   * @param t speed of the clock
   */
  public ShapePanel(AnimationOperations model, int t) {
    super();
    this.model = model;
    int tempo = t;

    this.setBackground(Color.WHITE);

    /**
     * Class to hold TimeListener, which gets the next t phase after every call from timer.
     */
    class TimeListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
        model.nextPhase(tempo);
        repaint();
      }
    }

    ActionListener listener = new TimeListener();
    Timer timer = new Timer(1000 / tempo, listener);
    timer.start();
  }

  @Override
  protected void paintComponent(Graphics g) {
    ArrayList<Shape> activeShapes = model.getListOfActiveShapes();
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    for (Shape s : activeShapes) {
      switch (s.getType()) {
        case "rectangle":
          g2d.setColor(s.getColor());
          g2d.fillRect((int) s.getPoint().getX(), (int) s.getPoint().getY(),
              (int) s.getXAtt(), (int) s.getYAtt());
          break;
        case "oval":
          g2d.setColor(s.getColor());
          g2d.fillOval((int) s.getPoint().getX(), (int) s.getPoint().getY(),
              (int) s.getXAtt(), (int) s.getYAtt());
          break;
        default:
          throw new IllegalArgumentException("Invalid shape type to paint.");
      }
    }
  }
}
