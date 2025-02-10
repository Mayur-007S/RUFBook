package NotePad;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.awt.print.Printable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.undo.UndoManager;
import javax.swing.text.DefaultHighlighter;

public class SimpleNotepad extends JFrame {
    private JTextArea textArea;
    private String currentFilePath;
    private UndoManager undoManager;
    private String lastSearchTerm = "";

    public SimpleNotepad() {
        setTitle("RUFBook");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        Font f = new Font("Time New Roman", Font.PLAIN, 16);
        textArea = new JTextArea();
        textArea.setFont(f);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        undoManager = new UndoManager();
        textArea.getDocument().addUndoableEditListener(undoManager);

        createMenuBar();

        currentFilePath = null;
    }

    private void newWindow() {
        JFrame frame = new JFrame();
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        frame.setTitle("RUFBook");
        frame.setLocationRelativeTo(null);
        // setIconImage(new
        // ImageIcon("D:\\mayur111\\FullStackWebDevelopment\\NEW_JAVA\\Swing\\NotePad\\png\\logo-black.png").getImage());

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        currentFilePath = null;

    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        Font f2 = new Font("Time New Roman", Font.PLAIN, 12);
        JMenu fileMenu = new JMenu("File");
        fileMenu.setFont(f2);
        menuBar.setBackground(Color.white);

        JMenuItem newItem = new JMenuItem("New");
        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));

        JMenuItem newWindowItem = new JMenuItem("New Window");
        newWindowItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));

        JMenuItem openItem = new JMenuItem("Open...");
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));

        JMenuItem saveAsItem = new JMenuItem("Save As...");
        saveAsItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));

        JMenuItem PageSetupItem = new JMenuItem("Page Setup...");

        JMenuItem print = new JMenuItem("Print...");
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));

        JMenuItem exitItem = new JMenuItem("Exit");

        newItem.addActionListener(e -> newFile());
        newWindowItem.addActionListener(e -> newWindow());
        openItem.addActionListener(e -> openFile());
        saveItem.addActionListener(e -> saveFile());
        saveAsItem.addActionListener(e -> saveFile());
        exitItem.addActionListener(e -> System.exit(0));
        PageSetupItem.addActionListener(e -> showPageSetupDialog(this));
        print.addActionListener(e -> printComponent(textArea));

        fileMenu.add(newItem);
        fileMenu.add(newWindowItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.addSeparator();
        fileMenu.add(PageSetupItem);
        fileMenu.add(print);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        JMenu EditMenu = new JMenu("Edit");
        EditMenu.setFont(f2);

        JMenuItem Undo = new JMenuItem("Undo");
        Undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));

        JMenuItem Cut = new JMenuItem("Cut");
        Cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));

        JMenuItem Copy = new JMenuItem("Copy");
        Copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));

        Copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.copy();
            }
        });
        Cut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.copy();
                textArea.setText("");
            }
        });
        JMenuItem Paste = new JMenuItem("Paste");
        Paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));

        Paste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.paste();
            }
        });

        JMenuItem Delete = new JMenuItem("Delete");
        // Delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));

        Delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int start = textArea.getSelectionStart();
                int End = textArea.getSelectionEnd();
                if (start != End) {
                    textArea.replaceRange("", start, End);
                }
            }
        });
        JMenuItem find = new JMenuItem("Find...");
        find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));

        JMenuItem Findnext = new JMenuItem("Find Next");
        Findnext.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));

        JMenuItem FindPrevious = new JMenuItem("Find Previous");
        FindPrevious.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, InputEvent.SHIFT_DOWN_MASK));

        JMenuItem Replace = new JMenuItem("Replace...");
        Replace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK));

        JMenuItem Goto = new JMenuItem("Go To...");
        Goto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK));

        JMenuItem SelectAll = new JMenuItem("Select All");
        SelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));

        JMenuItem Timedate = new JMenuItem("Time/Date");
        Timedate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));

        EditMenu.add(Undo);
        EditMenu.addSeparator();
        EditMenu.add(Cut);
        EditMenu.add(Copy);
        EditMenu.add(Paste);
        EditMenu.add(Delete);
        EditMenu.addSeparator();
        EditMenu.add(find);
        EditMenu.add(Findnext);
        EditMenu.add(FindPrevious);
        EditMenu.add(Replace);
        EditMenu.add(Goto);
        EditMenu.addSeparator();
        EditMenu.add(SelectAll);
        EditMenu.add(Timedate);
        menuBar.add(EditMenu);

        Undo.addActionListener(e -> {
            if (undoManager.canUndo()) {
                undoManager.undo();
            }
        });

        find.addActionListener(e -> showFindDialog());

        JMenu Format = new JMenu("Format");
        Format.setFont(f2);
        Checkbox ck = new Checkbox("Word Wrap");
        JMenuItem font = new JMenuItem("Font...");
        Format.add(ck);
        Format.add(font);
        menuBar.add(Format);

        JMenu View = new JMenu("View");
        View.setFont(f2);
        JMenuItem zoom = new JMenuItem("Zoom");
        Checkbox ck2 = new Checkbox("Status Bar");
        View.add(zoom);
        View.add(ck2);
        menuBar.add(View);

        JMenu help = new JMenu("Help");
        help.setFont(f2);
        JMenuItem viewhelp = new JMenuItem("View Help");
        JMenuItem sendfeed = new JMenuItem("Send Feedback");
        JMenuItem about = new JMenuItem("About RUFBook");
        help.add(viewhelp);
        help.add(sendfeed);
        help.addSeparator();
        help.add(about);
        menuBar.add(help);

        setJMenuBar(menuBar);

    }

    private void newFile() {
        textArea.setText("");
        currentFilePath = null;
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            currentFilePath = file.getAbsolutePath();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                textArea.setText("");
                String line;
                while ((line = reader.readLine()) != null) {
                    textArea.append(line + "\n");
                }

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error opening file: " + e.getMessage());
            }
        }
    }

    private void saveFile() {
        if (currentFilePath == null) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
            int returnValue = fileChooser.showOpenDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                currentFilePath = file.getAbsolutePath();
            } else {
                return;
            }
        }

        try (BufferedWriter write = new BufferedWriter(new FileWriter(currentFilePath))) {
            write.write(textArea.getText());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage());
        }
    }

    public static void showPageSetupDialog(JFrame parent) {
        PrinterJob printerjob = PrinterJob.getPrinterJob();
        PageFormat pageFormat = printerjob.defaultPage();

        PageFormat newPageFormat = printerjob.pageDialog(pageFormat);
    }

    public static void printComponent(JComponent component) {
        PrinterJob printJob2 = PrinterJob.getPrinterJob();
        Printable printable = (graphics, pageFormat, pageIndex) -> {
            if (pageIndex > 0) {
                return Printable.NO_SUCH_PAGE;
            }
            Graphics2D g2d = (Graphics2D) graphics;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            component.printAll(g2d);
            return Printable.PAGE_EXISTS;
        };

        printJob2.setPrintable(printable);

        if (printJob2.printDialog()) {
            try {
                printJob2.print();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(component, "Printing failed: " + e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showFindDialog() {
        String searchTerm = JOptionPane.showInputDialog(this, "Find:", lastSearchTerm);
        if (searchTerm != null && !searchTerm.isEmpty()) {
            lastSearchTerm = searchTerm;
            highlightText(searchTerm);
        }
    }

    private void highlightText(String searchTerm) {
        String text = textArea.getText();
        int index = text.indexOf(searchTerm);
        textArea.getHighlighter().removeAllHighlights();

        while (index >= 0) {
            try {
                textArea.getHighlighter().addHighlight(index, index + searchTerm.length(),
                        new DefaultHighlighter.DefaultHighlightPainter(Color.yellow));
                index = text.indexOf(searchTerm, index + searchTerm.length());
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimpleNotepad notepad = new SimpleNotepad();
            notepad.setVisible(true);
        });
    }
}
