package com.meritamerica.assignment6.Resource;

import com.meritamerica.assignment6.Repository.AccountHolderRepository;
import com.meritamerica.assignment6.Repository.BankAccountRepository;
import com.meritamerica.assignment6.Repository.TransactionRepository;
import com.meritamerica.assignment6.models.AccountHolder;
import com.meritamerica.assignment6.exceptions.*;
import com.meritamerica.assignment6.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.Valid;
import java.util.List;
/**
 * This controller class allows, cd offerings, account holders and accounts associated with
 * an account holder to be created and retrieved from the web services provided by the API.
 */
     @RestController
     @Component
  public class MeritBankControllerResource {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private AccountHolderRepository accountHolderRepository;
    private BankAccountRepository bankAccountRepository;
    private TransactionRepository transactionRepository;



    Logger logger = LoggerFactory.getLogger(this.getClass());

    //region CDOffering
    /**
     * This method takes in a cd offering and post it to the API of Merit Bank and
     * returns the cd offering.
     *
     * @return the cd offering posted to the API.
     */
    @PostMapping(value = "AccountHolders/{id}/AccountHoldersContactDetail")
    @ResponseStatus(HttpStatus.CREATED)
    public static void postAccountHolderContactDetail(){

    }
    @GetMapping(value = "AccountHolders/{id}/AccountHoldersContactDetail")
    @ResponseStatus(HttpStatus.OK)
    public static void getAccountHolderContactDetail(){

    }

    @PostMapping(value="/CDOfferings")
    @ResponseStatus(HttpStatus.CREATED)
    public CDOffering postCDOffering(@RequestBody CDOffering cdOffering) throws InvalidArgumentException {
        if (cdOffering.getInterestRate() <= 0 || cdOffering.getInterestRate() >= 1 || cdOffering.getTerm() < 1) {
            throw new InvalidArgumentException("Invalid Term or Interest Rate");
        }
        MeritBank.addCDOffering(cdOffering);
        return cdOffering;
    }

    /**
     * This method retrieves an array of all the cd offerings available from Merit Bank.
     *
     * @return an array of Merit Banks cd offerings.
     */
    @GetMapping(value="/CDOfferings")
    @ResponseStatus(HttpStatus.OK)
    public List<CDOffering> getCDOfferings() throws OfferingNotFoundException {
        if (MeritBank.getCDOfferings().size() < 1) {
            throw new OfferingNotFoundException("No CD Offerings exist");
        }
        return MeritBank.getCDOfferings();
    }

    /**
     * This method retrieves a single cd offering from a given id from Merit Bank.
     *
     * @param id the path variable to an individual cd offering.
     * @return the requested cd offering from Merit Bank
     */
    @GetMapping(value="CDOfferings/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CDOffering getCDOfferingById(@PathVariable("id") int id) throws OfferingNotFoundException {
        if (id > MeritBank.getCDOfferings().size()) {
            throw new OfferingNotFoundException("CD Offering cannot be located");
        }
        return MeritBank.getCDOfferingById(id);
    }
    //endregion

    //region AccountHolder/s

    /**
     * This method takes in a account holder and post it to the API of Merit Bank and
     * returns the account holder.
     *
     * @param accountHolder the account holder to be posted to the API.
     * @return the account holder posted to the API.
     */
    @PostMapping(value="/AccountHolders")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder postAccountHolder(@RequestBody @Valid AccountHolder accountHolder) {
        MeritBank.addAccountHolder(accountHolder);
        return accountHolder;
    }

    /**
     * This method retrieves an array of all of Merit Banks account holders.
     *
     * @return an array of Merit Banks account holders.
     */
    @GetMapping(value="/AccountHolders")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountHolder> getAccountHolders() throws AccountHolderNotFoundException {
        if (MeritBank.getAccountHolders().size() < 1) {
            throw new AccountHolderNotFoundException("No Account Holders exist");
        }
        return MeritBank.getAccountHolders();
    }

    /**
     * This method retrieves a single account holder from a given id from Merit Bank.
     *
     * @param id the path variable to an individual account holder of Merit Bank.
     * @return the requested account holder of Merit Bank.
     */
    @GetMapping(value="/AccountHolders/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder getAccountHolderById(@PathVariable("id") int id) throws AccountHolderNotFoundException {
        //return MeritBank.getAccountHolderbyId(id);
        AccountHolder accountHolder = MeritBank.getAccountHolderbyId(id);
        if (accountHolder == null)  {
            throw new AccountHolderNotFoundException("Account not found");
        }
        return accountHolder;
    }
    //endregion

    //region AccountHolders/BankAccounts

    /**
     * This method takes in a checking account of an individual account holder designated
     * by the path variable and post it to the API of Merit Bank and returns the checking
     * account.
     *
     * @param id the path variable to an individual account holder.
     * @param checkingAccount the checking account to be posted to the API.
     * @return the checking account posted to the API.
     */
    @PostMapping(value="/AccountHolders/{id}/CheckingAccounts")
    @ResponseStatus(HttpStatus.CREATED)
    public CheckingAccount postCheckingAccountById(@PathVariable("id") int id, @RequestBody @Valid CheckingAccount checkingAccount)
            throws ExceedsFraudSuspicionLimitException, ExceedsCombinedBalanceLimitException, AccountHolderNotFoundException {

        AccountHolder accountHolder = MeritBank.getAccountHolderbyId(id);
        if (accountHolder == null) {
            throw new AccountHolderNotFoundException("Checking Account failed to Post: Account Holder could not be located");
        }
        accountHolder.addCheckingAccount(checkingAccount);
        return checkingAccount;
    }

    /**
     * This method retrieves an array of all the checking accounts of an individual account
     * holder of Merit Bank.
     *
     * @param id the path variable to an individual account holder
     * @return an array of the requested account holders checking accounts.
     */
    @GetMapping(value="AccountHolders/{id}/CheckingAccounts")
    @ResponseStatus(HttpStatus.OK)
    public List<CheckingAccount> getCheckingAccountsById(@PathVariable("id") int id) throws AccountHolderNotFoundException {
        AccountHolder accountHolder = MeritBank.getAccountHolderbyId(id);
        if (accountHolder == null) {
            throw new AccountHolderNotFoundException("Checking Account cannot be located: Account Holder could not be located");
        }
        return accountHolder.getCheckingAccounts();
    }

    /**
     * This method takes in a savings account of an individual account holder designated
     * by the path variable and post it to the API of Merit Bank and returns the savings
     * account.
     *
     * @param id the path variable to an individual account holder.
     * @param savingsAccount the savings account to be posted to the API.
     * @return the savings account posted to the API.
     */
    @PostMapping(value="AccountHolders/{id}/SavingsAccounts")
    @ResponseStatus(HttpStatus.CREATED)
    public SavingsAccount postSavingsAccountById(@PathVariable("id") int id, @RequestBody @Valid SavingsAccount savingsAccount)
            throws ExceedsFraudSuspicionLimitException, ExceedsCombinedBalanceLimitException, AccountHolderNotFoundException {

        AccountHolder accountHolder = MeritBank.getAccountHolderbyId(id);
        if (accountHolder == null) {
            throw new AccountHolderNotFoundException("Savings Account failed to Post: Account Holder could not be located");
        }
        accountHolder.addSavingsAccount(savingsAccount);
        return savingsAccount;
    }

    /**
     * This method retrieves an array of all the savings accounts of an individual account
     * holder of Merit Bank.
     *
     * @param id the path variable to an individual account holder
     * @return an array of the requested account holders savings accounts.
     */
    @GetMapping(value="AccountHolders/{id}/SavingsAccounts")
    @ResponseStatus(HttpStatus.OK)
    public List<SavingsAccount> getSavingsAccountsById(@PathVariable("id") int id) throws AccountHolderNotFoundException {
        AccountHolder accountHolder = MeritBank.getAccountHolderbyId(id);
        if (accountHolder == null) {
            throw new AccountHolderNotFoundException("Savings Account cannot be located: Account Holder could not be located");
        }
        return accountHolder.getSavingsAccounts();
    }
    /**
     * This method takes in a cd account of an individual account holder designated
     * by the path variable and post it to the API of Merit Bank and returns the cd
     * account.
     *
     * @param id the path variable to an individual account holder.
     * @param cdAccount the cd account to be posted to the API.
     * @return the cd account posted to the API.
     */
    @PostMapping(value="AccountHolders/{id}/CDAccounts")
    @ResponseStatus(HttpStatus.CREATED)
    public CDAccount postCDAccountById(@PathVariable("id") int id, @RequestBody @Valid CDAccount cdAccount)
            throws ExceedsFraudSuspicionLimitException, AccountHolderNotFoundException {

        AccountHolder accountHolder = MeritBank.getAccountHolderbyId(id);
        if (accountHolder == null) {
            throw new AccountHolderNotFoundException("CD Account failed to Post: Account Holder could not be located");
        }
        accountHolder.addCDAccount(cdAccount);
        return cdAccount;
    }

    /**
     * This method retrieves an array of all the cd accounts of an individual account
     * holder of Merit Bank.
     *
     * @param id the path variable to an individual account holder
     * @return an array of the requested account holders cd accounts.
     */
    @GetMapping(value="AccountHolders/{id}/CDAccounts")
    @ResponseStatus(HttpStatus.OK)
    public List<CDAccount> getCDAccountsById(@PathVariable("id") int id) throws AccountHolderNotFoundException {
        AccountHolder accountHolder = MeritBank.getAccountHolderbyId(id);
        if (accountHolder == null) {
            throw new AccountHolderNotFoundException("CD Account cannot be located: Account Holder could not be located");
        }
        return accountHolder.getCDAccounts();
    }
    //endregion


}
