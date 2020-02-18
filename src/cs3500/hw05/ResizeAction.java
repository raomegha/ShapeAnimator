package cs3500.hw05;

import java.util.ArrayList;

import javafx.util.Pair;

/**
 * Action to resize a shape.
 */
public class ResizeAction extends Action {

  private ArrayList<Pair<String, Double>> oldParms;
  private ArrayList<Pair<String, Double>> newParms;

  /**
   * Constructs a ResizeAction.
   *
   * @param startTime - The start time of the animation
   * @param endTime   - The end time of the animation
   * @param oldParms  - The old parameters for the shape
   * @param newParms  - The new parameters for the shape
   */
  public ResizeAction(int startTime, int endTime,
                      ArrayList<Pair<String, Double>> oldParms,
                      ArrayList<Pair<String, Double>> newParms) {
    super(startTime, endTime);
    this.oldParms = oldParms;
    this.newParms = newParms;
  }

  @Override
  public void apply() {
    this.shape.resize(this.newParms);
  }

  @Override
  public String printAction() {
    String oldParms = "";
    String newParms = "";
    for (int i = 0; i < this.newParms.size(); i++) {
      oldParms = oldParms.concat(this.oldParms.get(i).getKey() + ": "
              + this.oldParms.get(i).getValue() + " ");
      newParms = newParms.concat(this.newParms.get(i).getKey() + ": "
              + this.newParms.get(i).getValue() + " ");
    }

    return "Shape " + this.shape.getName() + " scales from " + oldParms + "to " + newParms
            + "from t=" + this.startTime + " to t=" + this.endTime;
  }

  @Override
  public Action update(double time) {
    ArrayList<Pair<String, Double>> updated = new ArrayList<>();

    for (int i = 0; i < this.newParms.size(); i++) {
      double newVal = tweening(time, this.oldParms.get(i).getValue(),
              this.newParms.get(i).getValue());
      Pair<String, Double> updatedPair = new Pair(this.oldParms.get(i).getKey(), newVal);
      updated.add(updatedPair);
    }
    ResizeAction newResize = new ResizeAction(this.startTime, this.endTime, this.oldParms, updated);
    newResize.saveShape(this.shape);
    return newResize;
  }


  /**
   * Gets the old parameters.
   * @return Arrylists of parms
   */
  public ArrayList<Pair<String, Double>> getOldParms() {
    return (ArrayList<Pair<String, Double>>) this.oldParms.clone();
  }
  
  /**
   * Gets the new parameters.
   * @return ArrayList of parms
   */
  public ArrayList<Pair<String, Double>> getNewParms() {
    return (ArrayList<Pair<String, Double>>) this.newParms.clone();
  }
}
