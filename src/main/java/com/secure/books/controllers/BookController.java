package com.secure.books.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.secure.books.services.BookService;

// import jakarta.validation.Valid;

import com.secure.books.models.Book;

@RequestMapping("/api/books")
@RestController
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    /**
     * USER STORY 1: Get a list of all books for a user
     * @param bookId
     * @param book
     * @return
     */
    @GetMapping
    public List<Book> getUsersBooks(@AuthenticationPrincipal UserDetails userDetails) {
        String sellerUser = userDetails.getUsername();
        return bookService.getBooks(sellerUser);
    }
    
    /**
     * USER STORY 2: Create / Save a new book to the database.
     * @param book
     * @param result
     * @return
     */
    @PostMapping
    public ResponseEntity<Book> saveBook(@RequestBody Book book, @AuthenticationPrincipal UserDetails userDetails) {
        String sellerUser = userDetails.getUsername();
        Book newBook = bookService.insertBook(sellerUser, book);
        return ResponseEntity.status(HttpStatus.OK).body(newBook);
    }

    
    
    /**
     * Update a book by using PUT /books/{id}
     * @param bookId
     * @param book
     * @return If the update is successful, response should contain new book.
     */
    @PutMapping("/{bookId}")
    public ResponseEntity<Book> putBook(@PathVariable("bookId") Integer bookId, @RequestBody Book book, 
                                        @AuthenticationPrincipal UserDetails userDetails) {
        String sellerUser = userDetails.getUsername();
        Book newBook = bookService.updateBook(sellerUser, bookId, book);
        return ResponseEntity.status(HttpStatus.OK).body(newBook);
    }
    
    
    /**
     * Delete a book using DELETE /books/{id}
     * @param bookId
     * @return
     */
    @DeleteMapping("/{bookId}")
    public ResponseEntity<Long> deleteBookById(@PathVariable("bookId") Integer bookId, 
                                                @AuthenticationPrincipal UserDetails userDetails) {
        String sellerUser = userDetails.getUsername();
        Long myLong = bookService.deleteBook(sellerUser, bookId);
        return ResponseEntity.status(HttpStatus.OK).body(myLong);
    }


    /**
     * Get book by id
     * @param bookId
     * @return
     */
    @GetMapping("/{bookId}")
    public Book fetchBookById(@PathVariable Integer bookId, @AuthenticationPrincipal UserDetails userDetails) {
        
        String sellerUser = userDetails.getUsername();
        Book book = bookService.getBookById(sellerUser, bookId);
        return book;
    }
}