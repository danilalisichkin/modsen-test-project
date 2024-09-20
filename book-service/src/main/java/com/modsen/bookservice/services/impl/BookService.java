package com.modsen.bookservice.services.impl;

import com.modsen.bookservice.contollers.clients.LibraryClient;
import com.modsen.bookservice.core.dto.BookAddingDTO;
import com.modsen.bookservice.core.dto.BookDTO;
import com.modsen.bookservice.core.mappers.BookMapper;
import com.modsen.bookservice.dao.repository.BookRepository;
import com.modsen.bookservice.entities.Book;
import com.modsen.bookservice.exceptions.DataUniquenessConflictException;
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

    private final LibraryClient libraryClient;

    @Autowired
    public BookService(BookRepository bookRepository, BookMapper bookMapper, LibraryClient libraryClient) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.libraryClient = libraryClient;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::entityToDto)
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
        if (bookRepository.findByIsbn(bookAddingDTO.getIsbn()).isPresent()) {
            throw new DataUniquenessConflictException();
        }

        Book book = bookRepository.save(bookMapper.addingDtoToEntity(bookAddingDTO));

        libraryClient.addBookInventory(bookMapper.entityToInventoryAddingDto(book));

        return bookMapper.entityToDto(book);
    }

    @Override
    public BookDTO updateBook(BookDTO bookDTO) {
        if (bookRepository.findById(bookDTO.getId()).isEmpty()) {
            throw new ResourceNotFoundException("Book with id=%s not found", bookDTO.getId());
        }

        return bookMapper.entityToDto(
                bookRepository.save(bookMapper.dtoToEntity(bookDTO))
        );
    }

    @Override
    public void deleteBookById(Long id) {
        if (bookRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Book with id=%s not found", id);
        }
        bookRepository.deleteById(id);
    }
}
