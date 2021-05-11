package com.meritamerica.assignment6.Resource;

import com.meritamerica.assignment6.Repository.AccountHolderRepository;
import com.meritamerica.assignment6.Repository.TransactionRepository;
import com.meritamerica.assignment6.models.AccountHolder;
import com.meritamerica.assignment6.exceptions.*;
import com.meritamerica.assignment6.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
     @Table
     @RestController
     @Component

  public class MeritBankControllerResource<AuthenticationManager> extends SpringSecurityJwtApplication{
      @GeneratedValue(strategy = GenerationType.AUTO)
      @Autowired
      private AuthenticationManager authenticationManager;
      @Autowired
         private JwtUtil jwtTokenUtil;

         @Autowired
         private MyUserDetailsService userDetailsService;

      private AccountHolderRepository accountHolderRepository;
      //private BankAccountRepository bankAccountRepository;
      private TransactionRepository transactionRepository;

      Logger logger = LoggerFactory.getLogger(this.getClass());

      @PostMapping(value = "AccountHolders/{id}/AccountHoldersContactDetail")
      @ResponseStatus(HttpStatus.CREATED)
      public static void postAccountHolderContactDetail() {
      AccountHoldersContactDetail accountHoldersContactDetail = new AccountHoldersContactDetail();

      }
      @GetMapping(value = "AccountHolders/{id}/AccountHoldersContactDetail")
      @ResponseStatus(HttpStatus.OK)
      public static void getAccountHolderContactDetail() {
      AccountHoldersContactDetail accountHoldersContactDetail = new AccountHoldersContactDetail();

      }

      @PostMapping(value = "/CDOfferings")
      @ResponseStatus(HttpStatus.CREATED)
      public CDOffering postCDOffering(@RequestBody CDOffering cdOffering) throws InvalidArgumentException {
          if (cdOffering.getInterestRate() <= 0 || cdOffering.getInterestRate() >= 1 || cdOffering.getTerm() < 1) {
              throw new InvalidArgumentException("Invalid Term or Interest Rate");
          }
          MeritBank.addCDOffering(cdOffering);
          return cdOffering;
      }
      @GetMapping(value = "/CDOfferings")
      @ResponseStatus(HttpStatus.OK)
    private List<CDOffering> getCDOfferings() throws OfferingNotFoundException {
          if (MeritBank.getCDOfferings().size() < 1) {
              throw new OfferingNotFoundException("No CD Offerings exist");
          }
          return MeritBank.getCDOfferings();
      }

      @GetMapping(value = "CDOfferings/{id}")
      @ResponseStatus(HttpStatus.OK)
    private CDOffering getCDOfferingById(@PathVariable("id") int id) throws OfferingNotFoundException {
          if (id > MeritBank.getCDOfferings().size()) {
              throw new OfferingNotFoundException("CD Offering cannot be located");
          }
          return MeritBank.getCDOfferingById(id);
      }
      @PostMapping(value = "/AccountHolders")
      @ResponseStatus(HttpStatus.CREATED)
      public AccountHolder postAccountHolder(@RequestBody @Valid AccountHolder accountHolder) {

          MeritBank.addAccountHolder(accountHolder);
          return accountHolder;
      }


      @GetMapping(value = "/AccountHolders")
      @ResponseStatus(HttpStatus.OK)
    private List<AccountHolder> getAccountHolders() throws AccountHolderNotFoundException {
          if (MeritBank.getAccountHolders().size() < 1) {
              throw new AccountHolderNotFoundException("No Account Holders exist");
          }
          return MeritBank.getAccountHolders();
      }


      @GetMapping(value = "/AccountHolders/{id}")
      @ResponseStatus(HttpStatus.OK)
      private AccountHolder getAccountHolderById(@PathVariable("id") int id) throws AccountHolderNotFoundException {
          //return MeritBank.getAccountHolderbyId(id);
          AccountHolder accountHolder = MeritBank.getAccountHolderbyId(id);
          if (accountHolder == null) {
              throw new AccountHolderNotFoundException("Account not found");
          }
          return accountHolder;
      }

      @PostMapping(value = "/AccountHolders/{id}/CheckingAccounts")
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


      @GetMapping(value = "AccountHolders/{id}/CheckingAccounts")
      @ResponseStatus(HttpStatus.OK)
      private List<CheckingAccount> getCheckingAccountsById(@PathVariable("id") int id) throws AccountHolderNotFoundException {
          AccountHolder accountHolder = MeritBank.getAccountHolderbyId(id);
          if (accountHolder == null) {
              throw new AccountHolderNotFoundException("Checking Account cannot be located: Account Holder could not be located");
          }
          return accountHolder.getCheckingAccounts();
      }


      @PostMapping(value = "AccountHolders/{id}/SavingsAccounts")
      @ResponseStatus(HttpStatus.CREATED)
      public SavingsAccount postSavingsAccountById(@PathVariable("id") int id, @RequestBody @Valid SavingsAccount savingsAccount)
              throws ExceedsFraudSuspicionLimitException, ExceedsCombinedBalanceLimitException, AccountHolderNotFoundException {

          AccountHolder accountHolder = MeritBank.getAccountHolderbyId(id);
          if (accountHolder == null) {
              throw new AccountHolderNotFoundException("Savings Account failed to Post: Account Holder could not be located");
          }
          accountHolder.addSavingsAccount();
          return savingsAccount;
      }


      @GetMapping(value = "AccountHolders/{id}/SavingsAccounts")
      @ResponseStatus(HttpStatus.OK)
     private List<SavingsAccount> getSavingsAccountsById(@PathVariable("id") int id) throws AccountHolderNotFoundException {
          AccountHolder accountHolder = MeritBank.getAccountHolderbyId(id);
          if (accountHolder == null) {
              throw new AccountHolderNotFoundException("Savings Account cannot be located: Account Holder could not be located");
          }
          return accountHolder.getSavingsAccounts();
      }

      @PostMapping(value = "AccountHolders/{id}/CDAccounts")
      @ResponseStatus(HttpStatus.CREATED)
      public CDAccount postCDAccountById(@PathVariable("id") int id, @RequestBody @Valid CDAccount cdAccount)
              throws ExceedsFraudSuspicionLimitException, AccountHolderNotFoundException {

          AccountHolder accountHolder = MeritBank.getAccountHolderbyId(id);
          if (accountHolder == null) {
              throw new AccountHolderNotFoundException("CD Account failed to Post: Account Holder could not be located");
          }
          accountHolder.addCDAccount();
          return cdAccount;
      }


      @GetMapping(value = "AccountHolders/{id}/CDAccounts")
      @ResponseStatus(HttpStatus.OK)
      private List<CDAccount> getCDAccountsById(@PathVariable("id") int id) throws AccountHolderNotFoundException {
          AccountHolder accountHolder = MeritBank.getAccountHolderbyId(id);
          if (accountHolder == null) {
              throw new AccountHolderNotFoundException("CD Account cannot be located: Account Holder could not be located");
          }
          return accountHolder.getCDAccounts();
      }

      @PostMapping(value = "AuthenticateManager/Authenticate/CreateUser")
       @ResponseStatus(HttpStatus.CREATED)
      /*  POST /Authenticate/CreateUser
         Creates a new User
         Only administrator can call this
         This should respond with an HTTP 201 if successful with the JSON of the created User (including its userID)
        */
      private static Object AuthenticationMgrUser( List<String> user){

          new ArrayList<>();
         AuthenticationRequest authenticationRequest = new AuthenticationRequest();
          System.out.println("Enter a user name:");
          System.out.println("Enter a password:");

          if(authenticationRequest == null ){
             String req = "Http 201";
             //throw new AuthenticationManager("Http201");
              return req;
          }

          return  AuthenticationMgrUser(user);
      }

       @PostMapping(value ="AuthenticateManager/Authenticate")
               @ResponseStatus(HttpStatus.CREATED)
        public static Object AuthenticationMgr(List<String> Mgr){
          AuthenticationRequest authenticationRequest = new AuthenticationRequest();
      /////     Takes username and password and returns an HTTP 200 and a JWT Token in the body if
           // authentication is successful or an HTTP 401 if authentication fails
       //    Anyone call this*/
           new ArrayList<>();
           AuthenticationRequest authenticationRequestMgr = new AuthenticationRequest();
           System.out.println("Enter a user name:");
           System.out.println("Enter a password:");
           if(authenticationRequest == null ){
               String req = "Http 401";
               //throw new AuthenticationManager("Http201");
               return req;
           }

          return AuthenticationMgr(Mgr);
       }

         @GetMapping (value = "/Me")
         @ResponseStatus(HttpStatus.OK)
      private List<AccountHolder> AccountHolder(List<AccountHolder> ID){
             new AccountHolder();
             return AccountHolder (ID);
         }

         @PostMapping(value = "Me/CheckingAccounts")
         @ResponseStatus(HttpStatus.CREATED)
         private List<AccountHolder> CheckingAccounts(List<SavingsAccount> checkingholders){
         JwtUtil   jwtUtil = new JwtUtil();
          return CheckingAccounts(checkingholders);
         }
         @ GetMapping (value = "Me/CheckingAccounts")
         @ResponseStatus(HttpStatus.OK)
         private List<AccountHolder> CheckingAccounts(List<SavingsAccount> checkingholders, JwtUtil jwtUtil){
             jwtUtil = new JwtUtil();
//Returns all checkings accounts held by the account holder associated with the JWT Token
             return SavingAccounts(checkingholders,jwtUtil);
         }
         @PostMapping(value = "Me/SavingAccounts")
         @ResponseStatus(HttpStatus.CREATED)
         private List<AccountHolder>SavingAccounts(List<SavingsAccount> savingholders){
             JwtUtil jwtUtil = new JwtUtil();
             return SavingAccounts(savingholders);
         }

        /*
          POST /Me/CDAccounts
          Creates a new CD account associated with the account holder associated with the JWT Token
          Only Account Holder can call this
          GET /Me/CDAccounts
          Returns all CD accounts held by the account holder associated with the JWT Token
          Only Account Holder can call this
          CDOffering:
 */
        @GetMapping (value = "Me/SavingAccounts")
         @ResponseStatus(HttpStatus.OK)
    private List<AccountHolder> SavingAccounts(List<SavingsAccount> accountholders, JwtUtil jwtUtil){
             jwtUtil = new JwtUtil();
//Returns all checkings accounts held by the account holder associated with the JWT Token
             return SavingAccounts(accountholders, jwtUtil);
         }

         @PostMapping(value = "Me/CDAccounts")
         @ResponseStatus(HttpStatus.CREATED)
         private List<AccountHolder> CDAccounts(List<SavingsAccount> CDholders){
             JwtUtil   jwtUtil = new JwtUtil();
             return CheckingAccounts(CDholders);
         }
         @ GetMapping (value = "Me/CDAccounts")
         @ResponseStatus(HttpStatus.OK)
         private List<AccountHolder> CDAccounts(List<SavingsAccount> CDholders, JwtUtil jwtUtil){
             jwtUtil = new JwtUtil();
//Returns all checkings accounts held by the account holder associated with the JWT Token
             return SavingAccounts(CDholders ,jwtUtil);
         }
        /* POST /CDOfferings
         Creates a new CD Offering
         Only administrator can call this
         GET /CDOfferings
         Returns all CD Offerings
         Account Holder and Administrator can call this*/
        @PostMapping(value = "Me/CDOfferings")
        @ResponseStatus(HttpStatus.CREATED)
       private List<AccountHolder> CDOffering(List<SavingsAccount> cdOfferings){
            JwtUtil   jwtUtil = new JwtUtil();

            return CDOffering(cdOfferings);
        }

        @ GetMapping (value = "Me/CDOfferings")
        @ResponseStatus(HttpStatus.OK)
         protected List<AccountHolder> CDOffering(List<SavingsAccount> cdOfferings, JwtUtil jwtUtil){
             jwtUtil = new JwtUtil();
//Returns all CDOff held by the account holder associated with the JWT Token
             return CDOffering(cdOfferings ,jwtUtil);
         }

     }
