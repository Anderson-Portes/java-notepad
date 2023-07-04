import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GUI implements ActionListener {

  public JFrame window;
  public JTextArea textArea;
  public boolean wordWrap = false;
  private JScrollPane scrollPane;
  public JMenuItem wordWrapSubMenu;
  private JMenuBar menuBar;
  private JMenu[] menus;
  public FileMenuImplementation fileMenuImplementation = new FileMenuImplementation(this);
  public FormatMenuImplementation formatMenuImplementation = new FormatMenuImplementation(this);
  public ColorMenuImplementation colorMenuImplementation = new ColorMenuImplementation(this);
  public EditMenuImplementation editMenuImplementation = new EditMenuImplementation(this);
  public UndoManager undoManager = new UndoManager();
  private KeyBoardHandler keyBoardHandler = new KeyBoardHandler(this);

  public static void main(String[] args) {
    new GUI();
  }

  public GUI() {
    this.setWindow();
    this.setTextArea();
    this.setMenuBar();
    this.window.setVisible(true);
  }

  private void setWindow() {
    this.window = new JFrame("Notepad");
    this.window.setSize(800, 600);
    this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private void setTextArea() {
    this.textArea = new JTextArea();
    this.textArea.getDocument().addUndoableEditListener(this.getUndonableEditListener());
    this.textArea.addKeyListener(this.keyBoardHandler);
    this.scrollPane = new JScrollPane(this.textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.scrollPane.setBorder(BorderFactory.createEmptyBorder());
    this.window.add(this.scrollPane);
    this.formatMenuImplementation.setFont();
    this.textArea.setTabSize(2);
  }

  public UndoableEditListener getUndonableEditListener() {
    return new UndoableEditListener() {
      @Override
      public void undoableEditHappened(UndoableEditEvent e) {
        undoManager.addEdit(e.getEdit());
      }
    };
  }

  private void setMenuBar() {
    this.menuBar = new JMenuBar();
    this.setMenus();
    window.setJMenuBar(this.menuBar);
  }

  private void setMenus() {
    this.menus = new JMenu[4];
    String[] menuItems = { "File", "Edit", "Format", "Color" };
    for (int i = 0; i < menuItems.length; i++) {
      this.menus[i] = new JMenu(menuItems[i]);
      this.menuBar.add(this.menus[i]);
    }
    this.setSubMenus();
  }

  private void setSubMenus() {
    this.setFileMenu();
    this.setFormatMenu();
    this.setColorMenu();
    this.setEditMenu();
  }

  private void setEditMenu() {
    String items[] = { "Undo", "Redo" };
    for (String action : items) {
      JMenuItem item = new JMenuItem(action);
      item.addActionListener(this);
      item.setActionCommand("Edit" + action);
      this.menus[1].add(item);
    }
  }

  private void setColorMenu() {
    String[] items = { "White", "Black", "Blue" };
    for (String color : items) {
      JMenuItem item = new JMenuItem(color);
      item.addActionListener(this);
      item.setActionCommand("ColorSet" + color);
      this.menus[3].add(item);
    }
  }

  private void setFileMenu() {
    String[] menuItems = { "New", "Open", "Save", "Save As", "Exit" };
    for (String action : menuItems) {
      JMenuItem item = new JMenuItem(action);
      item.addActionListener(this);
      item.setActionCommand("File" + action);
      this.menus[0].add(item);
    }
  }

  private JMenu getFontSubMenu() {
    JMenu fontMenu = new JMenu("Font");
    String[] menuItems = { "Arial", "Comic Sans MS", "Times New Roman", "Consolas", "JetBrains Mono" };
    for (int i = 0; i < menuItems.length; i++) {
      JMenuItem item = new JMenuItem(menuItems[i]);
      item.addActionListener(this);
      item.setActionCommand("FormatFontFamily" + menuItems[i]);
      fontMenu.add(item);
    }
    return fontMenu;
  }

  private JMenu getFontSizeSubMenu() {
    JMenu fontSizeMenu = new JMenu("Font Size");
    for (int i = 8; i <= 32; i += 4) {
      String fontSize = String.valueOf(i);
      JMenuItem item = new JMenuItem(fontSize);
      item.addActionListener(this);
      item.setActionCommand("FormatFontSize" + fontSize);
      fontSizeMenu.add(item);
    }
    return fontSizeMenu;
  }

  private JMenuItem getWordWrapSubMenu() {
    this.wordWrapSubMenu = new JMenuItem("Word Wrap: Off");
    this.wordWrapSubMenu.addActionListener(this);
    this.wordWrapSubMenu.setActionCommand("FormatWordWrap");
    return this.wordWrapSubMenu;
  }

  private void setFormatMenu() {
    this.menus[2].add(this.getWordWrapSubMenu());
    this.menus[2].add(this.getFontSubMenu());
    this.menus[2].add(this.getFontSizeSubMenu());
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();
    String fontSizePattern = "FormatFontSize";
    if (command.startsWith(fontSizePattern)) {
      int index = command.indexOf(fontSizePattern);
      this.formatMenuImplementation.formatFontSize(command.substring(index + fontSizePattern.length()));
      return;
    }
    String fontFamilyPattern = "FormatFontFamily";
    if (command.startsWith(fontFamilyPattern)) {
      int index = command.indexOf(fontFamilyPattern);
      this.formatMenuImplementation.formatFontFamily(command.substring(index + fontFamilyPattern.length()));
      return;
    }
    String colorSetPattern = "ColorSet";
    if (command.startsWith(colorSetPattern)) {
      int index = command.indexOf(colorSetPattern);
      this.colorMenuImplementation.setColor(command.substring(index + colorSetPattern.length()));
      return;
    }
    switch (command) {
      case "FileNew":
        this.fileMenuImplementation.newFile();
        break;
      case "FileOpen":
        this.fileMenuImplementation.openFile();
        break;
      case "FileSave As":
        this.fileMenuImplementation.saveAsFile();
        break;
      case "FileSave":
        this.fileMenuImplementation.saveFile();
        break;
      case "FileExit":
        this.fileMenuImplementation.exitFile();
        break;
      case "FormatWordWrap":
        this.formatMenuImplementation.formatWordWrap();
        break;
      case "EditUndo":
        this.editMenuImplementation.editUndo();
        break;
      case "EditRedo":
        this.editMenuImplementation.editRedo();
        break;
    }
  }
}