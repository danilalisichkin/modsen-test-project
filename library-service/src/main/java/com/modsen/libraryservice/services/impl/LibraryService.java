package com.modsen.libraryservice.services.impl;

import com.modsen.libraryservice.core.dto.BookInventoryAddingDTO;
import com.modsen.libraryservice.core.dto.BookInventoryDTO;
import com.modsen.libraryservice.core.mappers.BookInventoryMapper;
import com.modsen.libraryservice.entities.BookInventory;
import com.modsen.libraryservice.exceptions.ResourceNotFoundException;
import com.modsen.libraryservice.repository.BookInventoryRepository;
import com.modsen.libraryservice.services.ILibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryService implements ILibraryService {

    private final BookInventoryRepository bookInventoryRepository;

    private final BookInventoryMapper bookInventoryMapper;

    @Autowired
    public LibraryService(BookInventoryRepository bookInventoryRepository, BookInventoryMapper bookInventoryMapper) {
        this.bookInventoryRepository = bookInventoryRepository;
        this.bookInventoryMapper = bookInventoryMapper;
    }

    @Override
    public BookInventoryDTO addBookInventory(BookInventoryAddingDTO bookInventoryAddingDTO) {
        BookInventory bookInventory = bookInventoryMapper.addingDtoToEntity(bookInventoryAddingDTO);

        return bookInventoryMapper.entityToDto(bookInventoryRepository.save(bookInventory));
    }

    @Override
    public List<BookInventoryDTO> getAvailableBookInventory() {
        List<BookInventory> availableBooks = bookInventoryRepository.findByBorrowedAtIsNullOrReturnByBefore(LocalDateTime.now());

        if (availableBooks.isEmpty()) {
            throw new ResourceNotFoundException("No available books found");
        }

        return availableBooks.stream()
                .map(bookInventoryMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookInventoryDTO updateBookInventory(BookInventoryDTO bookInventoryDTO) {
        BookInventory bookInventory = bookInventoryMapper.dtoToEntity(bookInventoryDTO);

        return bookInventoryMapper.entityToDto(bookInventoryRepository.save(bookInventory));
    }
}
