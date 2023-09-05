package ru.dk.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dk.library.models.Book;
import ru.dk.library.models.Person;
import ru.dk.library.repositories.BooksRepository;
import ru.dk.library.repositories.PeopleRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> index() {
        return booksRepository.findAll();
    }

    public Book show(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.delete(show(id));
    }

    public Optional<Person> getBookOwner(int id) {
        return peopleRepository.findByBook(id);
    }

    @Transactional
    public void release(int id) {
        booksRepository.release(id);
    }

    @Transactional
    public void assign(int id, Person selectedPerson) {
        booksRepository.assign(id, selectedPerson);
        Book book = show(id);
        book.setTakenAt(LocalDateTime.now());
    }

    public List<Book> pageBy(int page, int size, boolean sort) {
        int realSize = size == 0 ? Integer.MAX_VALUE : size;
        PageRequest pageRequest;
        if (sort) {
            pageRequest = PageRequest.of(page, realSize, Sort.by(Sort.Direction.ASC, "year"));
        } else {
            pageRequest = PageRequest.of(page, realSize);
        }
        return booksRepository.findAll(pageRequest).stream().toList();
    }

    public Optional<Book> findByTitleStartingWith(String startsWith) {
        return booksRepository.findByTitleStartingWith(startsWith);
    }
}
