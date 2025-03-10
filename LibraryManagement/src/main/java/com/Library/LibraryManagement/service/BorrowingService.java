package com.Library.LibraryManagement.service;

import com.Library.LibraryManagement.entity.Book;
import com.Library.LibraryManagement.entity.BorrowingRecord;
import com.Library.LibraryManagement.entity.Patron;
import com.Library.LibraryManagement.repository.BookRepository;
import com.Library.LibraryManagement.repository.BorrowingRecordRepository;
import com.Library.LibraryManagement.repository.PatronRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BorrowingService {
    private final BorrowingRecordRepository borrowingRecordRepository;
    private final PatronRepository patronRepository;
    private final BookRepository bookRepository;

    public BorrowingService(BorrowingRecordRepository borrowingRecordRepository, PatronRepository patronRepository, BookRepository bookRepository) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.patronRepository = patronRepository;
        this.bookRepository = bookRepository;
    }
    @Transactional
    public BorrowingRecord borrowBook(Long bookId, Long patronId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found"));
        Patron patron = patronRepository.findById(patronId).orElseThrow(() -> new IllegalArgumentException("Patron not found"));

        Optional<BorrowingRecord> existingRecord = borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId);
        if (existingRecord.isPresent()) {
            throw new IllegalStateException("Book already borrowed and not returned");
        }
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDate.now());
        return borrowingRecordRepository.save(borrowingRecord);
    }

    @Transactional
    public BorrowingRecord returnBook(Long bookId, Long patronId) {
        BorrowingRecord record = borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(bookId,patronId).
                orElseThrow(() -> new IllegalArgumentException("No borrowing record found for this book and patron"));
        record.setReturnDate(LocalDate.now());
        return borrowingRecordRepository.save(record);
    }
}
