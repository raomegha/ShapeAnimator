package cs3500.animator.provider.view;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import cs3500.animator.provider.model.AnimationOperations;
import cs3500.animator.provider.model.Shape;


public class HybridShapePanel extends JPanel {

  private AnimationOperations model;
  private ArrayList<String> invisibleShapeNames;

  /**
   * Constructs the panel that displays all the shapes for the Hybrid View.
   * Uses model to get all shapes, and compares with invisible shapes.
   * @param model model that shapes are taken from
   */
  public HybridShapePanel(AnimationOperations model) {
    super();
    this.model = model;
    invisibleShapeNames = new ArrayList<>();
    this.setBackground(Color.WHITE);
  }

  @Override
  protected void paintComponent(Graphics g) {
    ArrayList<Shape> activeShapes = model.getListOfActiveShapes();
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    for (Shape s : activeShapes) {
      if (!invisibleShapeNames.contains(s.getName())) {
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

  protected void setModel(AnimationOperations newModel) {
    this.model = newModel;
  }

  protected void addInvisibleShapeName(String s) {
    invisibleShapeNames.add(s);
  }

  protected void removeInvisibleShapeName(String s) {
    invisibleShapeNames.remove(s);
  }
}
