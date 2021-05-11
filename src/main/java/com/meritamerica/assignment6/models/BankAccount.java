package com.meritamerica.assignment6.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class BankAccount extends AccountHolder {
       @Autowired
       @Id
       @ ManyToOne(cascade = CascadeType.ALL)  // with the other models
       @JoinColumn(name = "bankAccount_id")  // it just run with employee_id
     protected AccountHolder accountHolder;
       @GeneratedValue(strategy = GenerationType.AUTO)
    // region instance variables
   @OneToMany
    protected Date openedOn;
    protected Long accountNumber;
    protected double interestRate;
    protected double balance;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "bankHolder")
  //  private List <accountNumber>  accountNumbers;
    private List<CheckingAccount> checkingAccounts;
    private List<SavingsAccount> savingsAccounts;
    private List<CDAccount> cdAccounts;

    //@ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private final ArrayList<Transaction> transactions = new ArrayList<>();
    // endregion

    // region constructors
public BankAccount(){}

    public BankAccount(Date openedOn, Long accountNumber, double interestRate, double balance){
        super();
    }

    public BankAccount(double balance, double interestRate){
        this(MeritBank.getNextAccountNumber(), balance, interestRate, new Date());
    }

    public BankAccount(double balance, double interestRate, Date openedOn){
        this(MeritBank.getNextAccountNumber(), balance, interestRate, openedOn);
    }

    protected BankAccount(long accountNumber, double balance, double interestRate, Date openedOn){
        super();
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.interestRate = interestRate;
        this.openedOn = openedOn;
    }
    // endregion

    // region getters/setters
    public long getAccountNumber() { return this.accountNumber; }

    public double getBalance() { return this.balance; }

    public double getInterestRate()
    { return this.interestRate; }

    public Date getOpenedOn() { return this.openedOn; }

    public boolean withdraw(double amount){
        if (this.balance - amount < 0) {
            return false;
        } else {
            this.balance -= amount;
            return true;
        }
    }

    public boolean deposit (double amount){
        if (amount < 0) {
            return false;
        } else {
            this.balance += amount;
            return true;
        }
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public List<Transaction> getTransactions() { return transactions; }
    // endregion

    // region read/write from string
    public static BankAccount readFromString(String accountData) throws ParseException { return null; }

    @Override
    public String toString() {
        return "BankAccount{" +
                "accountHolder=" + accountHolder +
                ", openedOn=" + openedOn +
                ", accountNumber=" + accountNumber +
                ", interestRate=" + interestRate +
                ", balance=" + balance +
                '}';
    }


    public double futureValue(int years) { return MeritBank.recursiveFutureValue(this.balance, this.interestRate, years); }
}
