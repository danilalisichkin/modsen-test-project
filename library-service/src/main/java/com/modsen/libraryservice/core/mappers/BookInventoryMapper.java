package com.modsen.libraryservice.core.mappers;

import com.modsen.libraryservice.core.dto.BookInventoryAddingDTO;
import com.modsen.libraryservice.core.dto.BookInventoryDTO;
import com.modsen.libraryservice.entities.BookInventory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookInventoryMapper {
    BookInventory addingDtoToEntity(BookInventoryAddingDTO bookInventoryAddingDTO);
    BookInventoryDTO entityToDto(BookInventory bookInventory);
    BookInventory dtoToEntity(BookInventoryDTO bookInventoryDTO);
}
