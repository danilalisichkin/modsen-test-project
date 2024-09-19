package com.modsen.bookservice.contollers.api;

import com.modsen.bookservice.core.dto.BookAddingDTO;
import com.modsen.bookservice.core.dto.BookDTO;
import com.modsen.bookservice.services.IBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@SecurityRequirement(name = "token")
@Tag(name="BookController", description="Provides CRUD-operations with books and getting by isbn")
public class BookApiController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final IBookService bookService;

    @Autowired
    public BookApiController(IBookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    @Operation(summary = "Get all", description = "Allows to get all existing books")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        logger.info("Sending all books");

        List<BookDTO> books = bookService.getAllBooks();

        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get by id", description = "Allows to get book by its id")
    public ResponseEntity<BookDTO> getBookById(@NotNull @PathVariable Long id) {
        logger.info("Sending book with id={}", id);

        BookDTO bookDTO = bookService.getBookById(id);

        return ResponseEntity.status(HttpStatus.OK).body(bookDTO);
    }

    @GetMapping("/isbn/{isbn}")
    @Operation(summary = "Get by ISBN", description = "Allows to get book by its isbn")
    public ResponseEntity<BookDTO> getBookByISBN(
            @Pattern(regexp = "^(?:\\d{9}X|\\d{10}|\\d{13})$")
            @PathVariable String isbn) {

        logger.info("Sending book with ISBN={}", isbn);

        BookDTO bookDTO = bookService.getBookByISBN(isbn);

        return ResponseEntity.status(HttpStatus.OK).body(bookDTO);
    }

    @PostMapping
    @Operation(summary = "Add/save new", description = "Allows to save new book")
    public ResponseEntity<BookDTO> saveBook(@Valid @RequestBody BookAddingDTO bookAddingDTO) {
        logger.info("Saving book with ISBN={}", bookAddingDTO.getIsbn());

        BookDTO savedBookDTO = bookService.saveBook(bookAddingDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedBookDTO);
    }

    @PutMapping
    @Operation(summary = "Update", description = "Allows to update data of existing book")
    public ResponseEntity<BookDTO> updateBook(@Valid @RequestBody BookDTO bookDTO) {
        logger.info("Updating book with id={}", bookDTO.getId());

        BookDTO updatedBookDTO = bookService.updateBook(bookDTO);

        return ResponseEntity.status(HttpStatus.OK).body(updatedBookDTO);
    }

    @DeleteMapping
    @Operation(summary = "Delete", description = "Allows to delete existing book")
    public ResponseEntity<Void> deleteBook(@Valid @RequestBody BookDTO bookDTO) {
        logger.info("Deleting book with id={}", bookDTO.getId());

        bookService.deleteBook(bookDTO);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
