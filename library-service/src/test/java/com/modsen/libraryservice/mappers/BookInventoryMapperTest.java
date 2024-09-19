package com.modsen.libraryservice.mappers;

import com.modsen.libraryservice.core.dto.BookInventoryAddingDTO;
import com.modsen.libraryservice.core.dto.BookInventoryDTO;
import com.modsen.libraryservice.core.mappers.BookInventoryMapper;
import com.modsen.libraryservice.entities.BookInventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BookInventoryMapperTest {

    @Autowired
    private BookInventoryMapper bookInventoryMapper;

    private BookInventory bookInventory;
    private BookInventoryDTO bookInventoryDTO;
    private BookInventoryAddingDTO bookInventoryAddingDTO;

    @BeforeEach
    void setUp() {
        bookInventory = BookInventory.builder()
                .id(1L)
                .bookId(1L)
                .borrowedAt(LocalDateTime.now().minusDays(1))
                .returnBy(LocalDateTime.now().plusDays(1))
                .build();
        bookInventoryDTO = BookInventoryDTO.builder()
                .id(1L)
                .bookId(1L)
                .borrowedAt(LocalDateTime.now().minusDays(1))
                .returnBy(LocalDateTime.now().plusDays(1))
                .build();
        bookInventoryAddingDTO = BookInventoryAddingDTO.builder()
                .bookId(1L)
                .build();
    }

    @Test
    void shouldConvertAddingDTOToEntity() {
        BookInventory convertedEntity = bookInventoryMapper.addingDtoToEntity(bookInventoryAddingDTO);

        assertNotNull(convertedEntity);
        assertEquals(bookInventoryAddingDTO.getBookId(), convertedEntity.getBookId());
        assertNull(convertedEntity.getId());
        assertNull(convertedEntity.getBorrowedAt());
        assertNull(convertedEntity.getReturnBy());
    }

    @Test
    void shouldConvertEntityToDTO() {
        BookInventoryDTO convertedDTO = bookInventoryMapper.entityToDto(bookInventory);

        assertNotNull(convertedDTO);
        assertEquals(bookInventory.getId(), convertedDTO.getId());
        assertEquals(bookInventory.getBookId(), convertedDTO.getBookId());
        assertEquals(bookInventory.getBorrowedAt(), convertedDTO.getBorrowedAt());
        assertEquals(bookInventory.getReturnBy(), convertedDTO.getReturnBy());
    }

    @Test
    void shouldConvertDTOToEntity() {
        BookInventory convertedEntity = bookInventoryMapper.dtoToEntity(bookInventoryDTO);

        assertNotNull(convertedEntity);
        assertEquals(bookInventoryDTO.getId(), convertedEntity.getId());
        assertEquals(bookInventoryDTO.getBookId(), convertedEntity.getBookId());
        assertEquals(bookInventoryDTO.getBorrowedAt(), convertedEntity.getBorrowedAt());
        assertEquals(bookInventoryDTO.getReturnBy(), convertedEntity.getReturnBy());
    }

    @Test
    void shouldReturnNullWhenAddingDTOIsNull() {
        assertNull(bookInventoryMapper.addingDtoToEntity(null));
    }

    @Test
    void shouldReturnNullWhenEntityIsNull() {
        assertNull(bookInventoryMapper.entityToDto(null));
    }

    @Test
    void shouldReturnNullWhenDTOIsNull() {
        assertNull(bookInventoryMapper.dtoToEntity(null));
    }
}