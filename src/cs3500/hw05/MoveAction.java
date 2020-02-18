package cs3500.hw05;

import javafx.util.Pair;

/**
 * Action to move a shape.
 */
public class MoveAction extends Action {

  private Pair<Double,Double> startPos;
  private Pair<Double, Double> endPos;

  /**
   * Constructs a MoveAction.
   *
   * @param startTime - the start time of the action
   * @param endTime   - the end time of the action
   * @param startPos    - the origin of the action
   * @param endPos    - the destination of the action
   */
  public MoveAction(int startTime, int endTime, Pair<Double, Double> startPos,
                    Pair<Double, Double> endPos) {
    super(startTime, endTime);
    this.startPos = startPos;
    this.endPos = endPos;
  }

  @Override
  public void apply() {
    this.shape.move(endPos);
  }

  @Override
  public String printAction() {
    return "Shape " + this.shape.getName() + " moves from (" + this.startPos.getKey()
            + "," + this.startPos.getValue() + ") to (" + +this.endPos.getKey() + ","
            + this.endPos.getValue() + ") " + "from t=" + this.startTime + " to t=" + this.endTime;
  }

  @Override
  public Action update(double time) {
    double x = this.tweening(time, this.startPos.getKey(), this.endPos.getKey());
    double y = this.tweening(time, this.startPos.getValue(), this.endPos.getValue());
    Pair<Double, Double> newPosn = new Pair(x, y);

    MoveAction newMove = new MoveAction(this.startTime, this.endTime,
            this.shape.getPosition(), newPosn);
    newMove.saveShape(this.shape);
    return newMove;
  }

  /**
   * Getter for the start position.
   * @return - Start position
   */
  public Pair<Double,Double> getStartPos() {
    return this.startPos;
  }

  /**
   * Getter for the end position.
   * @return - End position
   */
  public Pair<Double,Double> getEndPos() {
    return this.endPos;
  }
}
