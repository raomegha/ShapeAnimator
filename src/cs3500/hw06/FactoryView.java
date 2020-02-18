package cs3500.hw06;

import java.io.OutputStream;

import cs3500.hw07.HybridView;


/**
 * Creates an instance of a view depending on the given type of view requested.
 */
public class FactoryView {
  protected OutputStream out;
  protected int tempo;

  /**
   * Creates a FactoryView.
   *
   * @param out   - The output path
   * @param tempo - The given tempo
   * @throws IllegalArgumentException - when the output stream is null
   */
  public FactoryView(OutputStream out, int tempo)
          throws IllegalArgumentException {
    if (out == null) {
      throw new IllegalArgumentException("Output stream cannot be null");
    }

    this.out = out;
    this.tempo = tempo;
  }

  /**
   * Creates an instance of a view based on the given view type string.
   *
   * @param viewType - The type of view.
   * @return - An instance of an Iview.
   * @throws IllegalArgumentException - when the type of view doesn't exist
   */
  public IView create(String viewType) throws IllegalArgumentException {
    switch (viewType) {
      case "visual":
        VisualView v = new VisualView();
        v.setVisible(true);
        return v;
      case "text":
        return new TextView(this.out, new StringBuilder(), this.tempo);
      case "svg":
        return new SvgView(this.out, new StringBuilder(), this.tempo, true);
      case "interactive":
        return new HybridView();
      default:
        throw new IllegalArgumentException("Type of View doesn't exist");
    }
  }
}
