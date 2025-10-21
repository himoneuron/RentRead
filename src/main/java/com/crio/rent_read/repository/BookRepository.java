package com.crio.rent_read.repository;

import java.util.List;
import java.util.Optional;
import com.crio.rent_read.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity ,Long> {
    /**
     * Finds a book by its title. Useful for checking if a book already exists.
     * @param title The title of the book.
     * @return An Optional containing the BookEntity if found.
     */
    Optional<BookEntity> findByTitle(String title);

    /**
     * Finds all books based on their availability status.
     * This is the primary method for the "browse all available books" feature.
     * @param available The availability status (true for available, false for not).
     * @return A list of books matching the status.
     */
    List<BookEntity> findAllByAvailable(boolean available);
}
