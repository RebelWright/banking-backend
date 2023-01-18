package com.project2.controllers;

import com.project2.daos.UserDAO;
import com.project2.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value="users")
public class UserController {
    private final UserDAO uDAO;

    @Autowired
    public UserController(UserDAO uDAO){
        super();
        this.uDAO = uDAO;
    }

    @GetMapping(value="/")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(uDAO.findAll());
    }

    @GetMapping(value="/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable int userId){
        Optional<User> user = uDAO.findByUserId(userId);
        if(user.isPresent()) {
            User extractedUser = user.get();
            return ResponseEntity.ok(extractedUser); //200
        }
        return ResponseEntity.badRequest().build(); //400
    }

    @PostMapping(value="/register")
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

    @PostMapping(value="/login")
    public ResponseEntity<User> loginUser(@RequestBody User u)
    {
        // getting email and password from request body
        String email = u.getEmail();
        String password = u.getPassword();

        // find a user that matches credentials
        Optional<User> user = uDAO.findByEmailAndPassword(email, password);

        if(user.isPresent()){
            User extractedUser = user.get();

            if(email.equals(extractedUser.getEmail()) && password.equals(extractedUser.getPassword())){
                return ResponseEntity.ok(extractedUser); //200
            }
        }
        return ResponseEntity.badRequest().build(); //400
    }

    @PutMapping(value="/update")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        Optional<User> editedUser = uDAO.findByUserId(user.getUserId());

        User extractedUser =  editedUser.get();

        if (user.getUserId() < 1) {
            return ResponseEntity.badRequest().build(); //400
        }else{

            return ResponseEntity.accepted().body(uDAO.save(extractedUser)); //202
        }
    }

    @PutMapping(value="/password/{userId}")
    public ResponseEntity updatePassword(@RequestBody User user, @PathVariable int userId)
    {
        // get password from request body
        String password = user.getPassword();

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
    public ResponseEntity<User> updateAddress(@RequestBody User user, @PathVariable int userId)
    {
        String address = user.getAddress();

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

    @DeleteMapping(value="/delete/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable int userId){
        if (uDAO.findByUserId(userId).isPresent()){
            uDAO.deleteById(userId);
            return ResponseEntity.ok("Successful Deletion"); //200
        }
        return ResponseEntity.badRequest().build(); //400
    }


}

