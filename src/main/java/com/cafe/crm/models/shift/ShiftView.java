package com.cafe.crm.models.shift;


import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.worker.Worker;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class ShiftView {

    private Set<Worker> allActiveWorker;

    private Set<Client> clients;

    private List<Calculate> activeCalculate;

    private Set<Calculate> allCalculate;

    private Double cashBox;

    private Double totalCashBox;

    private Long salaryWorker;

    private Double card;

    private Double allPrice;

    private LocalDate shiftDate;

    private Double otherCosts;

    private Double bankCashBox;

    public ShiftView() {
    }

    public ShiftView(Set<Worker> allActiveWorker, Set<Client> clients, List<Calculate> activeCalculate,
                     Set<Calculate> allCalculate, Double cashBox, Double totalCashBox, Long salaryWorker,
                     Double card, Double allPrice, LocalDate shiftDate, Double otherCosts, Double bankCashBox) {
        this.allActiveWorker = allActiveWorker;
        this.clients = clients;
        this.activeCalculate = activeCalculate;
        this.allCalculate = allCalculate;
        this.cashBox = cashBox;
        this.totalCashBox = totalCashBox;
        this.salaryWorker = salaryWorker;
        this.card = card;
        this.allPrice = allPrice;
        this.shiftDate = shiftDate;
        this.otherCosts = otherCosts;
        this.bankCashBox = bankCashBox;
    }

    public Set<Worker> getAllActiveWorker() {
        return allActiveWorker;
    }

    public void setAllActiveWorker(Set<Worker> allActiveWorker) {
        this.allActiveWorker = allActiveWorker;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

    public List<Calculate> getActiveCalculate() {
        return activeCalculate;
    }

    public void setActiveCalculate(List<Calculate> activeCalculate) {
        this.activeCalculate = activeCalculate;
    }

    public Set<Calculate> getAllCalculate() {
        return allCalculate;
    }

    public void setAllCalculate(Set<Calculate> allCalculate) {
        this.allCalculate = allCalculate;
    }

    public Double getCashBox() {
        return cashBox;
    }

    public void setCashBox(Double cashBox) {
        this.cashBox = cashBox;
    }

    public Double getTotalCashBox() {
        return totalCashBox;
    }

    public void setTotalCashBox(Double totalCashBox) {
        this.totalCashBox = totalCashBox;
    }

    public Long getSalaryWorker() {
        return salaryWorker;
    }

    public void setSalaryWorker(Long salaryWorker) {
        this.salaryWorker = salaryWorker;
    }

    public Double getCard() {
        return card;
    }

    public void setCard(Double card) {
        this.card = card;
    }

    public Double getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(Double allPrice) {
        this.allPrice = allPrice;
    }

    public LocalDate getShiftDate() {
        return shiftDate;
    }

    public void setShiftDate(LocalDate shiftDate) {
        this.shiftDate = shiftDate;
    }

    public Double getOtherCosts() {
        return otherCosts;
    }

    public void setOtherCosts(Double otherCosts) {
        this.otherCosts = otherCosts;
    }

    public Double getBankCashBox() {
        return bankCashBox;
    }

    public void setBankCashBox(Double bankCashBox) {
        this.bankCashBox = bankCashBox;
    }
}
