package com.crio.rent_read.service.serviceImpl;

import java.util.List;
import java.util.Optional;
import com.crio.rent_read.dto.BookCreateRequest;
import com.crio.rent_read.exception.resourceNotFoundException;

import com.crio.rent_read.mapper.EntityDtoMapper;
import com.crio.rent_read.model.BookEntity;
import com.crio.rent_read.repository.BookRepository;
import com.crio.rent_read.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EntityDtoMapper mapper;

    @Override
    public BookEntity createBook(BookCreateRequest bookDto) {
        // Call the new, specific method instead of the generic .map()
        BookEntity book = mapper.toBookEntity(bookDto);
        return bookRepository.save(book);
    }

  

    @Override
    public List<BookCreateRequest> findAllBooks() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<BookEntity> findBookById(Long id) {
        return bookRepository.findById(id);
    }
    @Override
    public void deleteBookById(Long bookId) {
        // Check if the book exists first. If not, this would be a 404.
        if (!bookRepository.existsById(bookId)) {
            throw new resourceNotFoundException("Book not found with ID: " + bookId);
        }
        bookRepository.deleteById(bookId);
    }



    @Override
    public void deleteBook(Long bookId) {
        // TODO Auto-generated method stub
        
    }



    @Override
    public BookEntity updateBook(Long bookId, BookCreateRequest request) {
        // Find the existing book by its ID, now returns a BookEntity
        BookEntity existingBook = bookRepository.findById(bookId)
            .orElseThrow(() -> new resourceNotFoundException("Book not found with ID: " + bookId));

        // Update the fields from the request DTO
        existingBook.setTitle(request.getTitle());
        existingBook.setAuthor(request.getAuthor());
        existingBook.setGenre(request.getGenre());
        // 3. Convert the String "availabilityStatus" to a boolean for the entity.
        boolean isAvailable = request.getAvailabilityStatus().equalsIgnoreCase("AVAILABLE");
        existingBook.setAvailable(isAvailable);

        // 4. Save the updated entity and return it.
        return bookRepository.save(existingBook);
    }

}
