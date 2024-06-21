package edu.ukma;

import edu.ukma.Book;

import java.util.ArrayList;
import java.util.List;

public class LibraryService {
    private final List<Book> books = new ArrayList<>();

    public void addBookToLibrary(String title, String author) {
        addBook(new Book(title, author));
    }

    public void printAllBooks() {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public List<Book> getAllBooks() {
        return List.copyOf(books);
    }

    public Book findBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }

        return null;
    }

    public boolean removeBookByTitle(String title) {
        Book book = findBookByTitle(title);
        if (book != null) {
            books.remove(book);
            return true;
        }

        return false;
    }

    public boolean updateBookAuthor(String title, String newAuthor) {
        Book book = findBookByTitle(title);
        if (book != null) {
            books.remove(book);
            addBookToLibrary(title, newAuthor);
            return true;
        }

        return false;
    }

    private void addBook(Book book) {
        books.add(book);
    }
}