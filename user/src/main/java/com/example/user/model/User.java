package com.example.user.model;

import com.example.user.DTOs.AccountDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", unique = true, nullable = false)
    private long user_id;
    private String user_name;
    private String email_id;
    private String last_updated;
    private String create_date;
    private String pswd;

  //  @OneToMany(mappedBy ="accounts", cascade = CascadeType.ALL, orphanRemoval = true)
  //  private List<Account> accounts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    private List<Account> accounts;

    public User() {}

    public User(String username, String email) {
        this.user_name = username;
        this.email_id = email;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }
}
