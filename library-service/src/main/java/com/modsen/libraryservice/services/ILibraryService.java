package com.modsen.libraryservice.services;

import com.modsen.libraryservice.entities.BookInventory;

import java.util.List;

public interface ILibraryService {
    BookInventory addBookInventory(BookInventory bookInventoryAddingDTO);

    List<BookInventory> getAvailableBookInventory();

    BookInventory updateBookInventory(BookInventory bookInventoryDTO);
}
