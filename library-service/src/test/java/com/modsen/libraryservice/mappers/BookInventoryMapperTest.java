package com.modsen.libraryservice.mappers;

import com.modsen.libraryservice.core.dto.BookInventoryAddingDTO;
import com.modsen.libraryservice.core.dto.BookInventoryDTO;
import com.modsen.libraryservice.core.mappers.BookInventoryMapper;
import com.modsen.libraryservice.entities.BookInventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BookInventoryMapperTest {

    private BookInventoryMapper bookInventoryMapper;

    private BookInventory bookInventory;
    private BookInventoryDTO bookInventoryDTO;
    private BookInventoryAddingDTO bookInventoryAddingDTO;

    @BeforeEach
    void setUp() {
        bookInventoryMapper = Mappers.getMapper(BookInventoryMapper.class);

        LocalDateTime borrowTime = LocalDateTime.now().minusDays(1);
        LocalDateTime returnTime = LocalDateTime.now().plusDays(1);

        bookInventory = BookInventory.builder()
                .id(1L)
                .bookId(1L)
                .borrowedAt(borrowTime)
                .returnBy(returnTime)
                .build();
        bookInventoryDTO = BookInventoryDTO.builder()
                .id(1L)
                .bookId(1L)
                .borrowedAt(borrowTime)
                .returnBy(returnTime)
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
        assertEquals(bookInventoryDTO, convertedDTO);
    }

    @Test
    void shouldConvertDTOToEntity() {
        BookInventory convertedEntity = bookInventoryMapper.dtoToEntity(bookInventoryDTO);

        assertNotNull(convertedEntity);
        assertEquals(bookInventory.getId(), convertedEntity.getId());
        assertEquals(bookInventory.getBookId(), convertedEntity.getBookId());
        assertEquals(bookInventory.getBorrowedAt(), convertedEntity.getBorrowedAt());
        assertEquals(bookInventory.getReturnBy(), convertedEntity.getReturnBy());
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