package com.modsen.bookservice.core.dto.library;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "Entry to get/update/delete book in library inventory")
public class BookInventoryDTO {

    @NotNull
    private Long id;

    @NotNull
    private Long bookId;

    private LocalDateTime borrowedAt;
    private LocalDateTime returnBy;
}
