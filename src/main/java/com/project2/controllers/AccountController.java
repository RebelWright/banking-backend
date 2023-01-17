package com.project2.controllers;

import com.project2.daos.AccountDAO;
import com.project2.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value="/accounts")
public class AccountController {
    private final AccountDAO accountDAO;

    @Autowired
    public AccountController(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @GetMapping(value="/{userId}")
    public ResponseEntity<List<Account>> getAllAccountsByUserId(@PathVariable int userId)
    {
        Optional<List<Account>> accountsOptional = accountDAO.findByUser(userId);

        if (accountsOptional.isPresent())
        {
            List<Account> extractedAccounts = accountsOptional.get();

            return ResponseEntity.ok(extractedAccounts); //200
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value="/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable int accountId){
        Optional<Account> accountOptional = accountDAO.findByAccountId(accountId);
        if(accountOptional.isPresent()){
            Account extractedAccount = accountOptional.get();
            return ResponseEntity.ok(extractedAccount);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account){
        Account newAccount = accountDAO.save(account);
        if(newAccount == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.accepted().body(newAccount);
    }
}
