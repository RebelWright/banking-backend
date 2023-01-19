package com.project2.BankingApp.controllers;


import com.project2.controllers.ChargeController;
import com.project2.daos.AccountDAO;
import com.project2.daos.ChargeDAO;
import com.project2.models.Account;
import com.project2.models.Charge;
import com.project2.models.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ChargeControllerTest {
    @Mock
    private ChargeDAO cDAO;
    @Mock
    private AccountDAO aDAO;

    @InjectMocks
    private ChargeController cc;
    User u = new User(1,"test@gmail.com","password","John","Doe","1234567","Abc 123 St.");
    Account a = new Account(1, u,"testing","testing", 1000.0);
    Charge c1 = new Charge(1, a, 100, "charge1", "description1", "1/2/23");
    Charge c2 = new Charge(2, a, 50, "charge2", "description2", "1/3/23");


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllChargesTestSuccess() {
        List<Charge> expectedCharges = Arrays.asList(c1, c2);
        when(cDAO.findAll()).thenReturn(expectedCharges);
        ResponseEntity<List<Charge>> actualResponse = cc.getAllCharges();
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        System.out.println(actualResponse);
    }
    @Test
    public void getChargeByChargeIdTestSuccess() {
        Charge expectedCharge = c1;
        Optional<Charge> expectedOptional = Optional.of(expectedCharge);
        when(cDAO.findByChargeId(1)).thenReturn(expectedOptional);
        ResponseEntity<Charge> actualResponse = cc.getChargeByChargeId(1);
        //assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedCharge, actualResponse.getBody());
        System.out.println(expectedCharge);
        System.out.println(actualResponse.getBody());
    }
    @Test
    public void createNewChargeTestSuccess() {

        a.setAccountId(1);
        Optional<Account> accountOptional = Optional.of(a);
        when(aDAO.findByAccountId(1)).thenReturn(accountOptional);
        Charge expectedCharge = c1;
        expectedCharge.setAccount(a);
        when(cDAO.save(expectedCharge)).thenReturn(expectedCharge);
        ResponseEntity<Charge> actualResponse = cc.createNewCharge(expectedCharge,1);
        assertEquals(HttpStatus.ACCEPTED, actualResponse.getStatusCode());
        assertEquals(expectedCharge, actualResponse.getBody());
    }


}
