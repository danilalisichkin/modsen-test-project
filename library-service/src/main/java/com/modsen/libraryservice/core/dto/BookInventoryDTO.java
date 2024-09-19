package com.modsen.libraryservice.core.dto;

import lombok.Builder;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

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
