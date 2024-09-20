package com.modsen.bookservice.services;

import com.modsen.bookservice.core.dto.BookAddingDTO;
import com.modsen.bookservice.core.dto.BookDTO;

import java.util.List;

public interface IBookService {

    List<BookDTO> getAllBooks();

    BookDTO getBookById(Long id);

    BookDTO getBookByISBN(String isbn);

    BookDTO saveBook(BookAddingDTO bookDTO);

    BookDTO updateBook(BookDTO bookDTO);

    void deleteBookById(Long id);
}
