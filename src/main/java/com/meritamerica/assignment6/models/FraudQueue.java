package com.meritamerica.assignment6.models;

import com.meritamerica.assignment6.Repository.BankAccountRepository;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import java.util.LinkedList;

/**
 * The fraud queue contains a list of transactions that have been flagged for review
 * before processing by exceeding the maximum amount of a transaction set by Merit Bank.
 */
   @Entity
   @Inheritance
public class FraudQueue {

   @ JoinColumn(name = "employee_id")
   private MeritBank meritBank;






    private LinkedList<Transaction> transactions;

    /** no arguments constructor */
    FraudQueue() { this.transactions = new LinkedList<>(); }

    /**
     * this method adds a transaction to the list of transactions to be reviewed by
     * the fraud department
     *
     * @param transaction the transaction to be added to the fraud queue
     */
    public void addTransaction(Transaction transaction) { transactions.addLast(transaction); }

    /**
     * this method pops a transaction off the stack
     *
     * @return a transaction from the fraud queue
     */
    public Transaction getTransaction() {
        if (this.transactions.size() == 0) return null;
        else return transactions.pop();
    }
}
