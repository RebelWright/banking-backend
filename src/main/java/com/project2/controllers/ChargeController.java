package com.project2.controllers;

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

    @Autowired
    public ChargeController(ChargeDAO chargeDAO) {
        this.chargeDAO = chargeDAO;
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

    @PostMapping(value="/create")
    public ResponseEntity<Charge> createNewCharge(@RequestBody Charge charge){
        chargeDAO.save(charge);
        if (charge == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(charge);

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
