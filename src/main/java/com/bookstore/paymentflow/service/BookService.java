package com.bookstore.paymentflow.service;

import com.bookstore.paymentflow.model.Book;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class BookService {

  private final Map<Integer, Book> books = new LinkedHashMap<>(Map.of(
      1, new Book(1, "The Adventures of Tom Sawyer", "Mark Twen", 9.99),
      2, new Book(2, "1984", "George Orwell", 14.99),
      3, new Book(3, "To Kill a Mockingbird", "Harper Lee", 12.99)
  ));

  public Collection<Book> getBooks() {
    return books.values();
  }

  public Book getBook(int id) {
    return books.get(id);
  }
}
