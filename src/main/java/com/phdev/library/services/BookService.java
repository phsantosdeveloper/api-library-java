package com.phdev.library.services;

import com.phdev.library.dto.BookAvailableQuantityRequest;
import com.phdev.library.dto.BookFilter;
import com.phdev.library.dto.BookRequest;
import com.phdev.library.dto.BookResponse;
import com.phdev.library.dto.BookUpdate;
import com.phdev.library.entities.Author;
import com.phdev.library.entities.Book;
import com.phdev.library.entities.Category;
import com.phdev.library.exceptions.InvalidNumberBooksWithdrawException;
import com.phdev.library.repositories.AuthorRepository;
import com.phdev.library.repositories.BookRepository;
import com.phdev.library.specifications.BookSpecifications;
import com.phdev.library.repositories.CategoryRepository;
import com.phdev.library.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, CategoryRepository categoryRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional(readOnly = true)
    public Page<BookResponse> search(BookFilter filter, Pageable pageable) {
        return bookRepository.findAll(BookSpecifications.withFilters(filter), pageable)
                .map(BookResponse::new);
    }

    @Transactional(readOnly = true)
    public BookResponse findById(Long id) {
        Book book = findByBookId(id);
        return new BookResponse(book);
    }

    @Transactional(readOnly = true)
    public Page<BookResponse> findAvailableBooks(BookFilter filter, Pageable pageable) {
        return bookRepository.findAll(BookSpecifications.withFilters(filter).and(BookSpecifications.available()), pageable)
                .map(BookResponse::new);
    }

    @Transactional
    public BookResponse insert(BookRequest request) {
        Category category = getCategoryOrThrow(request.categoryId());
        Author author = getAuthorOrThrow(request.authorId());

        Book book = new Book(null, request.title(), request.isbn(), request.publicationYear(),
                request.availableQuantity(), category, author);

        bookRepository.save(book);
        return new BookResponse(book);
    }

    @Transactional
    public BookResponse update(Long id, BookUpdate update) {
        Book book = findByBookId(id);
        Category category = getCategoryOrThrow(update.categoryId());
        Author author = getAuthorOrThrow(update.authorId());
        updateBookData(book, update, category, author);

        bookRepository.save(book);
        return new BookResponse(book);
    }

    @Transactional
    public BookResponse addCopiesToStock(Long id, BookAvailableQuantityRequest request) {
        Book book = findByBookId(id);
        book.setAvailableQuantity(book.getAvailableQuantity() + request.quantity());

        bookRepository.save(book);
        return new BookResponse(book);
    }

    @Transactional
    public BookResponse removeCopiesFromStock(Long id, BookAvailableQuantityRequest request) {
        Book book = findByBookId(id);

        if(request.quantity() > book.getAvailableQuantity()) {
            throw new InvalidNumberBooksWithdrawException("The number of books to be " +
                    "checked out must not exceed the number available");
        }

        book.setAvailableQuantity(book.getAvailableQuantity() - request.quantity());

        bookRepository.save(book);
        return new BookResponse(book);
    }

    @Transactional
    public void delete(Long id) {
        Book book = findByBookId(id);
        bookRepository.delete(book);
    }

    private void updateBookData(Book book, BookUpdate update, Category category, Author author) {
        book.setTitle(update.title());
        book.setIsbn(update.isbn());
        book.setPublicationYear(update.publicationYear());
        book.setCategory(category);
        book.setAuthor(author);
    }

    private Book findByBookId(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        return book;
    }

    private Category getCategoryOrThrow(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        return category;
    }

    private Author getAuthorOrThrow(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        return author;
    }
}
