package com.relatosdepapel.catalogueservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BookPatchRequest(
        String title,
        String author,
        LocalDate publicationDate,
        String category,
        String isbn,

        @Min(value = 1, message = "rating must be between 1 and 5")
        @Max(value = 5, message = "rating must be between 1 and 5")
        Integer rating,

        Boolean visible,

        @Min(value = 0, message = "stock must be greater than or equal to 0")
        Integer stock,

        @DecimalMin(value = "0.00", message = "price must be greater than or equal to 0")
        BigDecimal price
) {
}
