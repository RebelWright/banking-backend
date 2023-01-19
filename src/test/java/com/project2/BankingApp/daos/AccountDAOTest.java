package com.project2.BankingApp.daos;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.project2.daos.AccountDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.project2.models.Account;
import com.project2.models.User;

class AccountDAOTest {
    //instantiate a AccountDAO to use
    @Mock
    private AccountDAO accountDAO;
    //create dummy models
    private User u = new User(1,"test@gmail.com","password","John","Doe","1234567","Abc 123 St.");
    private Account a1 = new Account(1, u,"testing","testing", 1000.0);
    private Account a2 = new Account(2, u, "testing2","testing2", 2000.0);

    //let's us use mockito annotations in our tests
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindByUser() {
        when(accountDAO.findByUser(u)).thenReturn(Optional.of(Arrays.asList(a1, a2)));
        Optional<List<Account>> result = accountDAO.findByUser(u);
        assertEquals(2, result.get().size());
        assertEquals(a1, result.get().get(0));
        assertEquals(a2, result.get().get(1));
    }

    @Test
    void testFindByAccountId() {
        when(accountDAO.findByAccountId(1)).thenReturn(Optional.of(a1));

        Optional<Account> result = accountDAO.findByAccountId(1);
        assertEquals(a1, result.get());
        System.out.println(a1);
        System.out.println(result.get());
    }
}
