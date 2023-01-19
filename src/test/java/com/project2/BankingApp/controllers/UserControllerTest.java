package com.project2.BankingApp.controllers;


import com.project2.controllers.UserController;
import com.project2.daos.UserDAO;
import com.project2.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {
    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserController userController;
    private User u = new User(1, "test@gmail.com", "password", "Joe", "Bob", "1234567", "123 ABC St.");

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getUserByIdTestSuccess() {
        User expectedUser = u;
        Optional<User> expectedOptional = Optional.of(expectedUser);
        when(userDAO.findByUserId(1)).thenReturn(expectedOptional);
        User actualUser = userController.getUserById(1).getBody();
        assertEquals(expectedUser, actualUser);
        System.out.println(expectedUser);
        System.out.println(actualUser);
    }
    //This test fails. Do we keep?
    @Test
    public void getUserByIdTestFail() {
        User expectedUser = u;
        Optional<User> expectedOptional = Optional.of(expectedUser);
        when(userDAO.findByUserId(1)).thenReturn(expectedOptional);
        User actualUser = userController.getUserById(0).getBody();
        assertNotEquals(expectedUser, actualUser);
        System.out.println(expectedUser);
        System.out.println(actualUser);

    }
    @Test
    public void updateUserPassword() {
        User expectedUser = u;
        String newPassword = "drowssap";
        Optional<User> expectedOptional = Optional.of(expectedUser);
        when(userDAO.findByUserId(1)).thenReturn(expectedOptional);
        when(userDAO.save(expectedUser)).thenReturn(expectedUser);
        ResponseEntity actualResponse = userController.updatePassword(expectedUser,1);
        assertEquals(HttpStatus.ACCEPTED, actualResponse.getStatusCode());
        assertEquals(expectedUser, actualResponse.getBody());
        System.out.println(expectedUser);
        System.out.println(actualResponse.getBody());
    }
    @Test
    public void updateAddressTest() {
        User expectedUser = u;
        String newAddress = "456 DEF St.";
        expectedUser.setAddress(newAddress);
        Optional<User> expectedOptional = Optional.of(expectedUser);
        when(userDAO.findByUserId(1)).thenReturn(expectedOptional);
        when(userDAO.save(expectedUser)).thenReturn(expectedUser);
        ResponseEntity<User> actualResponse = userController.updateAddress(expectedUser, 1);
        assertEquals(HttpStatus.ACCEPTED, actualResponse.getStatusCode());
        assertEquals(expectedUser, actualResponse.getBody());
    }
    //not working right
    @Test
    public void updateAddressTestFail() {
        User expectedUser = u;
        String newAddress = "123 ABC St.";
        expectedUser.setAddress(newAddress);
        Optional<User> expectedOptional = Optional.of(expectedUser);
        when(userDAO.findByUserId(1)).thenReturn(expectedOptional);
        when(userDAO.save(expectedUser)).thenReturn(expectedUser);
        ResponseEntity<User> actualResponse = userController.updateAddress(expectedUser, 1);
        assertEquals(HttpStatus.ACCEPTED, actualResponse.getStatusCode());
        assertEquals(expectedUser, actualResponse.getBody());
    }
    @Test
    public void deleteUserByIdTestSuccess() {
        when(userDAO.findByUserId(1)).thenReturn(Optional.of(new User()));
        ResponseEntity<String> actualResponse = userController.deleteUserById(1);
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals("Successful Deletion", actualResponse.getBody());
        verify(userDAO, times(1)).deleteById(1);
    }
    @Test
    public void registerUserTestSuccess() {
        User expectedUser = new User(1, "test@gmail.com", "password", "Joe", "Bob", "1234567", "123 ABC St.");
        when(userDAO.save(expectedUser)).thenReturn(expectedUser);
        ResponseEntity<User> actualResponse = userController.registerUser(expectedUser);
        assertEquals(HttpStatus.ACCEPTED, actualResponse.getStatusCode());
        assertEquals(expectedUser, actualResponse.getBody());
    }
    @Test
    public void loginUserTestSuccess() {
        User expectedUser = new User(1, "test@gmail.com", "password", "Joe", "Bob", "1234567", "123 ABC St.");
        Optional<User> expectedOptional = Optional.of(expectedUser);
        when(userDAO.findByEmailAndPassword("test@gmail.com", "password")).thenReturn(expectedOptional);
        User actualUser = userController.loginUser(expectedUser).getBody();
        assertEquals(expectedUser, actualUser);
    }
    //this test fails. Do we keep?
    @Test
    public void loginUserTestFail() {
        User expectedUser = new User(1, "test@gmail.com", "password", "Joe", "Bob", "1234567", "123 ABC St.");
        Optional<User> expectedOptional = Optional.of(expectedUser);
        when(userDAO.findByEmailAndPassword("tset@gmail.com", "drowssap")).thenReturn(expectedOptional);
        User actualUser = userController.loginUser(expectedUser).getBody();
        assertNotEquals(expectedUser, actualUser);
    }




    }
