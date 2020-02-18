package cs3500.hw05;

import java.util.ArrayList;

/**
 * Action to change the color of a shape.
 */
public class ChangeColorAction extends Action {

  private double oldRed;
  private double oldGreen;
  private double oldBlue;

  private double newRed;
  private double newGreen;
  private double newBlue;

  /**
   * Constructs a change color action.
   *
   * @param startTime - the time when the animation move starts
   * @param endTime   - the time when the animation move ends
   * @param oldRed    - The old amount of red
   * @param oldGreen  - The old amount of green
   * @param oldBlue   - The old amount of blue
   * @param newRed    - The new amount of red
   * @param newGreen  - The new amount of green
   * @param newBlue   - The new amount of blue
   */
  public ChangeColorAction(int startTime, int endTime, double oldRed, double oldGreen,
                           double oldBlue,  double newRed, double newGreen, double newBlue) {
    super(startTime, endTime);
    this.oldRed = oldRed;
    this.oldGreen = oldGreen;
    this.oldBlue = oldBlue;
    this.newRed = newRed;
    this.newGreen = newGreen;
    this.newBlue = newBlue;
  }


  @Override
  public void apply() {
    this.shape.changeColor(this.newRed, this.newGreen, this.newBlue);
  }

  @Override
  public String printAction() {
    return "Shape " + this.shape.getName() + " changes color from (" + this.oldRed + ","
            + this.oldGreen + "," + this.oldBlue + ") " + "to (" + this.newRed + ","
            + this.newGreen + "," + this.newBlue + ") " + "from t=" + this.startTime + " to t="
            + this.endTime;
  }

  @Override
  public Action update(double time) {
    double red = this.tweening(time, this.oldRed, this.newRed);
    double green = this.tweening(time, this.oldGreen, this.newGreen);
    double blue = this.tweening(time, this.oldBlue, this.newBlue);

    ChangeColorAction newAction = new ChangeColorAction(this.startTime, this.endTime,
            this.oldRed, this.oldGreen, this.oldBlue, red, green, blue);
    newAction.saveShape(this.shape);
    return newAction;
  }

  /**
   * Getter for all the colors.
   * @return - An arraylist of colors values.
   */
  public ArrayList<Double> getColors() {
    ArrayList<Double> colors = new ArrayList<>();
    colors.add(this.oldRed);
    colors.add(this.oldGreen);
    colors.add(this.oldBlue);
    colors.add(this.newRed);
    colors.add(this.newGreen);
    colors.add(this.newBlue);

    return colors;
  }
}
