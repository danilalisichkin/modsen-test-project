package com.modsen.libraryservice.core.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BookInventoryDTO {

    private Long id;

    private Long bookId;

    private LocalDateTime borrowedAt;

    private LocalDateTime returnBy;
}
