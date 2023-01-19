package com.project2.controllers;

import com.project2.daos.AccountDAO;
import com.project2.daos.ChargeDAO;
import com.project2.models.Account;
import com.project2.models.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value="charges")
public class ChargeController {
    private final ChargeDAO chargeDAO;
    private final AccountDAO accountDAO;


    @Autowired
    public ChargeController(ChargeDAO chargeDAO, AccountDAO accountDAO) {
        this.chargeDAO = chargeDAO;
        this.accountDAO = accountDAO;
    }

    @GetMapping(value="/")
    public ResponseEntity<List<Charge>> getAllCharges()
    {
        return ResponseEntity.ok(chargeDAO.findAll());
    }
    @GetMapping(value="/{chargeId}")
    public ResponseEntity<Charge> getChargeByChargeId(@PathVariable int chargeId){
        Optional<Charge> chargeOptional = chargeDAO.findByChargeId(chargeId);
        if (chargeOptional.isPresent()){
            Charge extractedCharges = chargeOptional.get();
            return ResponseEntity.ok(extractedCharges);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value="/account/{accountId}")
    public ResponseEntity<List<Charge>> getAllChargesByAccountId(@PathVariable int accountId){
        Optional<List<Charge>> chargeOptional = chargeDAO.findByAccount(accountId);
        if (chargeOptional.isPresent()){
            List<Charge> extractedCharges = chargeOptional.get();
            return ResponseEntity.ok(extractedCharges);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(value="/create/{accountId}")
    public ResponseEntity<Charge> createNewCharge(@RequestBody Charge charge, @PathVariable int accountId){
        Optional<Account> accountOptional = accountDAO.findByAccountId(accountId);
        charge.setAccount(accountOptional.get());
        Charge newCharge = chargeDAO.save(charge);
        if (newCharge == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.accepted().body(newCharge);

    }

    @PutMapping(value="/update/{chargeId}")
    public ResponseEntity<Charge> updateChargeById(@RequestBody Charge charge, @PathVariable int chargeId){
        Optional<Charge> editedCharge = chargeDAO.findByChargeId(chargeId);
        Charge extractedCharge = editedCharge.get();
        System.out.println(extractedCharge);

        Account a = extractedCharge.getAccount();
        System.out.println(a);

        extractedCharge = charge;
        extractedCharge.setAccount(a);

        if(charge.getChargeId() < 1){
            return ResponseEntity.badRequest().build(); //400
        } else {
            charge.setAccount(extractedCharge.getAccount());
            return ResponseEntity.accepted().body(chargeDAO.save(extractedCharge)); //202
        }
    }

    @DeleteMapping(value="/delete/{chargeId}")
    public ResponseEntity<String> deleteChargeById(@PathVariable int chargeId){
        if (chargeDAO.findByChargeId(chargeId).isPresent()){
            chargeDAO.deleteById(chargeId);
            return ResponseEntity.ok("Successful Deletion"); //200
        }
        return ResponseEntity.badRequest().build(); //400
    }

}
