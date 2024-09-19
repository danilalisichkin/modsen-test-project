package com.modsen.bookservice.services;

import com.modsen.bookservice.entities.Book;

import java.util.List;

public interface IBookService {

    List<Book> getAllBooks();

    Book getBookById(Long id);

    Book getBookByISBN(String isbn);

    Book saveBook(Book bookDTO);

    Book updateBook(Book bookDTO);

    void deleteBook(Book bookDTO);
}
