package com.project2.daos;

import com.project2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface UserDAO extends JpaRepository<User, Integer> {
    //get user by id
    public Optional<User> findByUserId(Integer userId);

    //login user
    public Optional<User> findByEmailAndPassword(String email, String password);
}