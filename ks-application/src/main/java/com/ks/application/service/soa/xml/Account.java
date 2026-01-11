package com.ks.application.service.soa.xml;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Account {

    @XmlAttribute(name = "AccountAddress")
    private String accountAddress;

    @XmlAttribute(name = "AccountNo")
    private String accountNo;

    @XmlAttribute(name = "AccountTitle")
    private String accountTitle;

    @XmlAttribute(name = "AccountType")
    private String accountType;

    @XmlAttribute(name = "CN")
    private String cn;

    @XmlAttribute(name = "CurrencyType")
    private String currencyType;

    @XmlAttribute(name = "IBAN")
    private String iban;

    @XmlAttribute(name = "StatementDateFrom")
    private String statementDateFrom;

    @XmlAttribute(name = "StatementDateTo")
    private String statementDateTo;

    @XmlAttribute(name = "StatementGeneratedOn")
    private String statementGeneratedOn;

    @XmlElement(name = "Transaction")
    private List<Transaction> transactions;

    @XmlElement(name = "OpeningBalance")
    private Balance openingBalance;

    @XmlElement(name = "ClosingBalance")
    private Balance closingBalance;

    // Getters and Setters
    public String getAccountAddress() { return accountAddress; }
    public void setAccountAddress(String accountAddress) { this.accountAddress = accountAddress; }

    public String getAccountNo() { return accountNo; }
    public void setAccountNo(String accountNo) { this.accountNo = accountNo; }

    public String getAccountTitle() { return accountTitle; }
    public void setAccountTitle(String accountTitle) { this.accountTitle = accountTitle; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public String getCn() { return cn; }
    public void setCn(String cn) { this.cn = cn; }

    public String getCurrencyType() { return currencyType; }
    public void setCurrencyType(String currencyType) { this.currencyType = currencyType; }

    public String getIban() { return iban; }
    public void setIban(String iban) { this.iban = iban; }

    public String getStatementDateFrom() { return statementDateFrom; }
    public void setStatementDateFrom(String statementDateFrom) { this.statementDateFrom = statementDateFrom; }

    public String getStatementDateTo() { return statementDateTo; }
    public void setStatementDateTo(String statementDateTo) { this.statementDateTo = statementDateTo; }

    public String getStatementGeneratedOn() { return statementGeneratedOn; }
    public void setStatementGeneratedOn(String statementGeneratedOn) { this.statementGeneratedOn = statementGeneratedOn; }

    public List<Transaction> getTransactions() { return transactions; }
    public void setTransactions(List<Transaction> transactions) { this.transactions = transactions; }

    public Balance getOpeningBalance() { return openingBalance; }
    public void setOpeningBalance(Balance openingBalance) { this.openingBalance = openingBalance; }

    public Balance getClosingBalance() { return closingBalance; }
    public void setClosingBalance(Balance closingBalance) { this.closingBalance = closingBalance; }
}
