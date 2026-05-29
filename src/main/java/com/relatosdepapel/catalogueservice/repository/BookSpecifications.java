package com.relatosdepapel.catalogueservice.repository;

import com.relatosdepapel.catalogueservice.entity.Book;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public final class BookSpecifications {

    private BookSpecifications() {
    }

    public static Specification<Book> withFilters(
            String title,
            String author,
            LocalDate publicationDate,
            String category,
            String isbn,
            Integer rating,
            Boolean visible
    ) {
        return Specification.where(containsIgnoreCase("title", title))
                .and(containsIgnoreCase("author", author))
                .and(equalsValue("publicationDate", publicationDate))
                .and(containsIgnoreCase("category", category))
                .and(equalsValue("isbn", isbn))
                .and(equalsValue("rating", rating))
                .and(equalsValue("visible", visible));
    }

    private static Specification<Book> containsIgnoreCase(String field, String value) {
        return (root, query, builder) -> {
            if (value == null || value.isBlank()) {
                return builder.conjunction();
            }
            return builder.like(builder.lower(root.get(field)), "%" + value.toLowerCase() + "%");
        };
    }

    private static <T> Specification<Book> equalsValue(String field, T value) {
        return (root, query, builder) -> value == null
                ? builder.conjunction()
                : builder.equal(root.get(field), value);
    }
}
