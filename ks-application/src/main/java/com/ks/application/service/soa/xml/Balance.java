package com.ks.application.service.soa.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
class Balance {

    @XmlAttribute(name = "Balance")
    private String balance;

    // Getter and Setter
    public String getBalance() { return balance; }
    public void setBalance(String balance) { this.balance = balance; }
}