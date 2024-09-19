package com.modsen.bookservice.mappers;

import com.modsen.bookservice.core.dto.BookAddingDTO;
import com.modsen.bookservice.core.dto.BookDTO;
import com.modsen.bookservice.core.dto.library.BookInventoryAddingDTO;
import com.modsen.bookservice.core.mappers.BookMapper;
import com.modsen.bookservice.entities.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BookMapperTest {

    @Autowired
    private BookMapper bookMapper;

    private Book book;
    private BookDTO bookDTO;
    private BookAddingDTO bookAddingDTO;
    private BookInventoryAddingDTO bookInventoryAddingDTO;

    @BeforeEach
    void setUp() {
        book = Book.builder()
                .id(1L)
                .isbn("123456789")
                .title("Test Title")
                .genre("Fiction")
                .description("Test Description")
                .author("Test Author")
                .build();
        bookDTO = BookDTO.builder()
                .id(1L)
                .isbn("123456789")
                .title("Test Title")
                .genre("Fiction")
                .description("Test Description")
                .author("Test Author")
                .build();
        bookAddingDTO = BookAddingDTO.builder()
                .isbn("987654321")
                .title("New Title")
                .genre("Non-Fiction")
                .description("New Description")
                .author("New Author")
                .build();
        bookInventoryAddingDTO = BookInventoryAddingDTO.builder()
                .bookId(1L)
                .build();
    }

    @Test
    void shouldConvertEntityToDTO() {
        BookDTO convertedDTO = bookMapper.entityToDto(book);

        assertNotNull(convertedDTO);
        assertEquals(bookDTO, convertedDTO);
    }

    @Test
    void shouldConvertDTOToEntity() {
        Book convertedEntity = bookMapper.dtoToEntity(bookDTO);

        assertNotNull(convertedEntity);
        assertEquals(bookDTO.getId(), convertedEntity.getId());
        assertEquals(bookDTO.getIsbn(), convertedEntity.getIsbn());
        assertEquals(bookDTO.getTitle(), convertedEntity.getTitle());
        assertEquals(bookDTO.getGenre(), convertedEntity.getGenre());
        assertEquals(bookDTO.getDescription(), convertedEntity.getDescription());
        assertEquals(bookDTO.getAuthor(), convertedEntity.getAuthor());
    }

    @Test
    void shouldConvertAddingDTOToEntity() {
        Book convertedEntity = bookMapper.addingDtoToEntity(bookAddingDTO);

        assertNotNull(convertedEntity);
        assertEquals(bookAddingDTO.getIsbn(), convertedEntity.getIsbn());
        assertEquals(bookAddingDTO.getTitle(), convertedEntity.getTitle());
        assertEquals(bookAddingDTO.getGenre(), convertedEntity.getGenre());
        assertEquals(bookAddingDTO.getDescription(), convertedEntity.getDescription());
        assertEquals(bookAddingDTO.getAuthor(), convertedEntity.getAuthor());
        assertNull(convertedEntity.getId());
    }

    @Test
    void shouldConvertEntityToInventoryAddingDTO() {
        BookInventoryAddingDTO convertedDTO = bookMapper.entityToInventoryAddingDto(book);

        assertNotNull(convertedDTO);
        assertEquals(book.getId(), convertedDTO.getBookId());
    }

    @Test
    void shouldReturnNullWhenAddingDTOIsNull() {
        assertNull(bookMapper.addingDtoToEntity(null));
    }

    @Test
    void shouldReturnNullWhenEntityIsNull() {
        assertNull(bookMapper.entityToDto(null));
        assertNull(bookMapper.entityToInventoryAddingDto(null));
    }

    @Test
    void shouldReturnNullWhenDTOIsNull() {
        assertNull(bookMapper.dtoToEntity(null));
    }
}
