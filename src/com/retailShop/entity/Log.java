package com.retailShop.entity;

import javax.persistence.*;
import com.retailShop.page.module.forms.EntityType;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Log implements EntityType {
    private int id;
    private String action;
    private String page;
    private User user;
    private Timestamp dateA;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "action", nullable = true, length = 255)
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Basic
    @Column(name = "date_a", nullable = true)

    public Timestamp getDateA() {
        return dateA;
    }

    public void setDateA(Timestamp dateA) {
        this.dateA = dateA;
    }

    @Basic
    @Column(name = "page", nullable = true, length = 255)
    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Log log = (Log) o;
        return id == log.id && Objects.equals(action, log.action) && Objects.equals(user, log.user) && Objects.equals(dateA, log.dateA);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, action, user, dateA);
    }

    @OneToOne(orphanRemoval = true, cascade=CascadeType.ALL )
    @JoinColumn(name = "user", referencedColumnName = "id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @PrePersist
    protected void onCreate() {
        dateA = new Timestamp(System.currentTimeMillis());
    }
}
