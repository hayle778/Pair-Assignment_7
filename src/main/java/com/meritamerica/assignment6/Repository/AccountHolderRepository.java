package com.meritamerica.assignment6.Repository;

import com.meritamerica.assignment6.exceptions.ExceedsCombinedBalanceLimitException;
import com.meritamerica.assignment6.exceptions.ExceedsFraudSuspicionLimitException;
import com.meritamerica.assignment6.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

@RequestMapping(value = "/rest/accountHolder/log")
public class AccountHolderRepository extends JpaRepositoriesAutoConfiguration {

 @Autowired
     AccountHolderRepository accountHolderRepository;
     AccountHoldersContactDetail accountHoldersContactDetail;
     CheckingAccount checkingAccount;
     SavingsAccount savingsAccount;
     CDAccount cdAccount;
     CDOffering cdOffering;
     private Object AccountHolderRepository;

 // get Mapping getting the data from the AccountHolder
 @GetMapping
 public List<AccountHolder> getAll(){
  return accountHolderRepository.findAll();
 // return accountHolderRepository.f
 }

 private List<AccountHolder> findAll() {
 // this oop class help us to find the instance variable and a value from a model class
  AccountHolder accountHolder = new AccountHolder();
 return findAll();
 }
@GetMapping(value = "update/{name}")
 public List<AccountHolder> update(@PathVariable final String name) throws ExceedsFraudSuspicionLimitException, ExceedsCombinedBalanceLimitException {
  // this AccountHolder class call all list that we have
  AccountHolder accountHolder = new AccountHolder();
 //accountHolder.setFirstName("ff");

  accountHolder.addCDAccount();
  accountHolder.addCheckingAccount();
   accountHolder.addSavingsAccount();
  // accountHolder.addCDOffering();
// accountHolder.addAccountHolder();

 /*BankAccount bankAccount = new BankAccount();
  bankAccount.getAccountNumber();
  bankAccount.getInterestRate();
  bankAccount.getBalance();
  bankAccount.getOpenedOn();*/



return (List<AccountHolder>) AccountHolderRepository;
 //return (List<Transaction>)  BankAccountRepository;

}




}
