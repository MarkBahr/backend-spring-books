package com.secure.books.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.secure.books.models.Book;
import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{

        List<Book> findByBookId(Integer bookId);

        List<Book> findBySellerUser(String sellerUser);
}
