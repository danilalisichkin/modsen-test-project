package com.modsen.bookservice.impl;

import com.modsen.bookservice.contollers.clients.LibraryClient;
import com.modsen.bookservice.core.dto.BookAddingDTO;
import com.modsen.bookservice.core.dto.BookDTO;
import com.modsen.bookservice.core.dto.library.BookInventoryAddingDTO;
import com.modsen.bookservice.core.dto.library.BookInventoryDTO;
import com.modsen.bookservice.core.mappers.BookMapper;
import com.modsen.bookservice.dao.repository.BookRepository;
import com.modsen.bookservice.entities.Book;
import com.modsen.bookservice.exceptions.ResourceNotFoundException;
import com.modsen.bookservice.services.impl.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
                .title("Test title")
                .author("Test author")
                .description("Test description")
                .genre("Test genre")
                .build();
        bookDTO = BookDTO.builder()
                .id(1L)
                .title("Test title")
                .author("Test author")
                .description("Test description")
                .genre("Test genre")
                .build();
        bookAddingDTO = BookAddingDTO.builder()
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
        when(bookRepository.findByIsbn("123456789")).thenReturn(Optional.of(book));
        when(bookMapper.entityToDto(book)).thenReturn(bookDTO);

        BookDTO returnedBook = bookService.getBookByISBN("123456789");

        assertNotNull(returnedBook);
        assertEquals(bookDTO, returnedBook);
    }

    @Test
    void whenGetNotExistingBookByIsbn_thenThrowNotFound() {
        when(bookRepository.findByIsbn("123456789")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> bookService.getBookByISBN("123456789"));
    }

    @Test
    void whenSaveBook_thenReturnBook() {
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.addingDtoToEntity(bookAddingDTO)).thenReturn(book);
        when(bookMapper.entityToInventoryAddingDto(book)).thenReturn(bookInventoryAddingDTO);
        when(bookMapper.entityToDto(book)).thenReturn(bookDTO);
        when(libraryClient.addBookInventory(bookInventoryAddingDTO)).thenReturn(null);

        BookDTO returnedBook = bookService.saveBook(bookAddingDTO);

        assertNotNull(returnedBook);
        assertEquals(bookDTO, returnedBook);
    }

    @Test
    void whenUpdateBook_thenReturnBook() {
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.dtoToEntity(bookDTO)).thenReturn(book);
        when(bookMapper.entityToDto(book)).thenReturn(bookDTO);

        BookDTO returnedBook = bookService.updateBook(bookDTO);

        assertNotNull(returnedBook);
        assertEquals(bookDTO, returnedBook);
    }
}
