package cs3500.hw07;

import java.awt.Checkbox;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeListener;

import cs3500.hw05.Shape;
import cs3500.hw06.Utilities;
import cs3500.hw06.VisualView;

/**
 * View that combines the visual and svg view into one view. Also allows user interaction.
 */
public class HybridView implements InteractiveView {

  protected VisualView visual;
  protected JPanel checklistPanel;
  protected ArrayList<Checkbox> checkboxes;
  protected JPanel buttonsPanel;
  protected JButton restart;
  protected JButton start;
  protected JButton pause;
  protected JButton resume;
  protected JButton increaseSpeed;
  protected JButton decreaseSpeed;
  protected Checkbox looping;
  protected JPanel svgFilePanel;
  protected JTextField svgField;

  protected boolean check;

  //CHANGE - added button to change color of background
  protected JButton backgroundChange;
  protected JColorChooser colorChooser;

  //CHANGE
  protected JSlider slider;

  /**
   * Construct an instance of the hybrid view.
   **/
  public HybridView() {
    this.visual = new VisualView();

    this.restart = new JButton("Restart (r)");
    this.restart.setActionCommand("restart");

    this.start = new JButton("Start (s)");
    this.start.setActionCommand("start");

    this.pause = new JButton("Pause (p)");
    this.pause.setActionCommand("pause");

    this.resume = new JButton("Resume (c)");
    this.resume.setActionCommand("resume");

    this.increaseSpeed = new JButton("Increase Speed (=>)");
    this.increaseSpeed.setActionCommand("increaseSpeed");

    this.decreaseSpeed = new JButton("Decrease Speed (<=)");
    this.decreaseSpeed.setActionCommand("decreaseSpeed");

    this.backgroundChange = new JButton("Change Background Color");
    this.backgroundChange.setActionCommand("changeBackgroundColor");

    this.looping = new Checkbox("Looping");
    this.looping.setForeground(Color.white);
    this.looping.setState(true);

    this.buttonsPanel = new JPanel();
    this.buttonsPanel.setBackground(Color.darkGray);
    this.buttonsPanel.setLayout(new GridLayout(1, 8, 10, 10));

    this.buttonsPanel.add(this.start);
    this.buttonsPanel.add(this.restart);
    this.buttonsPanel.add(this.pause);
    this.buttonsPanel.add(this.resume);
    this.buttonsPanel.add(this.increaseSpeed);
    this.buttonsPanel.add(this.decreaseSpeed);
    this.buttonsPanel.add(this.backgroundChange);
    this.buttonsPanel.add(this.looping);

    this.visual.getContentPane().add(this.buttonsPanel, BorderLayout.PAGE_END);
    this.checkboxes = new ArrayList<>();

    this.svgFilePanel = new JPanel();
    this.svgField = new JTextField("Enter the output file name for svg and then press enter",
            30);
    this.svgField.setActionCommand("svg");

    this.svgFilePanel.add(this.svgField);

    this.slider  = new JSlider(JSlider.HORIZONTAL, 0, 1, 1);
    this.svgFilePanel.add(this.slider);

    this.visual.getContentPane().add(this.svgFilePanel, BorderLayout.PAGE_START);

    this.visual.setVisible(true);
    this.check = false;

    this.colorChooser = new JColorChooser(Color.WHITE);
  }

  @Override
  public void drawShapes(ArrayList<Shape> shapes) {
    //initializes the checkbox on the first tick of the timer
    if (!this.check) {
      this.checklistPanel = new JPanel();
      this.checklistPanel.setBackground(Color.darkGray);
      this.checklistPanel.setLayout(new BoxLayout(checklistPanel, BoxLayout.Y_AXIS));
      this.visual.getContentPane().add(this.checklistPanel, BorderLayout.EAST);

      this.checklistPanel.setPreferredSize(new Dimension(150, 25 * shapes.size()));
      JScrollPane scroll = new JScrollPane(this.checklistPanel);
      this.visual.add(scroll, BorderLayout.EAST);

      for (int i = 0; i < shapes.size(); i++) {
        Checkbox box = new Checkbox(shapes.get(i).getName());
        box.setForeground(Color.white);
        box.setState(true);
        box.setFocusable(false);
        this.checkboxes.add(box);
      }
      for (int i = 0; i < shapes.size(); i++) {
        this.checklistPanel.add(this.checkboxes.get(i));
      }

      this.check = true;
      this.visual.setVisible(true);
    } else {
      //disables the subset of shapes checkbox while animation is running
    }
    this.visual.drawShapes(shapes);
  }

  @Override
  public boolean isVisual() {
    return true;
  }

  @Override
  public void setListeners(ActionListener clicks, KeyListener keys, ItemListener items,
                           ChangeListener change) {
    this.visual.addKeyListener(keys);
    this.visual.setFocusable(true);
    this.visual.requestFocus();

    this.restart.addActionListener(clicks);
    this.restart.setFocusable(false);

    this.start.addActionListener(clicks);
    this.start.setFocusable(false);

    this.pause.addActionListener(clicks);
    this.pause.setFocusable(false);

    this.resume.addActionListener(clicks);
    this.resume.setFocusable(false);

    this.increaseSpeed.addActionListener(clicks);
    this.increaseSpeed.setFocusable(false);

    this.decreaseSpeed.addActionListener(clicks);
    this.decreaseSpeed.setFocusable(false);

    this.backgroundChange.addActionListener(clicks);
    this.backgroundChange.setFocusable(false);

    this.looping.addItemListener(items);
    this.looping.setFocusable(false);

    this.svgField.addActionListener(clicks);

    this.slider.addChangeListener(change);

    for (int i = 0; i < this.checkboxes.size(); i++) {
      this.checkboxes.get(i).addItemListener(items);
    }
  }

  @Override
  public void revertFocus() {
    this.visual.requestFocus();
  }

  @Override
  public void addSlider(int t) {
    this.slider.setMaximum(t);
  }

  @Override
  public void changeBackgroundColor(Color c) {
    if (c == null) {
      Utilities.printErrorOnScreen("Color cannot be null");
    }
    this.visual.changeBackgroundColor(c);
  }

}

