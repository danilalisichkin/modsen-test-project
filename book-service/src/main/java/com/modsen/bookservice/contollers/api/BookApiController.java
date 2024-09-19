package com.modsen.bookservice.contollers.api;

import com.modsen.bookservice.core.dto.BookAddingDTO;
import com.modsen.bookservice.core.dto.BookDTO;
import com.modsen.bookservice.services.IBookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookApiController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final IBookService bookService;

    @Autowired
    public BookApiController(IBookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        logger.info("Sending all books");

        List<BookDTO> books = bookService.getAllBooks();

        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@NotNull @PathVariable Long id) {
        logger.info("Sending book with id={}", id);

        BookDTO bookDTO = bookService.getBookById(id);

        return ResponseEntity.status(HttpStatus.OK).body(bookDTO);
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDTO> getBookByISBN(
            @Pattern(regexp = "^(?:\\d{9}X|\\d{10}|\\d{13})$")
            @PathVariable String isbn) {

        logger.info("Sending book with ISBN={}", isbn);

        BookDTO bookDTO = bookService.getBookByISBN(isbn);

        return ResponseEntity.status(HttpStatus.OK).body(bookDTO);
    }

    @PostMapping
    public ResponseEntity<BookDTO> saveBook(@Valid @RequestBody BookAddingDTO bookAddingDTO) {
        logger.info("Saving book with ISBN={}", bookAddingDTO.getIsbn());

        BookDTO savedBookDTO = bookService.saveBook(bookAddingDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedBookDTO);
    }

    @PutMapping
    public ResponseEntity<BookDTO> updateBook(@Valid @RequestBody BookDTO bookDTO) {
        logger.info("Updating book with id={}", bookDTO.getId());

        BookDTO updatedBookDTO = bookService.updateBook(bookDTO);

        return ResponseEntity.status(HttpStatus.OK).body(updatedBookDTO);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBook(@Valid @RequestBody BookDTO bookDTO) {
        logger.info("Deleting book with id={}", bookDTO.getId());

        bookService.deleteBook(bookDTO);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
