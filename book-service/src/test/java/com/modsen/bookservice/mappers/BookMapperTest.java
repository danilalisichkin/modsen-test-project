package com.modsen.bookservice.mappers;

import com.modsen.bookservice.core.dto.BookAddingDTO;
import com.modsen.bookservice.core.dto.BookDTO;
import com.modsen.bookservice.core.dto.library.BookInventoryAddingDTO;
import com.modsen.bookservice.core.mappers.BookMapper;
import com.modsen.bookservice.entities.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BookMapperTest {

    private BookMapper bookMapper;

    private Book book;
    private BookDTO bookDTO;
    private BookAddingDTO bookAddingDTO;
    private BookInventoryAddingDTO bookInventoryAddingDTO;

    @BeforeEach
    void setUp() {
        bookMapper = Mappers.getMapper(BookMapper.class);

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
                .isbn("123456789")
                .title("Test Title")
                .genre("Fiction")
                .description("Test Description")
                .author("Test Author")
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
        assertEquals(book.getId(), convertedEntity.getId());
        assertEquals(book.getIsbn(), convertedEntity.getIsbn());
        assertEquals(book.getTitle(), convertedEntity.getTitle());
        assertEquals(book.getGenre(), convertedEntity.getGenre());
        assertEquals(book.getAuthor(), convertedEntity.getAuthor());
        assertEquals(book.getDescription(), convertedEntity.getDescription());
    }

    @Test
    void shouldConvertAddingDTOToEntity() {
        Book convertedEntity = bookMapper.addingDtoToEntity(bookAddingDTO);

        assertNotNull(convertedEntity);
        assertNull(convertedEntity.getId());
        assertEquals(book.getIsbn(), convertedEntity.getIsbn());
        assertEquals(book.getTitle(), convertedEntity.getTitle());
        assertEquals(book.getGenre(), convertedEntity.getGenre());
        assertEquals(book.getAuthor(), convertedEntity.getAuthor());
        assertEquals(book.getDescription(), convertedEntity.getDescription());
        assertNull(convertedEntity.getId());
    }

    @Test
    void shouldConvertEntityToInventoryAddingDTO() {
        BookInventoryAddingDTO convertedDTO = bookMapper.entityToInventoryAddingDto(book);

        assertNotNull(convertedDTO);
        assertEquals(bookInventoryAddingDTO, convertedDTO);
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
