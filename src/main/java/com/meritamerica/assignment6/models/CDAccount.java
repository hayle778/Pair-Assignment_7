package com.meritamerica.assignment6.models;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity

public class CDAccount extends BankAccount {
      @Id
     protected AccountHolder accountHolder;
      @ManyToOne
       protected CDOffering cdOffering;
     @JoinColumn (name = "CDAccount_id")
    private BankAccount bankAccount;
      @GeneratedValue(strategy = GenerationType.AUTO)

      //protected CDOffering cdOffering;
    protected int term;

    public CDAccount() {
        super(MeritBank.getNextAccountNumber(), 0, 0, new Date());
    }

    public CDAccount(CDOffering cdOffering, double balance) {
        super(balance, cdOffering.getInterestRate(), new Date());
        this.cdOffering = cdOffering;
    }

    private CDAccount(long accountNumber, double balance, double interestRate, int term, Date openedOn) {
        super(accountNumber, balance, interestRate, openedOn);
        this.cdOffering = new CDOffering(term, interestRate);
        this.term = term;
    }

    public int getTerm() { return this.cdOffering.getTerm(); }

    public static CDAccount readFromString(String accountData) throws ParseException {
        String[] temp = accountData.split(",");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        long accountNumber = Long.parseLong(temp[0]);
        double balance = Double.parseDouble(temp[1]);
        double interestRate = Double.parseDouble(temp[2]);
        Date date = dateFormat.parse(temp[3]);
        int term = Integer.parseInt(temp[4]);

        return new CDAccount(accountNumber, balance, interestRate, term, date);
    }

    public double futureValue(){ return super.futureValue(this.cdOffering.getTerm()); }

    @Override
    public String writeToString() { return super.writeToString() + "," + this.cdOffering.getTerm(); }

    @Override
    public boolean deposit(double amount) {
        Date currentDate = new Date();
        int currentTermYear = openedOn.getYear() - currentDate.getYear();
        if (currentTermYear > term && amount > 0) {
            balance += amount;
            return true;
        }
        return false;
    }

    @Override
    public boolean withdraw(double amount) {
        Date currentDate = new Date();
        int currentTermYear = openedOn.getYear() - currentDate.getYear();
        if (currentTermYear > term && amount <= this.getBalance() && amount > 0) {
            balance -= amount;
            return true;
        }
        return false;
    }


}