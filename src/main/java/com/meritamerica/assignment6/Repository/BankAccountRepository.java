package com.meritamerica.assignment6.Repository;

import com.meritamerica.assignment6.models.AccountHolder;
import com.meritamerica.assignment6.models.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.Entity;
import java.util.List;

@Entity
@RequestMapping(value = "rest/BankAccount/log")
public class BankAccountRepository extends JpaRepositoriesAutoConfiguration {

    @Autowired
// BankAccount bankAccount;
    /*openedOn;
   accountNumber;
interestRate;
 balance;*/
//BankAccountRepository bankAccountRepository;
            BankAccount bankAccount = new BankAccount();
          private Object BankAccountRepository;
    private Object BankAccount;

    @GetMapping
    public List<BankAccount> getAll() {
        return (List<com.meritamerica.assignment6.models.BankAccount>) BankAccount;

    }

    private static List<BankAccount> findAll() {
        // this oop class help us to find the instance variable and a value from a model class
        BankAccount bankAccount = new BankAccount();
        return findAll();
    }

    @GetMapping(value = "update/{name2}")
    public List<BankAccount> update(@PathVariable final String name) {
        // this AccountHolder class call all list that we have
        new BankAccount();
        //accountHolder.setFirstName("ff");

        bankAccount.getOpenedOn();
        bankAccount.getAccountNumber();
        bankAccount.getInterestRate();
        bankAccount.getOpenedOn();
        return (List<BankAccount>) BankAccountRepository;
    }
}