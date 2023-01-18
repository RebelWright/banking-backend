package com.project2.controllers;

import com.project2.daos.AccountDAO;
import com.project2.daos.UserDAO;
import com.project2.models.Account;
import com.project2.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value="accounts")
public class AccountController {
    private final AccountDAO accountDAO;
    private final UserDAO userDAO;

    @Autowired
    public AccountController(AccountDAO accountDAO, UserDAO userDAO) {
        this.accountDAO = accountDAO;
        this.userDAO = userDAO;
    }


    @GetMapping(value = "/")
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountDAO.findAll());
    }

    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<List<Account>> getAllAccountsByUserId(@PathVariable int userId) {
        Optional<User> userOptional =  userDAO.findByUserId(userId);
        Optional<List<Account>> accountsOptional = accountDAO.findByUser(userOptional.get());
        if (accountsOptional.isPresent()) {
            List<Account> extractedAccounts = accountsOptional.get();

            return ResponseEntity.ok(extractedAccounts); //200
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/account/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable int accountId) {
        Optional<Account> accountOptional = accountDAO.findByAccountId(accountId);
        if (accountOptional.isPresent()) {
            Account extractedAccount = accountOptional.get();
            return ResponseEntity.ok(extractedAccount);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/create/{userId}")
    public ResponseEntity<Account> createAccount(@RequestBody Account account, @PathVariable int userId) {
        Optional<User> userOptional =  userDAO.findByUserId(userId);
        account.setUser(userOptional.get());

        Account newAccount = accountDAO.save(account);
        if (newAccount == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.accepted().body(newAccount);
    }


    @PutMapping(value = "/update/{accountId}")
    public ResponseEntity<Account> updateAccountById(@RequestBody Account account, @PathVariable int accountId) {
        return ResponseEntity.badRequest().build(); //400
    }


    @DeleteMapping(value="/delete/{accountId}")
    public ResponseEntity<String> deleteAccountById(@PathVariable int accountId){
        if (accountDAO.findByAccountId(accountId).isPresent()){
            accountDAO.deleteById(accountId);
            return ResponseEntity.ok("Successful Deletion"); //200
        }
        return ResponseEntity.badRequest().build(); //400
    }

}

