package com.secure.books.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.secure.books.models.Book;
import com.secure.books.repositories.BookRepository;
import com.secure.books.services.BookService;

/**
 * This class handles the business logic for books, such as fetching, creating, & deleting books, etc.
 */
@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    
    /**
     * USER STORY 1: Get a list of all books for a user
     * @param username
     * @return
     */
    public List<Book> getBooks(String username) {
        // Get a list of books for that user
        List<Book> userBooksList = bookRepository.findBySellerUser(username);
        // return the list
        return userBooksList;
    }
    
    /**
     * USER STORY 2: Add a book to the database
     * @param username
     * @param book
     * @return
     */
    public Book insertBook(String username, Book book) {
        Book newBook = new Book();
        newBook.setTitle(book.getTitle());
        newBook.setAuthor(book.getAuthor());
        newBook.setDescription(book.getDescription());
        newBook.setPrice(book.getPrice());
        newBook.setCost(book.getCost());
        newBook.setSellerUser(username);
        
        // The save() method saves the book in database, returns the saved book with its auto generated id.
        Book persistedBook = bookRepository.save(newBook);
        return persistedBook;
    }

    /**
     * USER STORY 3: Update a book by it's id
     * @param username
     * @param book_id
     * @param book
     * @return
     */
    public Book updateBook(String username, Integer book_id, Book book) {
        
        // Retrieve book by id if exists, if not throw exception
        Optional<Book> optBook = bookRepository.findById(book_id);
        Book oldBook = optBook.orElseThrow(() ->
                new RuntimeException("Book not found."));

        // Set updated fields
        oldBook.setTitle(book.getTitle());
        oldBook.setAuthor(book.getAuthor());
        oldBook.setDescription(book.getDescription());
        oldBook.setPrice(book.getPrice());
        oldBook.setCost(book.getCost());

        // Persist updates to database
        Book updatedBook = bookRepository.save(oldBook);
        return updatedBook;
    }

    /**
     * USER STORY 4: Delete a book by Id
     * @param username
     * @param bookId
     * @return Number of rows deleted
     */
    public Long deleteBook(String username, Integer bookId) {
        Long oldCount = bookRepository.count();
        bookRepository.deleteById(bookId);
        return oldCount - bookRepository.count();
    }

    /**
      * USER Story 5: Get book by id
      * @param username
      * @return book
      */
    public Book getBookById(String username, Integer bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Book book = optionalBook.orElseThrow(() ->
                new RuntimeException("Book not found."));
        return book;
    }
}
