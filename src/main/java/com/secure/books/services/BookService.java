package com.secure.books.services;

import java.util.List;

import com.secure.books.models.Book;


public interface BookService {

    public List<Book> getBooks(String username);
    
    public Book insertBook(String username, Book book);

    public Book updateBook(String username, Integer book_id, Book book);

     public Long deleteBook(String username, Integer bookId);

     public Book getBookById(String username, Integer bookId);
}
