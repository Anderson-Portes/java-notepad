import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoardHandler implements KeyListener {

  private GUI gui;

  public KeyBoardHandler(GUI gui) {
    this.gui = gui;
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_S) {
      this.gui.fileMenuImplementation.saveFile();
    }
    if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z) {
      this.gui.editMenuImplementation.editUndo();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }

}
