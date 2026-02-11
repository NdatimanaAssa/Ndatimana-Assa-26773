package auca.ac.rw.restfullApiAssignment.controller.library;

import auca.ac.rw.restfullApiAssignment.modal.library.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing library books
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    // In-memory list to store books
    private List<Book> books = new ArrayList<>();
    private Long nextId = 4L;

    // Initialize with sample data
    public BookController() {
        books.add(new Book(1L, "Clean Code", "Robert Martin", "978-0132350884", 2008));
        books.add(new Book(2L, "Effective Java", "Joshua Bloch", "978-0134685991", 2017));
        books.add(new Book(3L, "Design Patterns", "Gang of Four", "978-0201633612", 1994));
    }

    /**
     * GET /api/books - Return list of all books
     */
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    /**
     * GET /api/books/{id} - Return a specific book by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = books.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();
        
        if (book.isPresent()) {
            return new ResponseEntity<>(book.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET /api/books/search?title={title} - Search books by title
     */
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooksByTitle(@RequestParam String title) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(book);
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * POST /api/books - Add a new book
     */
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        book.setId(nextId++);
        books.add(book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    /**
     * DELETE /api/books/{id} - Delete a book by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean removed = books.removeIf(book -> book.getId().equals(id));
        
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}