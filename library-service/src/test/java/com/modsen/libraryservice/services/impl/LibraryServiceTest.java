package com.modsen.libraryservice.services.impl;

import com.modsen.libraryservice.core.dto.BookInventoryAddingDTO;
import com.modsen.libraryservice.core.dto.BookInventoryDTO;
import com.modsen.libraryservice.core.mappers.BookInventoryMapper;
import com.modsen.libraryservice.entities.BookInventory;
import com.modsen.libraryservice.exceptions.ResourceNotFoundException;
import com.modsen.libraryservice.repository.BookInventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LibraryServiceTest {

    @Mock
    private BookInventoryRepository bookInventoryRepository;

    @Mock
    private BookInventoryMapper bookInventoryMapper;

    @InjectMocks
    private LibraryService libraryService;

    private BookInventory bookInventory;
    private BookInventoryDTO bookInventoryDTO;
    private BookInventoryAddingDTO bookInventoryAddingDTO;

    @BeforeEach
    void setUp() {
        bookInventory = BookInventory.builder()
                .id(1L)
                .bookId(1L)
                .borrowedAt(null)
                .returnBy(null)
                .build();
        bookInventoryDTO = BookInventoryDTO.builder()
                .id(1L)
                .bookId(1L)
                .borrowedAt(null)
                .returnBy(null)
                .build();
        bookInventoryAddingDTO = BookInventoryAddingDTO.builder()
                .bookId(1L)
                .build();
    }

    @Test
    void whenAddValidBookInventory_thenReturnBookInventoryDTO() {
        when(bookInventoryMapper.addingDtoToEntity(bookInventoryAddingDTO)).thenReturn(bookInventory);
        when(bookInventoryRepository.save(bookInventory)).thenReturn(bookInventory);
        when(bookInventoryMapper.entityToDto(bookInventory)).thenReturn(bookInventoryDTO);

        BookInventoryDTO returnedDTO = libraryService.addBookInventory(bookInventoryAddingDTO);

        assertNotNull(returnedDTO);
        assertEquals(bookInventoryDTO, returnedDTO);
    }

    @Test
    void whenGetAvailableBookInventory_thenReturnListOfBookInventoryDTOs() {
        List<BookInventory> bookInventories = Arrays.asList(bookInventory);
        List<BookInventoryDTO> bookInventoryDTOs = Arrays.asList(bookInventoryDTO);

        when(bookInventoryRepository.findByBorrowedAtIsNullOrReturnByBefore(any(LocalDateTime.class)))
                .thenReturn(bookInventories);
        when(bookInventoryMapper.entityToDto(bookInventory)).thenReturn(bookInventoryDTO);

        List<BookInventoryDTO> returnedDTOs = libraryService.getAvailableBookInventory();

        assertNotNull(returnedDTOs);
        assertFalse(returnedDTOs.isEmpty());
        assertEquals(bookInventoryDTOs, returnedDTOs);
    }

    @Test
    void whenUpdateExistingBookInventory_thenReturnBookInventoryDTO() {
        when(bookInventoryRepository.findById(1l)).thenReturn(Optional.of(bookInventory));
        when(bookInventoryMapper.dtoToEntity(bookInventoryDTO)).thenReturn(bookInventory);
        when(bookInventoryRepository.save(bookInventory)).thenReturn(bookInventory);
        when(bookInventoryMapper.entityToDto(bookInventory)).thenReturn(bookInventoryDTO);

        BookInventoryDTO returnedDTO = libraryService.updateBookInventory(bookInventoryDTO);

        assertNotNull(returnedDTO);
        assertEquals(bookInventoryDTO, returnedDTO);
    }

    @Test
    void whenUpdateNotExistingBookInventory_thenThrowNotFound() {
        when(bookInventoryRepository.findById(1l)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> libraryService.updateBookInventory(bookInventoryDTO));
    }
}
