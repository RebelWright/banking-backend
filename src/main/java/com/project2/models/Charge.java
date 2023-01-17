package com.project2.models;


import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name="charges")
@Component
public class Charge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chargeId;

    //Many charges can belong to one account
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "accountId")
    private Account account;

    @Column(nullable = false)
    private int chargeAmount;
    @Column(nullable = false)
    private String chargeName;
    @Column(nullable = false)
    private String date;

    public Charge() {
    }

    public Charge(int chargeId, Account account, int chargeAmount, String chargeName, String date) {
        this.chargeId = chargeId;
        this.account = account;
        this.chargeAmount = chargeAmount;
        this.chargeName = chargeName;
        this.date = date;
    }

    public Charge(Account account, int chargeAmount, String chargeName, String date) {
        this.account = account;
        this.chargeAmount = chargeAmount;
        this.chargeName = chargeName;
        this.date = date;
    }

    public int getChargeId() {
        return chargeId;
    }

    public void setChargeId(int chargeId) {
        this.chargeId = chargeId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(int chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public String getChargeName() {
        return chargeName;
    }

    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Charge{" +
                "chargeId=" + chargeId +
                ", account=" + account +
                ", chargeAmount=" + chargeAmount +
                ", chargeName='" + chargeName + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
