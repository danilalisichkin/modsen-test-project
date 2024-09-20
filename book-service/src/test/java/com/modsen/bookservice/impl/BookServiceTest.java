package com.modsen.bookservice.impl;

import com.modsen.bookservice.contollers.clients.LibraryClient;
import com.modsen.bookservice.core.dto.BookAddingDTO;
import com.modsen.bookservice.core.dto.BookDTO;
import com.modsen.bookservice.core.dto.library.BookInventoryAddingDTO;
import com.modsen.bookservice.core.dto.library.BookInventoryDTO;
import com.modsen.bookservice.core.mappers.BookMapper;
import com.modsen.bookservice.dao.repository.BookRepository;
import com.modsen.bookservice.entities.Book;
import com.modsen.bookservice.exceptions.DataUniquenessConflictException;
import com.modsen.bookservice.exceptions.ResourceNotFoundException;
import com.modsen.bookservice.services.impl.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private LibraryClient libraryClient;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private BookDTO bookDTO;
    private BookAddingDTO bookAddingDTO;
    private BookInventoryDTO bookInventoryDTO;
    private BookInventoryAddingDTO bookInventoryAddingDTO;

    @BeforeEach
    void setUp() {
        book = Book.builder()
                .id(1L)
                .isbn("1234567890")
                .title("Test title")
                .author("Test author")
                .description("Test description")
                .genre("Test genre")
                .build();
        bookDTO = BookDTO.builder()
                .id(1L)
                .isbn("1234567890")
                .title("Test title")
                .author("Test author")
                .description("Test description")
                .genre("Test genre")
                .build();
        bookAddingDTO = BookAddingDTO.builder()
                .isbn("1234567890")
                .title("Test title")
                .author("Test author")
                .description("Test description")
                .genre("Test genre")
                .build();
        bookInventoryDTO = bookInventoryDTO.builder()
                .bookId(1L)
                .borrowedAt(null)
                .returnBy(null)
                .build();
        bookInventoryAddingDTO = BookInventoryAddingDTO.builder()
                .bookId(1L)
                .build();
    }

    @Test
    void whenGetAllBooks_thenReturnBooks() {
        when(bookRepository.findAll()).thenReturn(List.of(book, book));
        when(bookMapper.entityToDto(book)).thenReturn(bookDTO);

        List<BookDTO> allBooks = bookService.getAllBooks();

        assertNotNull(allBooks);
        assertEquals(List.of(bookDTO, bookDTO), allBooks);
    }

    @Test
    void whenGetExistingBookById_thenReturnBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.entityToDto(book)).thenReturn(bookDTO);

        BookDTO returnedBook = bookService.getBookById(1L);

        assertNotNull(returnedBook);
        assertEquals(bookDTO, returnedBook);
    }

    @Test
    void whenGetNotExistingBookById_thenThrowNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.getBookById(1L));
    }

    @Test
    void whenGetExistingBookByIsbn_thenReturnBook() {
        when(bookRepository.findByIsbn("1234567890")).thenReturn(Optional.of(book));
        when(bookMapper.entityToDto(book)).thenReturn(bookDTO);

        BookDTO returnedBook = bookService.getBookByISBN("1234567890");

        assertNotNull(returnedBook);
        assertEquals(bookDTO, returnedBook);
    }

    @Test
    void whenGetNotExistingBookByIsbn_thenThrowNotFound() {
        when(bookRepository.findByIsbn("1234567890")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> bookService.getBookByISBN("1234567890"));
    }

    @Test
    void whenSaveValidBook_thenReturnBook() {
        when(bookRepository.findByIsbn("1234567890")).thenReturn(Optional.empty());
        when(bookMapper.addingDtoToEntity(bookAddingDTO)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.entityToInventoryAddingDto(book)).thenReturn(bookInventoryAddingDTO);
        when(libraryClient.addBookInventory(bookInventoryAddingDTO)).thenReturn(ResponseEntity.ok(bookInventoryDTO));
        when(bookMapper.entityToDto(book)).thenReturn(bookDTO);

        BookDTO returnedBook = bookService.saveBook(bookAddingDTO);

        assertNotNull(returnedBook);
        assertEquals(bookDTO, returnedBook);
        verify(libraryClient).addBookInventory(any());
    }

    @Test
    void whenSaveNotValidBook_thenThrowDataConflict() {
        when(bookRepository.findByIsbn("1234567890")).thenReturn(Optional.of(book));

        assertThrows(DataUniquenessConflictException.class, () -> bookService.saveBook(bookAddingDTO));
    }

    @Test
    void whenUpdateExistingBook_thenReturnBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.dtoToEntity(bookDTO)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.entityToDto(book)).thenReturn(bookDTO);

        BookDTO returnedBook = bookService.updateBook(bookDTO);

        assertNotNull(returnedBook);
        assertEquals(bookDTO, returnedBook);
    }

    @Test
    void whenUpdateNotExistingBook_thenThrowNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.updateBook(bookDTO));
    }

    @Test
    void whenDeleteExistingBookById_thenSuccess() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        assertDoesNotThrow(() -> bookService.deleteBookById(1L));
        verify(bookRepository).deleteById(1L);
    }

    @Test
    void whenDeleteNotExistingBook_thenThrowNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.deleteBookById(1L));
    }
}
