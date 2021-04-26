package com.meritamerica.assignment6.Repository;

import com.meritamerica.assignment6.exceptions.ExceedsAvailableBalanceException;
import com.meritamerica.assignment6.exceptions.ExceedsFraudSuspicionLimitException;
import com.meritamerica.assignment6.exceptions.NegativeAmountException;
import com.meritamerica.assignment6.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.Entity;
import java.util.List;

@Entity
@RequestMapping(value = "rest/Transaction/log")
public class TransactionRepository extends JpaRepositoriesAutoConfiguration {

    @Autowired
    TransactionRepository transactionRepository;
   // Transaction transaction;
    TransferTransaction transferTransaction;
    WithdrawTransaction withdrawTransaction;
    DepositTransaction depositTransaction;

    Transaction  transaction = new Transaction() {
        @Override
        public void process() throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException {
        }
    };
    private Object TransactionRepository;
   /* BankAccount bankAccount = new BankAccount();
    private Object BankAccountRepository;
    private Object BankAccount;*/

    @GetMapping
    public List<TransactionRepository > getAll() {
        return (List<com.meritamerica.assignment6.Repository.TransactionRepository>) TransactionRepository;
    }

    private static List<Transaction> findAll() {
        // this oop class help us to find the instance variable and a value from a model class
       //Transaction bankAccount = new Transaction() {
        //   @Override
          // public void process() throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException {

         //  }
      // };
        return findAll();
    }

    @GetMapping(value = "update/{name3}")
    public List<Transaction> update(@PathVariable final String name) {
        // this AccountHolder class call all list that we have
        new Transaction() {
            @Override
            public void process() throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException {
            }
        };
        //accountHolder.setFirstName("ff");
        return (List<Transaction>) TransactionRepository;



    }





}

