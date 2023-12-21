package com.mh.SpringBatch.Chunk;

import com.mh.SpringBatch.Entity.BookEntity;
import com.mh.SpringBatch.Repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class BookReader implements ItemReader<BookEntity> {

    private final String url;
    private final RestTemplate restTemplate;

    private int nextBook;
    private List<BookEntity> bookList;

    public BookReader(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    // 외부의 정보를 List<BookEntity>형태로 반환
    private List<BookEntity> fetchBooks(){
        // GET 요청을 보내고 응답을 ResponseEntity로 받습니다.
        ResponseEntity<BookEntity[]> response = restTemplate.getForEntity(this.url, BookEntity[].class);
        //ResponseEntity에서 Book 정보를 배열로 얻어 옵니다.
        BookEntity[] books = response.getBody();

        if (books != null){
            return Arrays.asList(books);
        }
        return null;
    }

    // read 메소드 재정의
    @Override
    public BookEntity read() throws Exception{
        if (this.bookList == null){ bookList = fetchBooks(); }
        BookEntity bookEntity = null;

        if (nextBook < bookList.size()){
            bookEntity = bookList.get(nextBook);
            nextBook++;
        } else {
            nextBook = 0;
            bookList = null;
        }
        return bookEntity;
    }
}
