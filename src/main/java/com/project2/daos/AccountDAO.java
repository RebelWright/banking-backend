package com.project2.daos;

import com.project2.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountDAO extends JpaRepository<Account, Integer>{
    //get all accounts by user id
    public Optional<List<Account>> findByUser(Integer userId);

    //get account by id
    public Optional<Account> findByAccountId(Integer accountId);
}