import java.awt.Font;

public class FormatMenuImplementation {

  private GUI gui;
  String font = "JetBrains Mono";
  int size = 12;

  public FormatMenuImplementation(GUI gui) {
    this.gui = gui;
  }

  public void formatWordWrap() {
    this.gui.wordWrap = !this.gui.wordWrap;
    this.gui.textArea.setLineWrap(this.gui.wordWrap);
    this.gui.textArea.setWrapStyleWord(this.gui.wordWrap);
    this.gui.wordWrapSubMenu.setText("Word Wrap: " + (this.gui.wordWrap ? "On" : "Off"));
  }

  public void formatFontSize(String size) {
    this.size = Integer.parseInt(size);
    this.setFont();
  }

  public void formatFontFamily(String font) {
    this.font = font;
    this.setFont();
  }

  public void setFont() {
    Font font = new Font(this.font, Font.PLAIN, this.size);
    this.gui.textArea.setFont(font);
  }
}
