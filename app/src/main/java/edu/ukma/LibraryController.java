package edu.ukma;

import com.google.gson.Gson;
import static spark.Spark.*;

public class LibraryController {
    private final LibraryService library = new LibraryService();
    private final Gson gson = new Gson();

    public void start() {
        port(4567);

        post("/books", (req, res) -> {
            Book book = gson.fromJson(req.body(), Book.class);
            library.addBookToLibrary(book.getTitle(), book.getAuthor());
            res.status(201);
            return "";
        });

        get("/books", (req, res) -> {
            res.type("application/json");
            return gson.toJson(library.getAllBooks());
        });

        get("/books/:title", (req, res) -> {
            String title = req.params(":title");
            Book book = library.findBookByTitle(title);
            if (book != null) {
                return gson.toJson(book);
            } else {
                res.status(404);
                return "";
            }
        });

        put("/books/:title", (req, res) -> {
            String title = req.params(":title");
            String newAuthor = req.queryParams("author");
            boolean updated = library.updateBookAuthor(title, newAuthor);
            if (updated) {
                return "";
            } else {
                res.status(404);
                return "";
            }
        });

        delete("/books/:title", (req, res) -> {
            String title = req.params(":title");
            boolean removed = library.removeBookByTitle(title);
            if (removed) {
                return "";
            } else {
                res.status(404);
                return "";
            }
        });
    }
}
