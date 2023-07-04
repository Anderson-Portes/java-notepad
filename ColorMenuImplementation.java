import java.awt.Color;

public class ColorMenuImplementation {
  private GUI gui;

  public ColorMenuImplementation(GUI gui) {
    this.gui = gui;
  }

  private void setGuiColor(Color backgroundColor, Color fontColor) {
    this.gui.window.getContentPane().setBackground(backgroundColor);
    this.gui.textArea.setBackground(backgroundColor);
    this.gui.textArea.setForeground(fontColor);
  }

  public void setColor(String color) {
    switch (color) {
      case "White":
        this.setGuiColor(Color.white, Color.black);
        break;
      case "Black":
        this.setGuiColor(Color.BLACK, Color.WHITE);
        break;
      case "Blue":
        this.setGuiColor(Color.BLUE, Color.WHITE);
        break;
    }
  }
}
