package com.modsen.bookservice.core.dto.library;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entry to add a book to library inventory")
public class BookInventoryAddingDTO {

    @NotNull
    private Long bookId;
}
