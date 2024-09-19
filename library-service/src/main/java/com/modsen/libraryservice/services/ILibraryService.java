package com.modsen.libraryservice.services;

import com.modsen.libraryservice.core.dto.BookInventoryAddingDTO;
import com.modsen.libraryservice.core.dto.BookInventoryDTO;

import java.util.List;

public interface ILibraryService {
    BookInventoryDTO addBookInventory(BookInventoryAddingDTO bookInventoryAddingDTO);

    List<BookInventoryDTO> getAvailableBookInventory();

    BookInventoryDTO updateBookInventory(BookInventoryDTO bookInventoryDTO);
}
