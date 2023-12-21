package com.mh.SpringBatch.Chunk;

import com.mh.SpringBatch.Entity.BookEntity;
import org.springframework.batch.item.ItemProcessor;

public class BookTitleProcessor implements ItemProcessor<BookEntity, BookEntity> {
    @Override
    public BookEntity process(BookEntity item) throws Exception {
        item.setTitle(item.getTitle().toUpperCase());
        return item;
    }
}
