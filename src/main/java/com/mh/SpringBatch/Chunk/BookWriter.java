package com.mh.SpringBatch.Chunk;

import com.mh.SpringBatch.Entity.BookEntity;
import com.mh.SpringBatch.Repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;


public class BookWriter implements ItemWriter<BookEntity> {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public void write(Chunk<? extends BookEntity> chunk) throws Exception {
        bookRepository.saveAll(chunk.getItems());
    }
}
