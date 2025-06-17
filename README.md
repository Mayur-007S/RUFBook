# RUFBook

RUFBook is a simple notepad application implemented in Java Swing.  
It provides a lightweight alternative for creating, editing, and managing text files, similar to the classic Notepad. The application features a GUI with file, edit, format, and view functionalities, along with undo, find, replace, and print support.

---

## ✨ Main Symbol: `SimpleNotepad`

- **Class:** `SimpleNotepad extends JFrame`
    - The core of the application; manages the main window, menu bar, and text editing area.

### 📝 Key Methods and Features

- **Constructor: `SimpleNotepad()`**
    - Initializes the main window, text area, undo manager, and sets up the menu bar.

- **Menu Bar Creation: `createMenuBar()`**
    - Adds menus:
        - **File:** New, New Window, Open, Save, Save As, Page Setup, Print, Exit
        - **Edit:** Undo, Cut, Copy, Paste, Delete, Find, Find Next/Previous, Replace, Go To, Select All, Time/Date
        - **Format:** Word Wrap, Font
        - **View:** Zoom, Status Bar
        - **Help:** View Help, Send Feedback, About
    - Each menu item has accelerator keys and event handlers.

- **File Operations**
    - `newFile()`: Clears the text area for a new file.
    - `openFile()`: Opens and loads content from a text file.
    - `saveFile()`: Saves content to the current or a new text file.

- **Window Management**
    - `newWindow()`: Opens a new notepad window.

- **Find & Highlight**
    - `showFindDialog()`: Prompts for a search term and highlights all occurrences.
    - `highlightText(String searchTerm)`: Highlights searched text in yellow.

- **Print Support**
    - `showPageSetupDialog(JFrame parent)`: Opens the page setup dialog.
    - `printComponent(JComponent component)`: Prints the text area content.

- **Undo Functionality**
    - Integrated with `UndoManager` to support undo/redo actions.

- **Main Method**
    - `main(String[] args)`: Application entry point; launches the notepad window.

---

## 🗂️ File Structure

- `SimpleNotepad.java` – Main class with all logic and GUI code.

---

## 🚀 Getting Started

1. **Clone the repository:**
    ```sh
    git clone https://github.com/Mayur-007S/RUFBook.git
    cd RUFBook
    ```
2. **Compile the Java file:**
    ```sh
    javac SimpleNotepad.java
    ```
3. **Run the application:**
    ```sh
    java SimpleNotepad
    ```

---

## 🛠️ Features Overview

- Create, open, save, and print text files
- Cut, copy, paste, delete, undo
- Find, replace, and go to line
- Select all, insert time/date
- Format options (word wrap, font)
- View options (zoom, status bar)
- Help and feedback menu

---

## 📜 License

MIT License

---
