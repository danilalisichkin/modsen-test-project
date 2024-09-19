package com.modsen.bookservice.core.dto.library;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BookInventoryDTO {

    @NotNull
    private Long id;

    @NotNull
    private Long bookId;

    private LocalDateTime borrowedAt;
    private LocalDateTime returnBy;
}
