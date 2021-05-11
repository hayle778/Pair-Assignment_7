package com.meritamerica.assignment6.models;

import com.meritamerica.assignment6.exceptions.ExceedsAvailableBalanceException;
import com.meritamerica.assignment6.exceptions.ExceedsFraudSuspicionLimitException;
import com.meritamerica.assignment6.exceptions.NegativeAmountException;

import javax.persistence.*;

@Entity
@Inheritance
public class DepositTransaction extends Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
     protected BankAccount targetAccount;
      protected double amount;

      @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DepositTransaction_id")
    private Transaction transaction;


    DepositTransaction(BankAccount targetAccount, double amount) {
        this.targetAccount = targetAccount;
        this.amount = amount;
    }

    @Override
    public void process() throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException {
        if (amount > MeritBank.FRAUD_SUSPICION_THRESHOLD) throw new ExceedsFraudSuspicionLimitException("Possible fraud detected on deposit");
        if (amount < 0) throw new NegativeAmountException("Deposit amount must be greater than 0");

        this.targetAccount.deposit(this.amount);
        this.targetAccount.addTransaction(this);
    }
}
