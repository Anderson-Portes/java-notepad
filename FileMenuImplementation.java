import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class FileMenuImplementation {
  private GUI gui;
  private String filePath;
  private String fileName;

  public FileMenuImplementation(GUI gui) {
    this.gui = gui;
  }

  public void newFile() {
    this.gui.textArea.setText("");
    this.gui.window.setTitle("New");
    this.fileName = this.filePath = null;
  }

  public void openFile() {
    FileDialog fileDialog = new FileDialog(gui.window, "Open", FileDialog.LOAD);
    fileDialog.setVisible(true);
    this.fileName = fileDialog.getFile();
    if (this.fileName.isEmpty()) {
      return;
    }
    this.filePath = fileDialog.getDirectory() + this.fileName;
    this.gui.window.setTitle(this.fileName);
    try {
      FileReader fileReader = new FileReader(this.filePath);
      BufferedReader reader = new BufferedReader(fileReader);
      this.gui.textArea.setText("");
      String line = null;
      while ((line = reader.readLine()) != null) {
        this.gui.textArea.append(line + "\n");
      }
      reader.close();
    } catch (Exception e) {
      System.out.println("An error occurred while reading the file: " + e.getMessage());
    }
  }

  public void saveFile() {
    if (this.fileName == null) {
      this.saveAsFile();
      return;
    }
    try {
      FileWriter fileWriter = new FileWriter(this.filePath);
      fileWriter.write(this.gui.textArea.getText());
      fileWriter.close();
    } catch (Exception e) {
      System.out.println("An error occurred while saving the file: " + e.getMessage());
    }
  }

  public void saveAsFile() {
    FileDialog filedDialog = new FileDialog(this.gui.window, "Save", FileDialog.SAVE);
    filedDialog.setVisible(true);
    this.fileName = filedDialog.getFile();
    if (this.fileName.isEmpty()) {
      return;
    }
    this.filePath = filedDialog.getDirectory() + this.fileName;
    this.gui.window.setTitle(this.fileName);
    try {
      FileWriter fileWriter = new FileWriter(this.filePath);
      fileWriter.write(this.gui.textArea.getText());
      fileWriter.close();
    } catch (Exception e) {
      System.out.println("An error occurred while saving the file: " + e.getMessage());
    }
  }

  public void exitFile() {
    System.exit(0);
  }
}
