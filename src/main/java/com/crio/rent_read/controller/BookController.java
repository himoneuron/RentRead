package com.crio.rent_read.controller;

import jakarta.validation.Valid;
import com.crio.rent_read.dto.BookCreateRequest;
import com.crio.rent_read.exchange.BookResponseDto;
import com.crio.rent_read.mapper.EntityDtoMapper;
import com.crio.rent_read.model.BookEntity;
import com.crio.rent_read.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private EntityDtoMapper mapper; 

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookResponseDto> createBook(@Valid @RequestBody BookCreateRequest request) {
        BookEntity createdBook = bookService.createBook(request);
        BookResponseDto responseDto = mapper.toBookResponseDto(createdBook);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }


    @DeleteMapping("/{bookId}") 
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBookById(bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
    }

@PutMapping("/{book_id}")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<BookResponseDto> updateBook(@PathVariable("book_id") Long bookId, @Valid @RequestBody BookCreateRequest request) {
    BookEntity updatedBook = bookService.updateBook(bookId, request);
    BookResponseDto responseDto = mapper.toBookResponseDto(updatedBook);
    return new ResponseEntity<>(responseDto, HttpStatus.OK);
}
}