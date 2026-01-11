package com.ks.application.service.soa.xml;

import jakarta.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "Bank_Statement")
@XmlAccessorType(XmlAccessType.FIELD)
public class BankStatement {

    @XmlAttribute(name = "BankName")
    private String bankName;

    @XmlElement(name = "Branch")
    private List<Branch> branches;

    // Getters and Setters
    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }

    public List<Branch> getBranches() { return branches; }
    public void setBranches(List<Branch> branches) { this.branches = branches; }
}





