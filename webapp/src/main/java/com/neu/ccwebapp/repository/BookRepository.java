package com.neu.ccwebapp.repository;

import com.neu.ccwebapp.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, String> {
}
