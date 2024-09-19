package com.modsen.bookservice.core.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDTO {

    @NotNull
    private Long id;

    @NotNull
    @Pattern(regexp = "^(?:\\d{9}X|\\d{10}|\\d{13})$", message = "Invalid ISBN format")
    private String isbn;

    @NotNull
    @Size(min = 3, max = 100, message = "Title must be a string with length from 4 to 100 characters")
    private String title;

    @NotNull
    @Size(min = 3, max = 100, message = "Genre must be a string with length from 4 to 100 characters")
    private String genre;

    @NotNull
    @Size(min = 3, max = 100, message = "Description must be a string with length from 4 to 100 characters")
    private String description;

    @NotNull
    @Size(min = 3, max = 100, message = "Author must be a string with length from 4 to 100 characters")
    private String author;
}
