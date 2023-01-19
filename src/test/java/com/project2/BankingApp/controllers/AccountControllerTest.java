package com.project2.BankingApp.controllers;
import com.project2.controllers.AccountController;
import com.project2.daos.AccountDAO;
import com.project2.daos.UserDAO;
import com.project2.models.Account;
import com.project2.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AccountControllerTest {

    @Mock
    private AccountDAO accountDAO;

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private AccountController accountController;

    User u = new User(1, "test@gmail.com", "password", "John", "Doe", "1234567", "Abc 123 St.");
    Account a = new Account(1, u, "testing", "testing", 1000.0);
    Account a2 = new Account(2, u, "testing2", "testing2", 100.0);
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAccountByIdSuccess() {

        when(accountDAO.findByAccountId(1)).thenReturn(Optional.of(a));
        ResponseEntity<Account> response = accountController.getAccountById(1);
        assertEquals(a, response.getBody());
    }

    @Test
    void testGetAllAccountsSuccess() {
        List<Account> expectedAccounts = Arrays.asList(a, a2);
        when(accountDAO.findAll()).thenReturn(expectedAccounts);
        ResponseEntity<List<Account>> actualResponse = accountController.getAllAccounts();
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        System.out.println(actualResponse);
    }

    @Test
    void testGetAllAccountsByUserIdSuccess() {
        when(userDAO.findByUserId(1)).thenReturn(Optional.of(u));
        when(accountDAO.findByUser(u)).thenReturn(Optional.of(Arrays.asList(a, a2)));
        ResponseEntity<List<Account>> response = accountController.getAllAccountsByUserId(1);
        assertEquals(Arrays.asList(a, a2), response.getBody());
    }

    @Test
    public void testCreateAccountSuccess() {
        when(userDAO.findByUserId(1)).thenReturn(Optional.of(u));
        when(accountDAO.save(a)).thenReturn(a);
        ResponseEntity<Account> response = accountController.createAccount(a, 1);
        assertEquals(a, response.getBody());
    }
}
