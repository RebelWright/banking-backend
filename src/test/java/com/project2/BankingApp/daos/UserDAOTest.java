package com.project2.BankingApp.daos;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.project2.daos.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.project2.models.User;

class UserDAOTest {
    @Mock
    private UserDAO userDAO;

    private User u = new User(1,"test@gmail.com","password","John","Doe","1234567","Abc 123 St.");


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        }

    @Test
    void testFindByUserId() {
        when(userDAO.findByUserId(1)).thenReturn(Optional.of(u));

        Optional<User> result = userDAO.findByUserId(1);
        assertEquals(u, result.get());
    }

    @Test
    void testFindByEmailAndPassword() {
        when(userDAO.findByEmailAndPassword("johndoe@email.com","password")).thenReturn(Optional.<User>of(u));

        Optional<User> result = userDAO.findByEmailAndPassword("johndoe@email.com","password");
        assertEquals(u, result.get());
    }
}
