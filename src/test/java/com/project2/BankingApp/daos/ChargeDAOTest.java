package com.project2.BankingApp.daos;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.project2.daos.ChargeDAO;
import com.project2.models.Account;
import com.project2.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.project2.models.Charge;

class ChargeDAOTest {
    @Mock
    private ChargeDAO chargeDAO;

    private User u = new User(1,"test@gmail.com","password","John","Doe","1234567","Abc 123 St.");
    private Account a = new Account(1, u,"testing","testing", 1000.0);
    private Charge charge1 = new Charge(1, a, 100, "charge1", "description1", "1/2/23");
    private Charge charge2 = new Charge(2, a, 50, "charge2", "description2", "1/3/23");


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        }

    @Test
    void testFindByAccount() {
        when(chargeDAO.findByAccount(1)).thenReturn(Optional.of(Arrays.asList(charge1, charge2)));

        Optional<List<Charge>> result = chargeDAO.findByAccount(1);
        assertEquals(2, result.get().size());
        assertEquals(charge1, result.get().get(0));
        assertEquals(charge2, result.get().get(1));
    }

    @Test
    void testFindByChargeId() {
        when(chargeDAO.findByChargeId(1)).thenReturn(Optional.of(charge1));

        Optional<Charge> result = chargeDAO.findByChargeId(1);
        assertEquals(charge1, result.get());

    }
}
