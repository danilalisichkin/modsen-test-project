package com.modsen.bookservice.contollers.clients;

import com.modsen.bookservice.config.FeignLibraryClientConfig;
import com.modsen.bookservice.core.dto.library.BookInventoryAddingDTO;
import com.modsen.bookservice.core.dto.library.BookInventoryDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "library-service", path = "/api/v1/library", configuration = FeignLibraryClientConfig.class)
public interface LibraryClient {
    @PostMapping
    ResponseEntity<BookInventoryDTO> addBookInventory(
            @Valid @RequestBody BookInventoryAddingDTO bookInventoryAddingDTO);
}
