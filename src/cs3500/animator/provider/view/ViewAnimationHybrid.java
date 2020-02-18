package cs3500.animator.provider.view;

import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.ScrollPaneLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.Timer;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import cs3500.animator.provider.model.AnimationOperations;
import cs3500.animator.provider.model.Command;
import cs3500.animator.provider.model.Shape;
import cs3500.hw08.AnimationModel;

/**
 * The class for the interactive visual animation.
 * This class implements IView and extends JFrame.
 */
public class ViewAnimationHybrid extends JFrame implements IView {

  private AnimationOperations m;
  private AnimationOperations newModel;
  private int tempo;
  private Timer timer;
  protected Appendable ap;
  private HybridShapePanel hShapePanel;
  private boolean paused;
  private boolean loopingIsOn;
  private KeyListener keyListener;
  private HybridKeyPanel hKeyPanel;
  private ActionListener listener;
  private ArrayList<String> invisibleShapeNames;


  /**
   * Construct the hybrid view.
   * @param model the model for the animation
   * @param t the time for the animation
   * @param ap the appendable
   * @param keyListener the keylistener for the animation
   */
  public ViewAnimationHybrid(AnimationOperations model, int t,
                             Appendable ap, KeyListener keyListener) {
    super();
    this.m = model;
    this.tempo = t;
    this.paused = false;
    this.loopingIsOn = false;
    this.keyListener = keyListener;
    this.ap = ap;
    this.invisibleShapeNames = new ArrayList<>();

    this.setTitle("Animation");
    this.setSize(1500, 1500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    /**
     * Class to hold TimeListener, which gets the next t phase after every call from timer.
     */
    class TimeListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (m.getT() > m.getLastEndTimeOfShapes() && loopingIsOn) {
          restart(newModel);
        } else {
          m.nextPhase(tempo);
          hShapePanel.repaint();
          hKeyPanel.repaint();
        }
      }
    }

    listener = new TimeListener();
    timer = new Timer(1000 / tempo, listener);
  }

  @Override
  public void startView() {
    this.addKeyListener(keyListener);

    hShapePanel = new HybridShapePanel(m);
    hShapePanel.setPreferredSize(new Dimension(1500, 1500));
    hShapePanel.setLayout(new BoxLayout(hShapePanel, BoxLayout.PAGE_AXIS));

    JScrollPane shapeScroller = new JScrollPane(hShapePanel);
    shapeScroller.setPreferredSize(new Dimension(250, 80));
    shapeScroller.setLayout(new ScrollPaneLayout());
    this.add(shapeScroller);

    hKeyPanel = new HybridKeyPanel();
    hKeyPanel.setPreferredSize(new Dimension(1500, 400));
    hKeyPanel.setLayout(null);
    this.add(hKeyPanel, BorderLayout.SOUTH);

    JPanel checkBoxPanel = new JPanel();
    checkBoxPanel.setPreferredSize(new Dimension(300,1500));
    checkBoxPanel.setBorder(BorderFactory.createTitledBorder("Checkboxes"));
    checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.PAGE_AXIS));

    JCheckBox[] checkBoxes = new JCheckBox[m.getListOfShapes().size()];
    ArrayList<JCheckBox> group = new ArrayList<>();
    for (int i = 0; i < checkBoxes.length; i++) {
      final int X = i;
      checkBoxes[i] = new JCheckBox(m.getListOfShapes().get(i).getName());
      checkBoxes[i].setSelected(false);
      checkBoxes[i].addItemListener(e -> {
        if (checkBoxes[X].isSelected()) {
          this.invisibleShapeNames.add(checkBoxes[X].getText());
          hShapePanel.addInvisibleShapeName(checkBoxes[X].getText());
          this.requestFocusInWindow();
        } else {
          this.invisibleShapeNames.remove(checkBoxes[X].getText());
          hShapePanel.removeInvisibleShapeName(checkBoxes[X].getText());
          this.requestFocusInWindow();
        }
      });
      group.add(checkBoxes[i]);
      checkBoxPanel.add(checkBoxes[i]);
      checkBoxPanel.revalidate();
      checkBoxPanel.repaint();
    }

    JScrollPane listScroller = new JScrollPane(checkBoxPanel);
    listScroller.setPreferredSize(new Dimension(250, 80));
    listScroller.setLayout(new ScrollPaneLayout());
    this.add(listScroller, BorderLayout.EAST);
    checkBoxPanel.revalidate();
    checkBoxPanel.repaint();

    this.setPreferredSize(new Dimension(1500,1500));
    this.setVisible(true);

    this.requestFocusInWindow();
    timer.start();
  }

  @Override
  public void setPause() {
    if (paused) {
      timer.start();
    } else {
      timer.stop();
    }
    this.paused = !paused;
  }

  @Override
  public void restart(AnimationOperations newModel) {
    AnimationOperations nextNewModel = new AnimationModel();
    ArrayList<Shape> nextNewShapes = new ArrayList<>();
    nextNewShapes.addAll(newModel.getListOfShapes());
    ArrayList<Command> nextNewCommands = new ArrayList<>();
    nextNewCommands.addAll(newModel.getCommands());
    nextNewModel.startGame(nextNewShapes, nextNewCommands);
    this.m = nextNewModel;
    this.hShapePanel.setModel(nextNewModel);
    timer.start();
    this.paused = false;
  }

  @Override
  public void increaseTempo() {
    this.tempo = tempo + 1;
    timer.stop();
    timer = new Timer(1000 / tempo, listener);
    timer.restart();
  }

  @Override
  public void decreaseTempo() {
    if (this.tempo > 1) {
      this.tempo = tempo - 1;
      timer.stop();
      timer = new Timer(1000 / tempo, listener);
      timer.restart();
    }
  }

  @Override
  public void setLooping(AnimationOperations newModel) {
    this.loopingIsOn = !this.loopingIsOn;
    this.newModel = newModel;
  }

  @Override
  public void createSVG(AnimationOperations newModel) {
    timer.stop();
    JFrame createSVG = new JFrame("SVG Creator");

    JButton execute;
    execute = new JButton("Create File");
    execute.setBounds(100,100,140, 40);

    JLabel label = new JLabel();
    label.setText("Enter File Name:");
    label.setBounds(10, 10, 100, 100);

    JTextField textField = new JTextField();
    textField.setBounds(110, 50, 130, 30);

    createSVG.add(label);
    createSVG.add(textField);
    createSVG.add(execute);
    createSVG.setSize(500, 500);
    createSVG.setLayout(null);
    createSVG.setVisible(true);
    createSVG.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    execute.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent execute) {
        IView newSVG = new ViewAnimationSVG(newModel,tempo,ap, loopingIsOn,invisibleShapeNames);
        newSVG.startView();
        try (PrintWriter output = new PrintWriter(textField.getText())) {
          output.println(ap.toString());
        } catch (FileNotFoundException fe) {
          fe.printStackTrace();
        }
        timer.start();
      }
    });
  }

  @Override
  public void addListener(KeyListener listener) {
    // Do nothing.  Added for customer purposes.
  }
}