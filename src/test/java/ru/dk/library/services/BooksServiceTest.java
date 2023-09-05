package ru.dk.library.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.dk.library.models.Person;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class BooksServiceTest {
    @Autowired
    BooksService service;

    @Test
    void getBookOwner() {
        Person person = service.getBookOwner(1).get();
        System.out.println(person);
    }

    @Test
    void release() {
        service.release(1);
    }

    @Test
    void assign() {
        Person person = new Person();
        person.setId(2);
        service.assign(1, person);
    }
}