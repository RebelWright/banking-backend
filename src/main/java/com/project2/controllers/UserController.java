package com.project2.controllers;

import com.project2.daos.UserDAO;
import com.project2.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value="/users")
public class UserController {
    private final UserDAO uDAO;

    @Autowired
    public UserController(UserDAO uDAO){
        super();
        this.uDAO = uDAO;
    }

    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody User u)
    {
        User newUser = uDAO.save(u);

        if(newUser == null)
        {
            return ResponseEntity.badRequest().build(); //400
        }

        return ResponseEntity.accepted().body(newUser); //202
    }

//    @GetMapping
//    public ResponseEntity<User> getUserById(@PathVariable int userId)
//    {
//        Optional<User> userOptional = uDAO.findByUserId(userId);
//
//        if(userOptional.isPresent())
//        {
//            User extractedUser = userOptional.get();
//
//            return ResponseEntity.ok(extractedUser); //200
//        }
//
//        return ResponseEntity.badRequest().build(); //400
//    }

    @GetMapping
    public ResponseEntity<User> loginUser(@RequestBody String email, @RequestBody String password)
    {
        Optional<User> u = uDAO.findByEmailAndPassword(email, password);

        if(u.isPresent()){
            User extractedUser = u.get();
            if (extractedUser.getPassword() == password && extractedUser.getEmail() == email) {
                return ResponseEntity.ok(extractedUser); //200
            }
        }
        return ResponseEntity.badRequest().build(); //400
    }

    @PutMapping(value="/password/{userId}")
    public ResponseEntity updatePassword(@RequestBody String password, @PathVariable int userId)
    {
        Optional<User> u = uDAO.findByUserId(userId);
        if (u.isPresent()) {
            User extractedUser = u.get();
            if (extractedUser.getPassword() != password) {
                extractedUser.setPassword(password);
            } else {
                System.out.println("Your new password cannot be the same as your current password.");;
            }
            return ResponseEntity.accepted().body(uDAO.save(extractedUser)); //202
        }
        return ResponseEntity.badRequest().build(); //400
    }

    @PutMapping(value="/address/{userId}")
    public ResponseEntity<User> updateAddress(@RequestBody String address, @PathVariable int userId)
    {
        Optional<User> u = uDAO.findByUserId(userId);
        if (u.isPresent()) {
            User extractedUser = u.get();
            if (extractedUser.getAddress() != address) {
                extractedUser.setAddress(address);
            }
            return ResponseEntity.accepted().body(uDAO.save(extractedUser)); //202
        }
        return ResponseEntity.badRequest().build();
    }
}

