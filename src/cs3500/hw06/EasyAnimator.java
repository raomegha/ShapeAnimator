package cs3500.hw06;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import cs3500.animator.provider.view.ViewAnimationHybrid;
import cs3500.hw05.AnimationModel;
import cs3500.hw08.AnimationOperationsAdapter;
import cs3500.hw08.ProviderViewWrapper;

/**
 * The main class for the animation. Reads and parses the command arguments and then creates the
 * model view and controller.
 */
public final class EasyAnimator {

  /**
   * Main method for the animation.
   *
   * @param args - The arguments provided for the animation. Includes the output path, speed,
   *             animationFileName for input, and the viewType.
   */
  public static void main(String[] args) {

    String animationFileName = "";
    String viewType = "";
    String outputPath = "";
    int tempo = 1;
    OutputStream out = System.out;

    //parses the command arguments
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-if")) {
        animationFileName = args[i + 1];
      }

      if (args[i].equals("-iv")) {
        viewType = args[i + 1];
      }

      if (args[i].equals("-o")) {
        outputPath = args[i + 1];
      }

      if (args[i].equals("-speed")) {
        try {
          tempo = Integer.parseInt(args[i + 1]);
        } catch (NumberFormatException e) {
          Utilities.printErrorOnScreen("Invalid speed - NumberFormatException");
        }
      }
    }

    AnimationModel model = new AnimationModel();
    TweenAnimationModelBuilder modelBuilder = new TweenAnimationModelBuilder(model);
    AnimationFileReader fileReader = new AnimationFileReader();

    //tries to build the model
    try {
      model = fileReader.readFile(animationFileName, modelBuilder);
    } catch (FileNotFoundException | IllegalArgumentException e) {
      Utilities.printErrorOnScreen(e.getMessage());
    }

    if (!outputPath.equals("out") && !viewType.equals("visual")) {
      try {
        out = new FileOutputStream(new File(outputPath));
      } catch (FileNotFoundException e) {
        Utilities.printErrorOnScreen("Invalid output file name");
      }
    }


    if (viewType.equals("provider")) {
      AnimationOperationsAdapter adapter = new AnimationOperationsAdapter(model);
      Appendable ap = new StringBuilder();
      IView providerHybrid = new ProviderViewWrapper(new ViewAnimationHybrid(adapter, tempo,
              ap, null), adapter);
      cs3500.animator.provider.view.IView hybrid = (ProviderViewWrapper) providerHybrid;
      AnimationController controller = new AnimationController(model, providerHybrid, tempo);
      hybrid.addListener(controller);
    }
    else {
      FactoryView factoryView = new FactoryView(out, tempo);
      //tries to create the view and run the animation
      try {
        IView view = factoryView.create(viewType);
        AnimationController controller = new AnimationController(model, view, tempo);
      } catch (IllegalArgumentException e) {
        Utilities.printErrorOnScreen(e.getMessage());
      }
    }
  }
}
