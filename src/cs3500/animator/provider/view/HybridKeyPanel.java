package cs3500.animator.provider.view;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

public class HybridKeyPanel extends JPanel {

  public HybridKeyPanel() {
    this.setBackground(Color.LIGHT_GRAY);
    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.BLACK);
    g2d.setFont(new Font("TimesRoman", Font.BOLD, 35));
    g2d.drawString("DIRECTIONS",620,50);
    g2d.setFont(new Font("TimesRoman", Font.PLAIN, 25));
    g2d.drawString("PRESS 'P' TO PAUSE/RESUME ANIMATION", 475, 100);
    g2d.drawString("PRESS 'R' TO RESTART ANIMATION", 475, 150);
    g2d.drawString("PRESS 'S' TO CREATE SVG FILE", 475, 200);
    g2d.drawString("PRESS 'L' TO ENABLE/DISABLE ANIMATION LOOPING", 475, 250);
    g2d.drawString("PRESS 'RIGHT ARROW' TO INCREASE ANIMATION SPEED", 475, 300);
    g2d.drawString("PRESS 'LEFT ARROW' TO DECREASE ANIMATION SPEED", 475, 350);
  }
}
