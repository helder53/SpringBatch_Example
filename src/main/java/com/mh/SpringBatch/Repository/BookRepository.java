package com.mh.SpringBatch.Repository;


import com.mh.SpringBatch.Entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
