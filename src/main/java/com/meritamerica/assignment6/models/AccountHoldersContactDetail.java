package com.meritamerica.assignment6.models;

import org.springframework.data.annotation.Id;

import javax.persistence.*;

     @Entity
public class AccountHoldersContactDetail extends AccountHolder{
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @OneToOne
    private  String firstName;
    private String middleName;
    private String lastName;
    private  String SSN;

    //@GeneratedValue(GenerationType = AUTO);
    public AccountHoldersContactDetail() {
       // super();
    }

    public AccountHoldersContactDetail(String firstName, String middleName, String lastName, String SSN) {
        super();
    }
    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getMiddleName() {
        return middleName;
    }

    @Override
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getSSN() {
        return SSN;
    }

    @Override
    public void setSSN(String SSN) {
        this.SSN = SSN;
    }


}
