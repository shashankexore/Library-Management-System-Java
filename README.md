# Library Management System

This is a simple **Library Management System** built using Java and Swing. The application allows users to add, update, search, and remove books in a library database, with a user-friendly graphical interface.

## Features

- **Add Books**: Input book details such as title, author, ISBN, publication year, genre, and availability.
- **Update Books**: Modify details of an existing book using its ISBN.
- **Search Books**: Search for books by title or author.
- **Remove Books**: Delete a book from the library.
- **Tabbed Interface**: Separate panels for book details and book list.
- **Toolbar**: Quick access to actions like adding, removing, and searching for books.
- **Book List**: Display all books in a table with columns for title, author, ISBN, genre, and availability.
- **Error Handling**: Dialog boxes for errors like missing fields or invalid operations.
- **Footer**: Displays the creator's name and registration number.

## Tech Stack

- **Programming Language**: Java
- **GUI Framework**: Swing
- **Data Structures**: ArrayList and HashMap

## How to Run

1. Ensure you have Java installed on your system.
2. Compile the program using the following command:

   ```bash
   javac LibraryManagementSystem.java
   ```

3. Run the program:

   ```bash
   java LibraryManagementSystem
   ```

## How to Use

1. **Add a Book**:
   - Fill in the book details in the "Book Details" tab.
   - Click the **Add Book** button to add the book to the list.

2. **Update a Book**:
   - Enter the ISBN of the book to update in the "Book Details" tab.
   - Modify the necessary fields and click the **Update Book** button.

3. **Search for a Book**:
   - Enter a title or author in the search bar on the "Book List" tab.
   - Click the **Search** button to filter books.

4. **Remove a Book**:
   - Select a book from the table in the "Book List" tab.
   - Click the **Remove Book** button in the toolbar to delete the book.

## Project Structure

- **LibraryManagementSystem**: Main class containing the GUI and event-handling logic.
- **Book**: Inner class representing a book entity with attributes like title, author, ISBN, etc.

## Customizations

- Genres available: Fiction, Non-fiction, Science, History, Fantasy (can be modified in the `genreCombo` dropdown).
- Colors: The panels and components have customized colors and fonts.


## Credits

Made by Shashank Singh.


