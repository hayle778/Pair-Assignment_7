package com.meritamerica.assignment6.models;

import com.meritamerica.assignment6.Resource.MeritBankControllerResource;
import com.meritamerica.assignment6.exceptions.ExceedsCombinedBalanceLimitException;
import com.meritamerica.assignment6.exceptions.ExceedsFraudSuspicionLimitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

    @Entity
    @RestController
    public class AccountHolder implements Comparable<AccountHolder> {


        @Autowired
        @OneToOne
        AccountHoldersContactDetail accountHoldersContactDetail;
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)

        private int id;
   // private String UserName;
     private static int nextId = 1;
     protected Date openedOn;
     protected Long accountNumber;
     protected double interestRate;
     protected double balance;

    // the account holder class pull all the Bank account, (SA,CA,CDA)
      @ManyToOne
      MeritBank meritBank;
        @OneToOne
        MeritBankControllerResource meritBankControllerResource;

        @JoinColumn(name =  "accountHolder_id")
      @OneToMany(fetch = FetchType.LAZY, mappedBy = "accountHolder") // this mapped to pull any child to the super class

   private List<SavingsAccount> savingsAccounts;
   private List<CheckingAccount> checkingAccounts;
   private List<CDAccount> cdAccounts;
   private List<BankAccount> bankAccounts;
   private  List<CDOffering> cdOfferings;
    private List<AccountHoldersContactDetail> accountHoldersContactDetails;


    CheckingAccount checkingAccount;
    SavingsAccount savingsAccount;
    CDAccount cdAccount;
    /** an account holders first name */
    @NotNull(message = "First name is a required field")
    @NotBlank(message = "First name cannot be left blank")
    private String firstName;

    /** an account holders middle name */
    private String middleName;

    /** an account holders last name */
    @NotNull(message = "Last name is a required field")
    @NotBlank(message = "Last name cannot be left blank")
    private String lastName;

    /** an account holders social security number */
    @NotNull(message = "SSN is a required field")
    @NotBlank(message = "SSN cannot be left blank")
    private String SSN;

    public AccountHolder() {

        this.id = getNextId();
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.SSN = SSN;
        this.checkingAccounts = new ArrayList<>();
        this.savingsAccounts = new ArrayList<>();
        this.cdAccounts = new ArrayList<>();
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSSN() {
        return this.SSN;
    }

    public void setSSN(String ssn) {
        this.SSN = ssn;
    }

    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }

    private static int getNextId() { return nextId++; }
    //endregion


    //region CheckingAccount Methods

    /**
     * this method takes in an opening balance and constructs a checking account object
     * which it passes to another addCheckingAccount method.
     *
     * @param openingBalance the opening balance of the new checking account
     * @return a new checking account object
     */
    public CheckingAccount addCheckingAccount(double openingBalance) throws
            ExceedsCombinedBalanceLimitException, ExceedsFraudSuspicionLimitException {
        return this.addCheckingAccount(new CheckingAccount(openingBalance));
    }

    public CheckingAccount addCheckingAccount(CheckingAccount checkingAccount) throws
            ExceedsCombinedBalanceLimitException, ExceedsFraudSuspicionLimitException {
        if (this.getCheckingBalance() + this.getSavingsBalance() +
                checkingAccount.getBalance() > MeritBank.NEW_ACCOUNT_MAX_BALANCE){
            throw new ExceedsCombinedBalanceLimitException("Combined balance exceeds the maximum threshold " +
                    "for a new checking account");
        } else if (checkingAccount.getBalance() > MeritBank.FRAUD_SUSPICION_THRESHOLD){
            throw new ExceedsFraudSuspicionLimitException("Possible fraud detected on new checking account");
        }
        //checkingAccount.addTransaction(new DepositTransaction(checkingAccount, checkingAccount.getBalance()));
        this.checkingAccounts.add(checkingAccount);
        return checkingAccount;
    }

    /**
     * this method gets and returns an array list of the account holders checking accounts
     *
     * @return the list of an account holders checking accounts
     */
    public List<CheckingAccount> getCheckingAccounts() { return this.checkingAccounts; }

    /**
     * this method gets and returns the number of checking accounts held by the account holder
     *
     * @return the number of checking accounts held by the account holder
     */
    public int getNumberOfCheckingAccounts() { return this.checkingAccounts.size(); }

    /**
     * this method gets and returns the combined balance of all checking accounts held by an
     * account holder
     *
     * @return the combined balance of an account holders checking accounts
     */
    public double getCheckingBalance() {
        double result = 0.0;
        for (CheckingAccount checkingAccount : this.checkingAccounts) {
            result += checkingAccount.getBalance();
        }
        return result;
    }
    //endregion

    //region SavingsAccount Methods
    /**
     * this method takes in an opening balance and constructs a savings account object
     * which it passes to another addSavingsAccount method.
     *
     * @param openingBalance the opening balance of the new savings account
     * @return a new savings account object
     */
    public SavingsAccount addSavingsAccount(double openingBalance) throws ExceedsCombinedBalanceLimitException,
            ExceedsFraudSuspicionLimitException {
        return this.addSavingsAccount();
    }

    /**
     * this method takes in a savings account and checks to see if the account holder is
     * under the maximum balance allowed to create a new savings account and if so adds it
     * to the account holders list of savings accounts
     *
     * @return the savings account that has been successfully added
     */
    public SavingsAccount addSavingsAccount() throws ExceedsCombinedBalanceLimitException,
            ExceedsFraudSuspicionLimitException {
        if (this.getCheckingBalance() + this.getSavingsBalance()
                + savingsAccount.getBalance() > MeritBank.NEW_ACCOUNT_MAX_BALANCE){
            throw new ExceedsCombinedBalanceLimitException("Combined balance exceeds the maximum threshold " +
                    "for a new savings account");
        } else if (savingsAccount.getBalance() > MeritBank.FRAUD_SUSPICION_THRESHOLD){
            throw new ExceedsFraudSuspicionLimitException("Possible fraud detected on new checking account");
        }
        //savingsAccount.addTransaction(new DepositTransaction(savingsAccount, savingsAccount.getBalance()));
        this.savingsAccounts.add(savingsAccount);
        return savingsAccount;
    }

    /**
     * this method gets and returns an array list of the account holders savings accounts
     *
     * @return the list of an account holders savings accounts
     */
    public List<SavingsAccount> getSavingsAccounts() { return this.savingsAccounts; }

    /**
     * this method gets and returns the number of savings accounts held by the account holder
     *
     * @return the number of savings accounts held by the account holder
     */
    public int getNumberOfSavingsAccounts() { return this.savingsAccounts.size(); }

    /**
     * this method gets and returns the combined balance of all savings accounts held by an
     * account holder
     *
     * @return the combined balance of an account holders savings accounts
     */
    public double getSavingsBalance() {
        double result = 0.0;
        for (SavingsAccount savingsAccount : this.savingsAccounts) {
            result += savingsAccount.getBalance();
        }
        return result;
    }
    //endregion

    //region CDAccount Methods
    /**
     * this method takes in an opening balance and constructs a cd account object
     * which it passes to another addCDAccount method.
     *
     * @param openingBalance the opening balance of the new cd account
     * @return a new cd account object
     */
    public CDAccount addCDAccount(CDOffering cdOffering, int openingBalance) throws ExceedsFraudSuspicionLimitException{
        return this.addCDAccount();
    }

    /**
     * this method takes in a cd account and  adds it to the account holders list of cd accounts
     *
     * @return the cd account that has been successfully added
     */
    public CDAccount addCDAccount() throws ExceedsFraudSuspicionLimitException {
        if (cdAccount.getBalance() > MeritBank.FRAUD_SUSPICION_THRESHOLD){
            throw new ExceedsFraudSuspicionLimitException("Possible fraud detected on new cd account");
        }
        //cdAccount.addTransaction(new DepositTransaction(cdAccount, cdAccount.getBalance()));
        this.cdAccounts.add(cdAccount);
        return cdAccount;
    }

    /**
     * this method gets and returns an array list of the account holders cdaccounts
     *
     * @return the list of an account holders cd accounts
     */
    public List<CDAccount> getCDAccounts() { return this.cdAccounts; }

    /**
     * this method gets and returns the number of cd accounts held by the account holder
     *
     * @return the number of cd accounts held by the account holder
     */
    public int getNumberOfCDAccounts() { return this.cdAccounts.size(); }

    /**
     * this method gets and returns the combined balance of all cd accounts held by an
     * account holder
     *
     * @return the combined balance of an account holders cd accounts
     */
    public double getCDBalance(){
        double result = 0.0;
        for (CDAccount cdAccount : this.cdAccounts) {
            result += cdAccount.getBalance();
        }
        return result;
    }
    //endregion

    /**
     * this method gets and returns the combined balance of all accounts held by an
     * account holder
     *
     * @return the combined balance of an account holders accounts
     */
    public double getCombinedBalance() {
        return this.getCheckingBalance() + this.getSavingsBalance() + this.getCDBalance();
    }

    // region read/write from strings
    public String writeToString(){
        StringBuilder result = new StringBuilder(this.lastName + "," + this.middleName + "," + this.firstName
                + "," + this.SSN + "\n");

        result.append(this.getNumberOfCheckingAccounts()).append("\n");


        for (CheckingAccount checkingAccount : this.checkingAccounts) {
            result.append(checkingAccount.writeToString()).append("\n");
            result.append(checkingAccount.getTransactions().size()).append("\n");
            for (Transaction trans : checkingAccount.getTransactions()) {
                result.append(trans.writeToString()).append("\n");
            }
        }

        result.append(this.getNumberOfSavingsAccounts()).append("\n");
        for (SavingsAccount savingsAccount : this.savingsAccounts) {
            result.append(savingsAccount.writeToString()).append("\n");
            result.append(savingsAccount.getTransactions().size()).append("\n");
            for (Transaction trans : savingsAccount.getTransactions()) {
                result.append(trans.writeToString()).append("\n");
            }
        }

        result.append(this.getNumberOfCDAccounts()).append("\n");
        for (CDAccount cdAccount : this.cdAccounts) {
            result.append(cdAccount.writeToString()).append("\n");
            result.append(cdAccount.getTransactions().size()).append("\n");
            for (Transaction trans : cdAccount.getTransactions()) {
                result.append(trans.writeToString()).append("\n");
            }
        }

        return result.toString();
    }

    public static AccountHolder readFromString(String accountHolderData) throws Exception {
        String[] temp = accountHolderData.split(",");
        if (temp.length != 4) throw new Exception();
        return  readFromString(accountHolderData);
    }



    @Override
    public int compareTo(AccountHolder other) {
        if (this.getCombinedBalance() < other.getCombinedBalance()) return -1;
        else if (this.getCombinedBalance() > other.getCombinedBalance()) return 1;
        else return 0;
    }

    public void addCheckingAccount() {
    }
////////// this methods ask by accountHolderRepository for updating the updated value
 //public void addCDAccount() {



//}

    //public void addCheckingAccount() {
   // }

    //public void addSavingsAccount() {
    //}


}