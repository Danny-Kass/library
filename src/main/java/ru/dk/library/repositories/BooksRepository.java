package ru.dk.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.dk.library.models.Book;
import ru.dk.library.models.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {

    List<Book> findByPerson(Person person);

    @Modifying
    @Query("UPDATE Book b SET b.person=NULL WHERE b.id=?1")
    void release(int id);

    @Modifying
    @Query("UPDATE Book b SET b.person=?2 WHERE b.id=?1")
    void assign(int id, Person person);

    Optional<Book> findByTitleStartingWith(String startsWith);
}
