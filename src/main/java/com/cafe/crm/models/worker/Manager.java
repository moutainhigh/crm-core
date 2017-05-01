package com.cafe.crm.models.worker;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name = "manager")
@PrimaryKeyJoinColumn(name = "worker_id")
public class Manager extends Worker {


    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    public Manager() {
        super();
    }

    public Manager(String firstName, String lastName, String position, Long shiftSalary, String login, String password) {
        super(firstName, lastName, position, shiftSalary);
        this.login = login;
        this.password = password;
    }

    public Manager(Long id, String firstName, String lastName, String login, String password, String position,
                   Long shiftSalary) {
        super(id, firstName, lastName, position, shiftSalary);
        this.login = login;
        this.password = password;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
