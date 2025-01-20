import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

public class LibraryManagementSystem extends JFrame {
    private ArrayList<Book> books;
    private HashMap<String, Book> bookMap;
    private JTable table;
    private JTextField titleField, authorField, isbnField, yearField, searchField;
    private JComboBox<String> genreCombo;
    private JCheckBox availableCheck;
    private DefaultTableModel tableModel;

    public LibraryManagementSystem() {
        books = new ArrayList<>();
        bookMap = new HashMap<>();
        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);

        // Tool bar
        JToolBar toolBar = new JToolBar();
        JButton addButton = new JButton("Add Book");
        JButton removeButton = new JButton("Remove Book");
        JButton searchButton = new JButton("Search Book");
        toolBar.add(addButton);
        toolBar.add(removeButton);
        toolBar.add(searchButton);
        add(toolBar, BorderLayout.NORTH);

        // Tabbed Pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Book Details Panel
        JPanel bookDetailsPanel = new JPanel(new GridLayout(7, 2));
        titleField = new JTextField();
        authorField = new JTextField();
        isbnField = new JTextField();
        yearField = new JTextField();
        genreCombo = new JComboBox<>(new String[]{"Fiction", "Non-fiction", "Science", "History", "Fantasy"});
        availableCheck = new JCheckBox("Available");

        // Customize Labels with Underline
        JLabel titleLabel = new JLabel("<html><u>Title:</u></html>");
        JLabel authorLabel = new JLabel("<html><u>Author:</u></html>");
        JLabel isbnLabel = new JLabel("<html><u>ISBN:</u></html>");
        JLabel yearLabel = new JLabel("<html><u>Publication Year:</u></html>");
        JLabel genreLabel = new JLabel("<html><u>Genre:</u></html>");
        JLabel availabilityLabel = new JLabel("<html><u>Availability:</u></html>");

        // Set font and colors
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        titleLabel.setFont(labelFont);
        authorLabel.setFont(labelFont);
        isbnLabel.setFont(labelFont);
        yearLabel.setFont(labelFont);
        genreLabel.setFont(labelFont);
        availabilityLabel.setFont(labelFont);

        bookDetailsPanel.add(titleLabel);
        bookDetailsPanel.add(titleField);
        bookDetailsPanel.add(authorLabel);
        bookDetailsPanel.add(authorField);
        bookDetailsPanel.add(isbnLabel);
        bookDetailsPanel.add(isbnField);
        bookDetailsPanel.add(yearLabel);
        bookDetailsPanel.add(yearField);
        bookDetailsPanel.add(genreLabel);
        bookDetailsPanel.add(genreCombo);
        bookDetailsPanel.add(availabilityLabel);
        bookDetailsPanel.add(availableCheck);

        JButton addBookButton = new JButton("Add Book");
        JButton updateBookButton = new JButton("Update Book");
        bookDetailsPanel.add(addBookButton);
        bookDetailsPanel.add(updateBookButton);

        tabbedPane.addTab("Book Details", bookDetailsPanel);

        // Book List Panel
        JPanel bookListPanel = new JPanel(new BorderLayout());
        String[] columns = {"Title", "Author", "ISBN", "Genre", "Available"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 12)); // Set table font
        JScrollPane scrollPane = new JScrollPane(table);
        bookListPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        JButton searchBtn = new JButton("Search");
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        bookListPanel.add(searchPanel, BorderLayout.NORTH);

        tabbedPane.addTab("Book List", bookListPanel);

        add(tabbedPane, BorderLayout.CENTER);

        // Set colors
        bookDetailsPanel.setBackground(Color.LIGHT_GRAY);
        bookListPanel.setBackground(Color.LIGHT_GRAY);
        toolBar.setBackground(Color.GRAY);
        menuBar.setBackground(Color.DARK_GRAY);

        // Footer label
        JLabel footerLabel = new JLabel("- Made by Shashank Singh (RA2311003010597)");
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        footerLabel.setForeground(Color.DARK_GRAY);
        footerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(footerLabel, BorderLayout.SOUTH);

        // Event handling
        addBookButton.addActionListener(e -> addBook());
        updateBookButton.addActionListener(e -> updateBook());
        searchBtn.addActionListener(e -> searchBook());
        removeButton.addActionListener(e -> removeBook());

        setVisible(true);
    }

    private void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String isbn = isbnField.getText();
        String year = yearField.getText();
        String genre = (String) genreCombo.getSelectedItem();
        boolean available = availableCheck.isSelected();

        if (title.isEmpty() || author.isEmpty() || isbn.isEmpty() || year.isEmpty()) {
            showErrorDialog("All fields must be filled out.");
            return;
        }

        Book book = new Book(title, author, isbn, year, genre, available);
        books.add(book);
        bookMap.put(isbn, book);
        tableModel.addRow(new Object[]{title, author, isbn, genre, available});
        clearFields();
    }

    private void updateBook() {
        String isbn = isbnField.getText();
        Book book = bookMap.get(isbn);
        if (book != null) {
            book.setTitle(titleField.getText());
            book.setAuthor(authorField.getText());
            book.setYear(yearField.getText());
            book.setGenre((String) genreCombo.getSelectedItem());
            book.setAvailable(availableCheck.isSelected());
            refreshTable();
            clearFields();
        } else {
            showErrorDialog("Book not found.");
        }
    }

    private void searchBook() {
        String search = searchField.getText();
        tableModel.setRowCount(0); // Clear the table
        for (Book book : books) {
            if (book.getTitle().contains(search) || book.getAuthor().contains(search)) {
                tableModel.addRow(new Object[]{book.getTitle(), book.getAuthor(), book.getIsbn(), book.getGenre(), book.isAvailable()});
            }
        }
    }

    private void removeBook() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String isbn = (String) table.getValueAt(row, 2);
            books.remove(bookMap.remove(isbn));
            tableModel.removeRow(row);
        } else {
            showErrorDialog("Please select a book to remove.");
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0); // Clear the table
        for (Book book : books) {
            tableModel.addRow(new Object[]{book.getTitle(), book.getAuthor(), book.getIsbn(), book.getGenre(), book.isAvailable()});
        }
    }

    private void clearFields() {
        titleField.setText("");
        authorField.setText("");
        isbnField.setText("");
        yearField.setText("");
        genreCombo.setSelectedIndex(0);
        availableCheck.setSelected(false);
    }

    private void showErrorDialog(String message) {
        JDialog dialog = new JDialog(this, "Error", true);
        dialog.setLayout(new FlowLayout());
        dialog.add(new JLabel(message));
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dialog.dispose());
        dialog.add(okButton);
        dialog.setSize(300, 150);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        new LibraryManagementSystem();
    }

    class Book {
        private String title, author, isbn, year, genre;
        private boolean available;

        public Book(String title, String author, String isbn, String year, String genre, boolean available) {
            this.title = title;
            this.author = author;
            this.isbn = isbn;
            this.year = year;
            this.genre = genre;
            this.available = available;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public String getIsbn() {
            return isbn;
        }

        public String getYear() {
            return year;
        }

        public String getGenre() {
            return genre;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }
    }
}
