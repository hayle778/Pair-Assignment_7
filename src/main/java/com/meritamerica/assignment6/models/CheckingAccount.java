package com.meritamerica.assignment6.models;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

     @Entity
     @Inheritance
     public class CheckingAccount extends BankAccount {

    //@GeneratedValue(strategy = GenerationType.AUTO)
         protected CheckingAccount() {
        super(MeritBank.getNextAccountNumber(), 0, MeritBank.CHECKING_INTEREST_RATE, new Date());
    }

    public CheckingAccount(double openingBalance) {
        super(MeritBank.getNextAccountNumber(), openingBalance, MeritBank.CHECKING_INTEREST_RATE, new Date());
    }

    private CheckingAccount(long accountNumber, double balance, double interestRate, Date openedOn) {
        super(accountNumber, balance, interestRate, openedOn);
    }

    static CheckingAccount readFromString(String accountData) throws ParseException{
        String[] temp = accountData.split(",");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        long accountNumber = Long.parseLong(temp[0]);
        double balance = Double.parseDouble(temp[1]);
        double interestRate = Double.parseDouble(temp[2]);
        Date date = dateFormat.parse(temp[3]);

        return new CheckingAccount(accountNumber, balance, interestRate, date);
    }
}
