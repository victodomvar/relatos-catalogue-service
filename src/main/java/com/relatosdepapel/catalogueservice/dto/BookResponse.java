package com.relatosdepapel.catalogueservice.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BookResponse(
        Long id,
        String title,
        String author,
        LocalDate publicationDate,
        String category,
        String isbn,
        Integer rating,
        Boolean visible,
        Integer stock,
        BigDecimal price
) {
}
