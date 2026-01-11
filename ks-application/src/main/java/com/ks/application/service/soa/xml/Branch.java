package com.ks.application.service.soa.xml;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
class Branch {

    @XmlAttribute(name = "BranchAddress")
    private String branchAddress;

    @XmlAttribute(name = "BranchCode")
    private String branchCode;

    @XmlAttribute(name = "BranchFax")
    private String branchFax;

    @XmlAttribute(name = "BranchName")
    private String branchName;

    @XmlAttribute(name = "BranchPhone")
    private String branchPhone;

    @XmlAttribute(name = "ZoneCode")
    private String zoneCode;

    @XmlAttribute(name = "ZoneName")
    private String zoneName;

    @XmlElement(name = "Account")
    private List<Account> accounts;

    // Getters and Setters
    public String getBranchAddress() { return branchAddress; }
    public void setBranchAddress(String branchAddress) { this.branchAddress = branchAddress; }

    public String getBranchCode() { return branchCode; }
    public void setBranchCode(String branchCode) { this.branchCode = branchCode; }

    public String getBranchFax() { return branchFax; }
    public void setBranchFax(String branchFax) { this.branchFax = branchFax; }

    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }

    public String getBranchPhone() { return branchPhone; }
    public void setBranchPhone(String branchPhone) { this.branchPhone = branchPhone; }

    public String getZoneCode() { return zoneCode; }
    public void setZoneCode(String zoneCode) { this.zoneCode = zoneCode; }

    public String getZoneName() { return zoneName; }
    public void setZoneName(String zoneName) { this.zoneName = zoneName; }

    public List<Account> getAccounts() { return accounts; }
    public void setAccounts(List<Account> accounts) { this.accounts = accounts; }
}
