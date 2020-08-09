package com.example.user.model;


import javax.persistence.*;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


   // @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
   // @Column(name = "user_id")
   // private Long mealId;

    @ManyToOne(cascade = CascadeType.ALL)
   // @JoinColumn(name = "user_id")
    @JoinColumn(name = "user_id")
    private User user;

    private String account_number;

    private String account_type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

//    public User getUser() {
//        return user;
//    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    @Override
    public String toString() {
        return "Account [account_number=" + account_number + ", account_type=" + account_type + "]";
    }
}
