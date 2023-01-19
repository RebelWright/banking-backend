package com.project2.daos;

import com.project2.models.Account;
import com.project2.models.Charge;
import com.project2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountDAO extends JpaRepository<Account, Integer>{
    //get all accounts by user id
    public Optional<List<Account>> findByUser(User user);

    //get account by id
    public Optional<Account> findByAccountId(Integer accountId);
}