package cs3500.animator.provider.view;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.KeyListener;

import cs3500.animator.provider.model.AnimationOperations;

/**
 * Creates the visual view.  This view opens a separate window to display the view on
 * by creating a JFrame, and adding onto it a JPanel and JScrollPane.
 */
public class ViewAnimationVisual extends JFrame implements IView {

  private AnimationOperations model;
  private final int tempo;

  /**
   * Constructs the visual view by supering in the JFrame variables and setting the JFrame
   * attributes.
   * @param model model to build view fro,
   * @param tempo tempo speed for animation
   */
  public ViewAnimationVisual(AnimationOperations model, int tempo, Appendable ap) {
    super();
    this.model = model;
    this.tempo = tempo;

    this.setTitle("Animation");
    this.setSize(1500, 1500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  @Override
  public void startView() {
    ShapePanel shapePanel;
    JScrollPane scrollPane;
    shapePanel = new ShapePanel(model, tempo);
    shapePanel.setPreferredSize(new Dimension(1500, 1500));
    scrollPane = new JScrollPane(shapePanel);
    scrollPane.setPreferredSize(new Dimension(1500,1500));
    this.setPreferredSize(new Dimension(1500,1500));
    this.add(scrollPane, BorderLayout.CENTER);
    this.setVisible(true);
    this.repaint();
  }

  @Override
  public void setPause() {
    // do nothing
  }

  @Override
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