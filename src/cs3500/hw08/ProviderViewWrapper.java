package cs3500.hw08;

import java.awt.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import cs3500.animator.provider.model.AnimationOperations;
import cs3500.animator.provider.view.IView;
import cs3500.animator.provider.view.ViewAnimationHybrid;
import cs3500.hw05.Shape;

/**
 * Wrapper class for provider views to be used by Controller.
 */
public class ProviderViewWrapper implements IView, cs3500.hw06.IView {
  protected IView wrapper;
  protected AnimationOperationsAdapter model;

  /**
   * Constructs an instance of a ProviderViewWrapper.
   *
   * @param wrapper - The given view to be used in the controller.
   * @param model   - The given model for the view.
   */
  public ProviderViewWrapper(IView wrapper, AnimationOperationsAdapter model) {
    this.wrapper = wrapper;
    this.model = model;
  }

  @Override
  public void startView() {
    this.wrapper.startView();
  }

  @Override
  public void setPause() {
    this.wrapper.setPause();
  }

  @Override
  public void restart(AnimationOperations model) {
    this.wrapper.restart(model);
  }

  @Override
  public void increaseTempo() {
    this.wrapper.increaseTempo();
  }

  @Override
  public void decreaseTempo() {
    this.wrapper.decreaseTempo();
  }

  @Override
  public void setLooping(AnimationOperations newModel) {
    this.wrapper.setLooping(newModel);
  }

  @Override
  public void createSVG(AnimationOperations newModel) {
    this.wrapper.createSVG(newModel);
  }

  @Override
  public void addListener(KeyListener listener) {
    this.wrapper.addListener(listener);
    if ((this.wrapper instanceof ViewAnimationHybrid)) {
      ViewAnimationHybrid hybrid = (ViewAnimationHybrid) this.wrapper;
      hybrid.addKeyListener(listener);
    }
  }

  @Override
  public void drawShapes(ArrayList<Shape> shapes) {
    this.wrapper.startView();

    if ((this.wrapper instanceof ViewAnimationHybrid)) {
      this.wrapper.setPause();
    }

  }

  @Override
  public boolean isVisual() {
    return false;
  }

  @Override
  public void changeBackgroundColor(Color c) {
    //does nothing for provider
  }
}
