package com.meritamerica.assignment6.models;

import com.meritamerica.assignment6.exceptions.ExceedsAvailableBalanceException;
import com.meritamerica.assignment6.exceptions.ExceedsFraudSuspicionLimitException;
import com.meritamerica.assignment6.exceptions.NegativeAmountException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
public class WithdrawTransaction extends Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    BankAccount targetAccount;
    double amount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @Autowired
    WithdrawTransaction(BankAccount targetAccount, double amount) {
        this.targetAccount = targetAccount;
        this.amount = amount;
    }

    @Override
    public void process() throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException {
        if (amount > MeritBank.FRAUD_SUSPICION_THRESHOLD) throw new ExceedsFraudSuspicionLimitException("Possible fraud detected on withdrawal");
        if (amount < 0) throw new NegativeAmountException("Withdrawal amount must be greater than zero");
        if (amount > targetAccount.getBalance()) throw new ExceedsAvailableBalanceException("Insufficient funds for withdrawal amount");

        this.targetAccount.withdraw(this.amount);
        this.targetAccount.addTransaction(this);
    }
}
