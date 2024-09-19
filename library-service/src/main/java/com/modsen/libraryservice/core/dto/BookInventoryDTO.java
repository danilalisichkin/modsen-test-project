package com.modsen.libraryservice.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

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
