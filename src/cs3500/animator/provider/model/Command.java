package cs3500.animator.provider.model;


/**
 * Abstract class for commands holding general command info.
 */
public abstract class Command implements ICommand {

  protected String name;
  protected int startTime;
  protected int finTime;

  /**
   * Constructor for a command.  Takes in general command info.
   * @param name name of shape
   * @param startTime time command starts
   * @param finTime time command finishes
   */
  public Command(String name, int startTime, int finTime) {
    this.name = name;
    this.startTime = startTime;
    this.finTime = finTime;

    checkIfInvalid(name, startTime, finTime);
  }

  /**
   * Gets the name of shape associated with this command.
   * @return String of shape name
   */
  public String getName() {
    String copyOfName = this.name;
    return copyOfName;
  }

  /**
   * Returns a copy of the command's start time.
   * @return a copy of the command's start time.
   */
  public int getStartTime() {
    return this.startTime;
  }

  /**
   * Returns a copy of the command's end time.
   * @return a copy of the command's end time.
   */
  public int getFinTime() {
    return this.finTime;
  }

  /**
   * Check to see if the command is valid or not.
   * @param name the name of the shape the command is acting on
   * @param startTime start time for the command
   * @param finTime end time for the command
   */
  protected void checkIfInvalid(String name, int startTime, int finTime) {
    if (name == null) {
      throw new IllegalArgumentException("Shape name to be animated cannot be null.");
    } else if (startTime < 0 || finTime < 0) {
      throw new IllegalArgumentException("Start and finish time of animation cannot be null.");
    } else if (startTime > finTime) {
      throw new IllegalArgumentException(
              "Animation time must be greater than or equal to start time.");
    }
  }

  /**
   * Return a description for the command.
   * @param shape the shape the command is acting on
   * @param tempo the tempo for the animation
   * @param unit the unit of time for the animation
   * @return the description of the command
   */
  public String toString(Shape shape, int tempo, String unit) {
    String cmdString = "";
    cmdString = cmdString + "Shape " + name + this.getCmdInfo(shape)
            + "from t=" + (startTime / tempo) + unit + " to t=" + (finTime / tempo) + unit + "\n";

    return cmdString;
  }

  /**
   * Return more specefic information about the command.
   * @param shape the shape the command is acting on
   * @return more specefic information about the command
   */
  protected abstract String getCmdInfo(Shape shape);

  @Override
  public abstract boolean equals(Object o);

  @Override
  public abstract int hashCode();

  /**
   * Execute the given command.
   * @param s the shape the command is changing
   * @param t animation time
   */
  public abstract void execute(Shape s, int t);

  /**
   * Returns this commands type.
   * @return a String that represents this shape's type.
   */
  public abstract String getType();
}