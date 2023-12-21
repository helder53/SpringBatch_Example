package com.mh.SpringBatch.Controller;

import com.mh.SpringBatch.Entity.BookEntity;
import com.mh.SpringBatch.Repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/book")
public class BookController {

    @Autowired
    private final BookRepository bookRepository;

    @GetMapping(value = "/findall")
    public List<BookEntity> findAll(){
        return bookRepository.findAll();
    }
}
