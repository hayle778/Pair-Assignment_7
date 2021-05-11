package com.meritamerica.assignment6.models;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


      @Entity
      @Inheritance
      public class SavingsAccount extends BankAccount {
          @Id
         @GeneratedValue(strategy = GenerationType.AUTO)
          @ ManyToOne
          @JoinColumn(name = "SavingAcc_id")
          private BankAccount bankAccount;
          public SavingsAccount()
  {
        super(MeritBank.getNextAccountNumber(), 0, MeritBank.SAVINGS_INTEREST_RATE, new Date());
    }

    public SavingsAccount(double openingBalance) {
        super(MeritBank.getNextAccountNumber(), openingBalance, MeritBank.SAVINGS_INTEREST_RATE, new Date());
    }

    private SavingsAccount(long accountNumber, double balance, double interestRate, Date openedOn) {
        super(accountNumber, balance, interestRate, openedOn);
    }

   public static SavingsAccount readFromString(String accountData) throws ParseException {
        String[] temp = accountData.split(",");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        long accountNumber = Long.parseLong(temp[0]);
        double balance = Double.parseDouble(temp[1]);
        double interestRate = Double.parseDouble(temp[2]);
        Date date = dateFormat.parse(temp[3]);

        return new SavingsAccount(accountNumber, balance, interestRate, date);
    }
}
