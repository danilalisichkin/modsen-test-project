package com.modsen.bookservice.services.impl;

import com.modsen.bookservice.core.dto.BookAddingDTO;
import com.modsen.bookservice.core.dto.BookDTO;
import com.modsen.bookservice.core.mappers.BookMapper;
import com.modsen.bookservice.dao.repository.BookRepository;
import com.modsen.bookservice.entities.Book;
import com.modsen.bookservice.exceptions.ResourceNotFoundException;
import com.modsen.bookservice.services.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService implements IBookService {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    @Autowired
    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> bookMapper.entityToDto(book))
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO getBookById(Long id) {
        return bookMapper.entityToDto(
                bookRepository.findById(id).orElseThrow(
                        () -> new ResourceNotFoundException("Book with id=%s not found", id)
                )
        );
    }

    @Override
    public BookDTO getBookByISBN(String isbn) {
        return bookMapper.entityToDto(
                bookRepository.findByIsbn(isbn).orElseThrow(
                        () -> new ResourceNotFoundException("Book with isbn=%s not found", isbn)
                )
        );
    }

    @Override
    public BookDTO saveBook(BookAddingDTO bookAddingDTO) {
        Book book = bookRepository.save(bookMapper.addingDtoToEntity(bookAddingDTO));

        return bookMapper.entityToDto(book);
    }

    @Override
    public BookDTO updateBook(BookDTO bookDTO) {
        return bookMapper.entityToDto(
                bookRepository.save(bookMapper.dtoToEntity(bookDTO))
        );
    }

    @Override
    public void deleteBook(BookDTO bookDTO) {
        bookRepository.delete(bookMapper.dtoToEntity(bookDTO));
    }
}
