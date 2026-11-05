package com.phdev.library.controller;

import com.phdev.library.dto.BookAvailableQuantityRequest;
import com.phdev.library.dto.BookRequest;
import com.phdev.library.dto.BookResponse;
import com.phdev.library.dto.BookUpdate;
import com.phdev.library.services.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<Page<BookResponse>> findAll(Pageable pageable) {
        Page<BookResponse> bookResponses = bookService.findAll(pageable);
        return ResponseEntity.ok().body(bookResponses);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BookResponse> findById(@PathVariable Long id) {
        BookResponse bookResponse = bookService.findById(id);
        return ResponseEntity.ok().body(bookResponse);
    }

    @GetMapping(params = "title")
    public ResponseEntity<Page<BookResponse>> findByTitle(@RequestParam(name = "title") String title, Pageable pageable) {
        Page<BookResponse> bookResponses = bookService.findByTitle(title, pageable);
        return ResponseEntity.ok().body(bookResponses);
    }

    @GetMapping(params = "authorId")
    public ResponseEntity<Page<BookResponse>> findByAuthorId(@RequestParam(name = "authorId") Long authorId, Pageable pageable) {
        Page<BookResponse> bookResponses = bookService.findByAuthorId(authorId, pageable);
        return ResponseEntity.ok().body(bookResponses);
    }

    @GetMapping(params = "authorName")
    public ResponseEntity<Page<BookResponse>> findByAuthorName(@RequestParam(name = "authorName") String name, Pageable pageable) {
        Page<BookResponse> bookResponses = bookService.findByAuthorName(name, pageable);
        return ResponseEntity.ok().body(bookResponses);
    }

    @GetMapping(params = "categoryId")
    public ResponseEntity<Page<BookResponse>> findByCategoryId(@RequestParam(name = "categoryId") Long categoryId, Pageable pageable) {
        Page<BookResponse> bookResponses = bookService.findByCategoryId(categoryId, pageable);
        return ResponseEntity.ok().body(bookResponses);
    }

    @GetMapping(params = "isbn")
    public ResponseEntity<BookResponse> findByIsbn(@RequestParam(name = "isbn") String isbn) {
        BookResponse bookResponse = bookService.findByIsbn(isbn);
        return ResponseEntity.ok().body(bookResponse);
    }

    @GetMapping(value = "/available")
    public ResponseEntity<Page<BookResponse>> findAvailableBooks(Pageable pageable) {
        Page<BookResponse> bookResponses = bookService.findAvailableBooks(pageable);
        return ResponseEntity.ok().body(bookResponses);
    }

    @PostMapping
    public ResponseEntity<BookResponse> insert(@Valid @RequestBody BookRequest request) {
        BookResponse bookResponse = bookService.insert(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(bookResponse.id()).toUri();
        return ResponseEntity.created(uri).body(bookResponse);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BookResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody BookUpdate update) {
        BookResponse bookResponse = bookService.update(id, update);
        return ResponseEntity.ok().body(bookResponse);
    }

    @PatchMapping(value = "/{id}/stock/add")
    public ResponseEntity<BookResponse> addCopiesToStock(@PathVariable Long id,
                                                         @Valid @RequestBody BookAvailableQuantityRequest request) {
        BookResponse bookResponse = bookService.addCopiesToStock(id, request);
        return ResponseEntity.ok().body(bookResponse);
    }

    @PatchMapping(value = "/{id}/stock/remove")
    public ResponseEntity<BookResponse> removeCopiesFromStock(@PathVariable Long id,
                                                              @Valid @RequestBody BookAvailableQuantityRequest request) {
        BookResponse bookResponse = bookService.removeCopiesFromStock(id, request);
        return ResponseEntity.ok().body(bookResponse);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
