package com.project2.models;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name="accounts")
@Component
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountId;

    //Many accounts can belong to one user
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private User user;

    @Column(nullable=false)
    private String accountType;
    @Column(nullable=false)
    private String accountName;
    @Column(nullable=false)
    private double balance = 0.00;

    public Account() {
    }

    public Account(int accountId, User user, String accountType, String accountName, double balance) {
        this.accountId = accountId;
        this.user = user;
        this.accountType = accountType;
        this.accountName = accountName;
        this.balance = balance;
    }

    public Account(User user, String accountType, String accountName, double balance) {
        this.user = user;
        this.accountType = accountType;
        this.accountName = accountName;
        this.balance = balance;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", user=" + user +
                ", accountType='" + accountType + '\'' +
                ", accountName='" + accountName + '\'' +
                ", balance=" + balance +
                '}';
    }
}
