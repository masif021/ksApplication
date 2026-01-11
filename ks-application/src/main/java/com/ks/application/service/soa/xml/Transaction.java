package com.ks.application.service.soa.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
class Transaction {

    @XmlAttribute(name = "Balance")
    private String balance;

    @XmlAttribute(name = "CreditAmount")
    private String creditAmount;

    @XmlAttribute(name = "DebitAmount")
    private String debitAmount;

    @XmlAttribute(name = "PageNo")
    private String pageNo;

    @XmlAttribute(name = "Particular")
    private String particular;

    @XmlAttribute(name = "SequenceNo")
    private String sequenceNo;

    @XmlAttribute(name = "TotalPages")
    private String totalPages;

    @XmlAttribute(name = "TranDate")
    private String tranDate;

    // Getters and Setters
    public String getBalance() { return balance; }
    public void setBalance(String balance) { this.balance = balance; }

    public String getCreditAmount() { return creditAmount; }
    public void setCreditAmount(String creditAmount) { this.creditAmount = creditAmount; }

    public String getDebitAmount() { return debitAmount; }
    public void setDebitAmount(String debitAmount) { this.debitAmount = debitAmount; }

    public String getPageNo() { return pageNo; }
    public void setPageNo(String pageNo) { this.pageNo = pageNo; }

    public String getParticular() { return particular; }
    public void setParticular(String particular) { this.particular = particular; }

    public String getSequenceNo() { return sequenceNo; }
    public void setSequenceNo(String sequenceNo) { this.sequenceNo = sequenceNo; }

    public String getTotalPages() { return totalPages; }
    public void setTotalPages(String totalPages) { this.totalPages = totalPages; }

    public String getTranDate() { return tranDate; }
    public void setTranDate(String tranDate) { this.tranDate = tranDate; }
}
