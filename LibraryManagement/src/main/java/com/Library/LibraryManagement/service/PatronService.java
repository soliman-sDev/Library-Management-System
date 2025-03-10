package com.Library.LibraryManagement.service;

import com.Library.LibraryManagement.entity.Patron;
import com.Library.LibraryManagement.repository.PatronRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatronService {

    private final PatronRepository patronRepository;

    public PatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    public Optional<Patron> getPatronById(Long id) {
        return patronRepository.findById(id);
    }

    public Patron addPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    public Patron updatePatron(Long id, Patron patronInfo) {
        return patronRepository.findById(id).map(patron -> {
            patron.setName(patronInfo.getName());
            patron.setEmail(patronInfo.getEmail());
            patron.setPhone(patronInfo.getPhone());
            patron.setAddress(patronInfo.getAddress());
            return patronRepository.save(patron);
        }).orElseThrow(() -> new RuntimeException("Patron not found") );
    }

    public void deletePatron(Long id) {
        patronRepository.deleteById(id);
    }

}
//
