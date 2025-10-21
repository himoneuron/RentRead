package com.crio.rent_read.service;

import java.util.List;
import java.util.Optional;
import com.crio.rent_read.dto.BookCreateRequest;

import com.crio.rent_read.model.BookEntity;

public interface BookService {
     // These methods come from your BookRepositoryService
     BookEntity createBook(BookCreateRequest bookDto);
     //BookCreateRequest findBookById(Long bookId);
     
     List<BookCreateRequest> findAllBooks();
 
     BookEntity updateBook(Long bookId, BookCreateRequest request);
 
     void deleteBook(Long bookId);
     Optional<BookEntity> findBookById(Long id); // Method we'll need soon
     void deleteBookById(Long bookId);     
}
