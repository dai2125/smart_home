package com.home.indirection.fourthAnalysis;

import com.home.indirection.fourthAnalysis.fix.Book;
import com.home.indirection.fourthAnalysis.fix.BookRepository;
import com.home.indirection.fourthAnalysis.fix.BookRepositoryImpl;
import com.home.indirection.fourthAnalysis.fix.BookService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import org.mockito.*;

import java.lang.reflect.Field;

public class TestIndirection {

    @Test
    void testBookService() {
        Field[] fields = BookService.class.getDeclaredFields();

        for(Field field : fields) {
            Class<?> fieldType = field.getType();
            if(fieldType.isInterface()) {
                assertTrue(fieldType.equals(BookRepository.class), "BookService should depend on BookRepository");
            }
        }

        BookRepository repository = Mockito.mock(BookRepositoryImpl.class);
        BookService service = new BookService(repository);

        Book book = new Book(1, "Zzz");
//        when(repository.findBookById(1)).thenReturn(book);

        service.performBusinessOperation();

//        verify(repository).findBookById(1);
//        verify(repository).saveBook(book);

        verifyNoMoreInteractions(repository);
    }
}
