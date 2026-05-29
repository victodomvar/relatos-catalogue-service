package com.relatosdepapel.catalogueservice.service;

import com.relatosdepapel.catalogueservice.dto.BookPatchRequest;
import com.relatosdepapel.catalogueservice.dto.BookRequest;
import com.relatosdepapel.catalogueservice.dto.BookResponse;
import com.relatosdepapel.catalogueservice.entity.Book;
import com.relatosdepapel.catalogueservice.exception.BookNotFoundException;
import com.relatosdepapel.catalogueservice.repository.BookRepository;
import com.relatosdepapel.catalogueservice.repository.BookSpecifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional(readOnly = true)
    public List<BookResponse> findBooks(
            String title,
            String author,
            LocalDate publicationDate,
            String category,
            String isbn,
            Integer rating,
            Boolean visible
    ) {
        return bookRepository.findAll(BookSpecifications.withFilters(
                        title,
                        author,
                        publicationDate,
                        category,
                        isbn,
                        rating,
                        visible
                ))
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public BookResponse getBook(Long id) {
        return toResponse(findById(id));
    }

    public BookResponse createBook(BookRequest request) {
        Book book = new Book();
        applyRequest(book, request);
        return toResponse(bookRepository.save(book));
    }

    public BookResponse updateBook(Long id, BookRequest request) {
        Book book = findById(id);
        applyRequest(book, request);
        return toResponse(book);
    }

    public BookResponse patchBook(Long id, BookPatchRequest request) {
        Book book = findById(id);

        if (request.title() != null) {
            book.setTitle(request.title());
        }
        if (request.author() != null) {
            book.setAuthor(request.author());
        }
        if (request.publicationDate() != null) {
            book.setPublicationDate(request.publicationDate());
        }
        if (request.category() != null) {
            book.setCategory(request.category());
        }
        if (request.isbn() != null) {
            book.setIsbn(request.isbn());
        }
        if (request.rating() != null) {
            book.setRating(request.rating());
        }
        if (request.visible() != null) {
            book.setVisible(request.visible());
        }
        if (request.stock() != null) {
            book.setStock(request.stock());
        }
        if (request.price() != null) {
            book.setPrice(request.price());
        }

        return toResponse(book);
    }

    public void deleteBook(Long id) {
        Book book = findById(id);
        bookRepository.delete(book);
    }

    private Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    private void applyRequest(Book book, BookRequest request) {
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setPublicationDate(request.publicationDate());
        book.setCategory(request.category());
        book.setIsbn(request.isbn());
        book.setRating(request.rating());
        book.setVisible(request.visible());
        book.setStock(request.stock());
        book.setPrice(request.price());
    }

    private BookResponse toResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublicationDate(),
                book.getCategory(),
                book.getIsbn(),
                book.getRating(),
                book.getVisible(),
                book.getStock(),
                book.getPrice()
        );
    }
}
