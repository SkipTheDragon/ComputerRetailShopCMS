package com.retailShop.Entity;

import com.retailShop.Page.Module.Forms.EntityType;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class User implements EntityType {
    @GeneratedValue(strategy=GenerationType.IDENTITY)

    private int id;
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private char[] password;
    private String lastName;
    @NotNull
    private UserRole userRoleByRoleid;
    private UserContact userContactByContact;

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

    @Column(name = "name", nullable = true, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "password", nullable = true, length = 100)
    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    @Basic
    @Column(name = "last_name", nullable = true, length = 50)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name) && Objects.equals(password, user.password) && Objects.equals(lastName, user.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, lastName);
    }

    @ManyToOne
    @JoinColumn(name = "roleid", referencedColumnName = "id")
    public UserRole getUserRoleByRoleid() {
        return userRoleByRoleid;
    }

    public void setUserRoleByRoleid(UserRole userRoleByRoleid) {
        this.userRoleByRoleid = userRoleByRoleid;
    }

    @ManyToOne
    @JoinColumn(name = "contact", referencedColumnName = "id")
    public UserContact getUserContactByContact() {
        return userContactByContact;
    }

    public void setUserContactByContact(UserContact userContactByContact) {
        this.userContactByContact = userContactByContact;
    }


}
