package com.phdev.library.services;

import com.phdev.library.dto.BookAvailableQuantityRequest;
import com.phdev.library.dto.BookRequest;
import com.phdev.library.dto.BookResponse;
import com.phdev.library.dto.BookUpdate;
import com.phdev.library.entities.Author;
import com.phdev.library.entities.Book;
import com.phdev.library.entities.Category;
import com.phdev.library.repositories.AuthorRepository;
import com.phdev.library.repositories.BookRepository;
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
    public Page<BookResponse> findAll(Pageable pageable) {
        Page<Book> books = bookRepository.findAll(pageable);
        return books.map(book -> new BookResponse(book));
    }

    @Transactional(readOnly = true)
    public BookResponse findById(Long id) {
        Book book = findByBookId(id);
        return new BookResponse(book);
    }

    @Transactional
    public BookResponse insert(BookRequest request) {
        Category category = findByCategoryId(request.categoryId());
        Author author = findByAuthorId(request.authorId());

        Book book = new Book(null, request.title(), request.isbn(), request.publicationYear(), request.availableQuantity(),
                category, author);

        bookRepository.save(book);

        return new BookResponse(book);
    }

    @Transactional
    public BookResponse update(Long id, BookUpdate update) {
        Book book = findByBookId(id);
        Category category = findByCategoryId(update.categoryId());
        Author author = findByAuthorId(update.authorId());
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
            throw new RuntimeException();
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

    private Category findByCategoryId(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        return category;
    }

    private Author findByAuthorId(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        return author;
    }
}
