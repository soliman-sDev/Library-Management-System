package com.Library.LibraryManagement.service;

import com.Library.LibraryManagement.entity.Book;
import com.Library.LibraryManagement.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id){
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found")) ;
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book bookInfo) {
        return bookRepository.findById(id).map(book -> {
            book.setAuthor(bookInfo.getAuthor());
            book.setTitle(bookInfo.getTitle());
            book.setIsbn(bookInfo.getIsbn());
            book.setPublishYear(bookInfo.getPublishYear());
            book.setPublisher(bookInfo.getPublisher());
            return bookRepository.save(book);
        }).orElseThrow(() -> new RuntimeException("Book not found"));
    }


    public void delBookById(Long id){
        Book book = getBookById(id);
        bookRepository.delete(book);
    }



}
