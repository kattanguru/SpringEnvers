package com.nisum.SpringEnvers;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long>, RevisionRepository<Book, Long, Long> {

    List<Book> findByName(String name);

}