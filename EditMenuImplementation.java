class EditMenuImplementation {
  private GUI gui;

  public EditMenuImplementation(GUI gui) {
    this.gui = gui;
  }

  public void editUndo() {
    this.gui.undoManager.undo();
  }

  public void editRedo() {
    this.gui.undoManager.redo();
  }
}