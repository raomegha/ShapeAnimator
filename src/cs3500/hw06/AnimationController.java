package cs3500.hw06;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cs3500.hw05.Action;
import cs3500.hw05.AnimationModel;
import cs3500.hw05.AnimationOperations;
import cs3500.hw05.Shape;
import cs3500.hw07.HybridView;
import cs3500.hw08.AnimationOperationsAdapter;
import cs3500.hw08.ProviderViewWrapper;
import javafx.scene.control.Slider;

/**
 * Represents the controller for the animation.
 */
public class AnimationController extends JApplet implements IController, ActionListener,
        KeyListener, ItemListener, ChangeListener {
  protected AnimationOperations model;
  protected IView view;

  protected Timer animateTimer;
  protected int time;
  protected int tempo;
  protected boolean isLooping;
  protected int speedFactor;

  /**
   * Constructs an AnimationController.
   *
   * @param model - The given model.
   * @param view  - The given view.
   * @param tempo - The given tempo to draw the shapes.
   * @throws IllegalArgumentException - if Model or view is null
   */
  public AnimationController(AnimationOperations model, IView view, int tempo)
          throws IllegalArgumentException {

    if (model == null) {
      throw new IllegalArgumentException("The model is null.");
    }

    if (view == null) {
      throw new IllegalArgumentException("The view is null");
    }

    this.model = model;
    this.view = view;
    this.time = -1;

    this.tempo = tempo;

    this.isLooping = true;

    this.speedFactor = 0;

    this.animateTimer = new Timer(1000 / tempo, e -> animate());

    if (!(this.view instanceof HybridView)) {

      if (!this.view.isVisual()) {
        this.animateTimer.setRepeats(false);
      } else {
        this.animateTimer.start();
      }
      //CHANGE - moved this.animate() to here because selecting shapes was not working
      // in hybrid view
      this.animate();
    } else {
      this.animate();
      HybridView hybridView = (HybridView) this.view;
      hybridView.setListeners(this, this, this, this);
      hybridView.addSlider(this.maxTime());
      ArrayList<Double> background = this.model.getBackground();
      hybridView.changeBackgroundColor(new Color((int) (background.get(0) * 255),
              (int) (background.get(1) * 255), (int) (background.get(2) * 255)));
    }

  }


  @Override
  public void animate() {
    this.time += 1;
    ArrayList<Action> allActions = this.model.getActions();
    ArrayList<Action> filteredActions = new ArrayList<>();
    ArrayList<Action> actionLeft = new ArrayList<>();

    this.filterActions(allActions, filteredActions, actionLeft);

    //restarts the animation if there are no shapes left.
    if (this.isDone()) {
      if (this.isLooping) {
        this.model.restart();
        this.time = 0;
      } else {
        this.animateTimer.stop();
      }
    }

    //Collections.sort(filteredActions);

    for (int i = 0; i < filteredActions.size(); i++) {
      Action a = filteredActions.get(i).update(this.time);
      this.model.animate(a);
    }

    ArrayList<Shape> shapes = this.model.getShapes();

    filterShapes(shapes);

    //CHANGE
    ArrayList<Shape> sortedShapes = sortShapesByLayer(shapes);

    this.view.drawShapes(sortedShapes);
  }

  /**
   * Filters the actions that apply at the current time.
   *
   * @param allActions      - represents all the actions.
   * @param filteredActions - represents the filtered actions.
   * @param actionLeft      - represents the actions left to animate.
   */
  private void filterActions(ArrayList<Action> allActions, ArrayList<Action> filteredActions,
                             ArrayList<Action> actionLeft) {
    for (int i = 0; i < allActions.size(); i++) {
      Action a = allActions.get(i);
      if (this.time >= a.getStartTime() && this.time <= a.getEndTime()) {
        filteredActions.add(a);
      }
      if (a.getEndTime() >= this.time) {
        actionLeft.add(a);
      }
    }
  }

  /**
   * Filters out the shapes that do not exist at the certain time.
   *
   * @param shapes - The list of shapes in the model
   */
  private void filterShapes(ArrayList<Shape> shapes) {
    if (this.time > 0) {
      for (int i = 0; i < shapes.size(); i++) {
        Shape s = shapes.get(i);

        if (this.time < s.getAppears() || this.time > s.getDisappears() || !s.getVisibility()) {
          shapes.remove(i);
          i--;
        }
      }
    }

  }

  /**
   * Sorts the shapes by the layer number.
   *
   * @param shapes - The list of shapes in the model
   */
  private ArrayList<Shape> sortShapesByLayer(ArrayList<Shape> shapes) {
    ArrayList<Shape> sortedShapes = new ArrayList<>();
    int layer = 0;
    while (shapes.size() != sortedShapes.size()) {
      for (int i = 0; i < shapes.size(); i++) {
        Shape s = shapes.get(i);
        if (s.getParameters().get(2).getValue() == layer) {
          sortedShapes.add(s);
        }
      }
      layer++;
    }

    return sortedShapes;
  }

  /**
   * Returns the maximum time of the animation.
   *
   * @return - Int of max time.
   */
  private int maxTime() {
    int t = 0;
    for (int i = 0; i < this.model.getShapes().size(); i++) {
      if (this.model.getShapes().get(i).getDisappears() > t) {
        t = this.model.getShapes().get(i).getDisappears();
      }
    }
    return t;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "restart":
        this.restart();
        break;
      case "start":
        this.startAnimation();
        break;
      case "pause":
        this.pause();
        break;
      case "resume":
        this.resume();
        break;
      case "increaseSpeed":
        this.changeSpeed(true);
        break;
      case "decreaseSpeed":
        this.changeSpeed(false);
        break;
      case "svg":
        JTextField textField = (JTextField) e.getSource();
        this.svgAnimate(textField.getText());
        HybridView hybridView = (HybridView) this.view;
        hybridView.revertFocus();
        break;
      case "changeBackgroundColor":
        if (!animateTimer.isRunning()) {
          Color newColor = JColorChooser.showDialog(null, "Choose a color", Color.white);
          ArrayList<Double> newBackground = new ArrayList<>(Arrays.asList(newColor.getRed() / 255.0,
                  newColor.getGreen() / 255.0, newColor.getBlue() / 255.0));
          this.model.updateBackground(newBackground);
          this.view.changeBackgroundColor(newColor);
        }
      default:
        break;
    }

  }

  @Override
  public void keyTyped(KeyEvent e) {
    //Our controller is not concerned with key typed.
  }

  @Override
  public void keyPressed(KeyEvent e) {
    boolean isHybrid = false;
    if (this.view instanceof ProviderViewWrapper) {
      isHybrid = true;
    }

    switch (e.getKeyCode()) {
      case KeyEvent.VK_RIGHT:
        if (isHybrid) {
          ProviderViewWrapper hybrid = (ProviderViewWrapper) this.view;
          hybrid.increaseTempo();
        } else {
          this.changeSpeed(true);
        }
        break;
      case KeyEvent.VK_LEFT:
        if (isHybrid) {
          ProviderViewWrapper hybrid = (ProviderViewWrapper) this.view;
          hybrid.decreaseTempo();
        } else {
          this.changeSpeed(false);
        }
        break;
      default:
        break;
    }

  }

  @Override
  public void keyReleased(KeyEvent e) {
    boolean isHybrid = false;

    if (this.view instanceof ProviderViewWrapper) {
      isHybrid = true;
    }

    switch (e.getKeyChar()) {
      //pause
      case 'p':
        if (isHybrid) {
          ProviderViewWrapper hybrid = (ProviderViewWrapper) this.view;
          hybrid.setPause();
        } else {
          this.pause();
        }
        break;
      //continue/resume
      case 'c':
        this.resume();
        break;
      //restart
      case 'r':
        if (isHybrid) {
          this.model.restart();
          ProviderViewWrapper hybrid = (ProviderViewWrapper) this.view;
          hybrid.restart(new AnimationOperationsAdapter(this.model));
        } else {
          this.restart();
        }
        break;
      //start or create svg
      case 's':
        if (isHybrid) {
          ProviderViewWrapper hybrid = (ProviderViewWrapper) this.view;
          hybrid.createSVG(new AnimationOperationsAdapter(this.model));
        } else {
          this.startAnimation();
        }
        break;
      case 'l':
        if (isHybrid) {
          ProviderViewWrapper hybrid = (ProviderViewWrapper) this.view;
          AnimationModel clone = new AnimationModel(this.model);
          clone.restart();
          hybrid.setLooping(new AnimationOperationsAdapter(clone));
        }
        break;
      default:
        break;
    }
  }


  /**
   * Method that will start the animation with user input.
   */
  private void startAnimation() {
    this.animateTimer.start();
  }


  /**
   * Method that will pause the animation with user input.
   */
  private void pause() {
    this.animateTimer.stop();
  }


  /**
   * Method that will resume the animation with user input.
   */
  private void resume() {
    if (this.time > 0) {
      this.animateTimer.start();
    }
  }

  /**
   * Method that will restart the animation with user input.
   */
  private void restart() {
    if (this.time > 0) {
      this.model.restart();
      this.time = 0;
      this.animateTimer.restart();
    }
  }

  /**
   * Method that will change the speed of the animation with user input.
   */
  private void changeSpeed(boolean isIncrease) {
    if (this.time > 0) {
      if (isIncrease) {
        this.speedFactor -= 10;
      } else {
        this.speedFactor += 10;
      }
      this.animateTimer.stop();
      int tickRate = Math.max(1, (1000 / this.tempo) + this.speedFactor);
      this.animateTimer = new Timer(tickRate, e -> animate());
      this.animateTimer.start();
    }
  }


  /**
   * Creates an svg file given the output path specified by the user.
   *
   * @param outputPath - The outputh path given by the user.
   */
  private void svgAnimate(String outputPath) {
    if (!outputPath.substring(outputPath.length() - 4).equals(".svg")) {
      Utilities.printErrorOnScreen("Output file name must be a.svg file");
    }
    try {
      OutputStream out = new FileOutputStream(new File(outputPath));
      ArrayList<Integer> background = new ArrayList<Integer>(Arrays.asList((int) (this.model.getBackground().get(0) * 255), (int) (this.model.getBackground().get(1) * 255), (int) (this.model.getBackground().get(2) * 255)));
      SvgView svg = new SvgView(out, new StringBuilder(), this.tempo, this.isLooping, background);
      svg.drawShapes(this.model.getShapes());
    } catch (FileNotFoundException er) {
      Utilities.printErrorOnScreen("Invalid output file name");
    }
  }

  /**
   * Checks if the animation is done.
   *
   * @return - Boolean.
   */
  private boolean isDone() {
    return this.time > this.maxTime();
  }

  @Override
  public void itemStateChanged(ItemEvent e) {
    if (e.getItem().toString().equals("Looping")) {
      if (e.getStateChange() == ItemEvent.SELECTED) {
        this.isLooping = true;
      } else {
        this.isLooping = false;
      }
    } else {
      this.model.changeShapeVisibility(e.getItem().toString());
    }
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    this.animateTimer.stop();
    JSlider slider = (JSlider) e.getSource();

    int difference = slider.getValue() - this.time;
    for (int i = 0; i < Math.abs(difference); i++) {
      if (difference < 0) {
        this.time -= 2;
      }
      this.animate();
    }
  }
}

