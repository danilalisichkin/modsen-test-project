package com.modsen.libraryservice.api;

import com.modsen.libraryservice.core.dto.BookInventoryAddingDTO;
import com.modsen.libraryservice.core.dto.BookInventoryDTO;
import com.modsen.libraryservice.services.ILibraryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/library")
public class BookInventoryApiController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ILibraryService libraryService;

    @Autowired
    public BookInventoryApiController(ILibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping
    public ResponseEntity<BookInventoryDTO> addBookInventory(
            @Valid @RequestBody BookInventoryAddingDTO bookInventoryAddingDTO) {

        logger.info("Adding inventory information for book with id={}", bookInventoryAddingDTO.getBookId());

        BookInventoryDTO bookInventoryDTO = libraryService.addBookInventory(bookInventoryAddingDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(bookInventoryDTO);
    }

    @GetMapping("/available")
    public ResponseEntity<List<BookInventoryDTO>> getAvailableBookInventory() {
        logger.info("Sending available book inventories");

        List<BookInventoryDTO> availableBooks = libraryService.getAvailableBookInventory();

        return ResponseEntity.status(HttpStatus.OK).body(availableBooks);
    }

    @PutMapping
    public ResponseEntity<BookInventoryDTO> updateBookInventory(@Valid @RequestBody BookInventoryDTO bookInventoryDTO) {
        logger.info("Updating book inventory information for book with id={}", bookInventoryDTO.getBookId());

        BookInventoryDTO updatedBookInventory = libraryService.updateBookInventory(bookInventoryDTO);

        return ResponseEntity.status(HttpStatus.OK).body(updatedBookInventory);
    }
}
