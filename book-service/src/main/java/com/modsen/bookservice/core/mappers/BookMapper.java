package com.modsen.bookservice.core.mappers;

import com.modsen.bookservice.core.dto.BookAddingDTO;
import com.modsen.bookservice.core.dto.BookDTO;
import com.modsen.bookservice.entities.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDTO entityToDto(Book book);

    Book dtoToEntity(BookDTO bookDTO);

    Book addingDtoToEntity(BookAddingDTO bookAddingDTO);
}
