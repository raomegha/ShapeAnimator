package cs3500.hw06;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cs3500.hw05.Shape;

/**
 * View that draws and plays animation within a window.
 */
public class VisualView extends JFrame implements IView {
  protected ArrayList<Shape> shapes;
  protected JPanel shapePanel;

  /**
   * Constructs an instance of a VisualView.
   */
  public VisualView() {
    super();
    this.setTitle("Animator");
    this.setSize(1000, 1000);


    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.shapes = new ArrayList<>();
    this.shapePanel = new DrawShapes();

    this.shapePanel.setPreferredSize(new Dimension(1500, 1500));
    JScrollPane scroll = new JScrollPane(this.shapePanel);
    this.add(scroll);
  }

  @Override
  public void drawShapes(ArrayList<Shape> shapes) {
    this.shapes = shapes;
    this.refresh();
  }

  @Override
  public boolean isVisual() {
    return true;
  }

  @Override
  public void changeBackgroundColor(Color c) {
    this.shapePanel.setBackground(c);
  }

  /**
   * Draws a rectangle on the frame.
   *
   * @param s - The given shape to draw.
   * @param g - The given graphics
   */
  protected void drawRectangle(Shape s, Graphics g) {
    int xPos = getWidth() / 4 + s.getPosition().getKey().intValue();
    int yPos = getHeight() / 4 + s.getPosition().getValue().intValue();
    int width = s.getParameters().get(0).getValue().intValue();
    int height = s.getParameters().get(1).getValue().intValue();
    Color col = new Color((int) (s.getRed() * 255), (int) (s.getGreen() * 255),
            (int) (s.getBlue() * 255));
    g.setColor(col);
    g.fillRect(xPos, yPos, width, height);
  }

  /**
   * Draws an oval on the frame.
   *
   * @param s - The given shape to draw.
   * @param g - The given graphics
   */
  protected void drawOval(Shape s, Graphics g) {
    int x = getWidth() / 4 + s.getPosition().getKey().intValue();
    int y = getHeight() / 4 + s.getPosition().getValue().intValue();
    int major = s.getParameters().get(0).getValue().intValue();
    int minor = s.getParameters().get(1).getValue().intValue();
    Color col = new Color((int) (s.getRed() * 255), (int) (s.getGreen() * 255),
            (int) (s.getBlue() * 255));

    g.setColor(col);
    g.fillOval(x, y, major, minor);
  }


  /**
   * Represents a panel to draw the list of shapes.
   */
  class DrawShapes extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {

      super.paintComponent(g);
      for (Shape s : shapes) {
        switch (s.getShapeType()) {
          case RECTANGLE:
            drawRectangle(s, g);
            break;
          case OVAL:
            drawOval(s, g);
            break;
          default:
            break;
        }
      }
    }
  }

  /**
   * Repaints the view.
   */
  public void refresh() {
    this.repaint();
  }


}
