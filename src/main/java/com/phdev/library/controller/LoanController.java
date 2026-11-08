package com.phdev.library.controller;

import com.phdev.library.dto.LoanRequest;
import com.phdev.library.dto.LoanResponse;
import com.phdev.library.dto.LoanUpdate;
import com.phdev.library.services.LoanService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public ResponseEntity<Page<LoanResponse>> findAll(Pageable pageable) {
        Page<LoanResponse> loanResponses = loanService.findAll(pageable);
        return ResponseEntity.ok().body(loanResponses);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<LoanResponse> findById(@PathVariable Long id) {
        LoanResponse loanResponse = loanService.findById(id);
        return ResponseEntity.ok().body(loanResponse);
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<Page<LoanResponse>> findByUserId(@PathVariable Long id, Pageable pageable) {
        Page<LoanResponse> loanResponses = loanService.findByUserId(id, pageable);
        return ResponseEntity.ok().body(loanResponses);
    }

    @GetMapping(value = "/book/{id}")
    public ResponseEntity<Page<LoanResponse>> findByBookId(@PathVariable Long id, Pageable pageable) {
        Page<LoanResponse> loanResponses = loanService.findByBookId(id, pageable);
        return ResponseEntity.ok().body(loanResponses);
    }

    @GetMapping(value = "/overdue")
    public ResponseEntity<Page<LoanResponse>> findOverdue(Pageable pageable) {
        Page<LoanResponse> loanResponses = loanService.findOverdue(pageable);
        return ResponseEntity.ok().body(loanResponses);
    }

    @PostMapping
    public ResponseEntity<LoanResponse> insert(@Valid @RequestBody LoanRequest request) {
        LoanResponse loanResponse = loanService.insert(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(loanResponse.id()).toUri();
        return ResponseEntity.created(uri).body(loanResponse);
    }

    @PatchMapping(value = "/{id}/return-expected-date")
    public ResponseEntity<LoanResponse> updateReturnExpectedDate(@PathVariable Long id,
                                               @Valid @RequestBody LoanUpdate update) {
        LoanResponse loanResponse = loanService.updateReturnExpectedDate(id, update);
        return ResponseEntity.ok().body(loanResponse);
    }

    @PatchMapping(value = "/{id}/return")
    public ResponseEntity<LoanResponse> confirmReturn(@PathVariable Long id) {
        LoanResponse loanResponse = loanService.confirmReturn(id);
        return ResponseEntity.ok().body(loanResponse);
    }

    @PatchMapping(value = "/{id}/cancel")
    public ResponseEntity<LoanResponse> cancel(@PathVariable Long id) {
        LoanResponse loanResponse = loanService.cancel(id);
        return ResponseEntity.ok().body(loanResponse);
    }
}
