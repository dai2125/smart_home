package com.home.indirection.thirdAnalysis;

import com.home.indirection.fourthAnalysis.fix.Book;
import com.home.indirection.fourthAnalysis.fix.BookRepository;
import com.home.indirection.fourthAnalysis.fix.BookRepositoryImpl;
import com.home.indirection.fourthAnalysis.fix.BookService;
import com.home.indirection.thirdAnalysis.fix.MessageMediator;
import com.home.indirection.thirdAnalysis.fix.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class TestIndirection {

    @Test
    public void testMessageService() {

        User sender = Mockito.mock(User.class);
        User receiver = Mockito.mock(User.class);
        MessageMediator messageMediator = new MessageMediator();

        when(sender.getName()).thenReturn("Alice");
        when(receiver.getName()).thenReturn("Bob");

        String message = "Hello, Bob!";
        messageMediator.sendMessage(message, sender, receiver);

        verify(sender).getName();
        verify(receiver).getName();
        verify(receiver).receiveMessage(message);
        verifyNoMoreInteractions(sender, receiver);
    }
}
