package com.cafe.crm.models.shift;


import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.goods.Goods;
import com.cafe.crm.models.worker.Worker;

import java.util.Set;

public class TotalStatisticView {

    private Double profit;

    private Double costSalary;

    private Double otherCosts;

    private Set<Worker> workers;

    private Set<Client> clients;

    private Set<Goods> salaryGoods;

    private Set<Goods> otherGoods;

    public TotalStatisticView() {
    }

    public TotalStatisticView(Double profit, Double costSalary, Double otherCosts, Set<Worker> workers,
                              Set<Client> clients, Set<Goods> salaryGoods, Set<Goods> otherGoods) {
        this.profit = profit;
        this.costSalary = costSalary;
        this.otherCosts = otherCosts;
        this.workers = workers;
        this.clients = clients;
        this.salaryGoods = salaryGoods;
        this.otherGoods = otherGoods;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Double getCostSalary() {
        return costSalary;
    }

    public void setCostSalary(Double costSalary) {
        this.costSalary = costSalary;
    }

    public Double getOtherCosts() {
        return otherCosts;
    }

    public void setOtherCosts(Double otherCosts) {
        this.otherCosts = otherCosts;
    }

    public Set<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(Set<Worker> workers) {
        this.workers = workers;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

    public Set<Goods> getSalaryGoods() {
        return salaryGoods;
    }

    public void setSalaryGoods(Set<Goods> salaryGoods) {
        this.salaryGoods = salaryGoods;
    }

    public Set<Goods> getOtherGoods() {
        return otherGoods;
    }

    public void setOtherGoods(Set<Goods> otherGoods) {
        this.otherGoods = otherGoods;
    }
}
