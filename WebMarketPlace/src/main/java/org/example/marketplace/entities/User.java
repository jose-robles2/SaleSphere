package org.example.marketplace.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "users") // Change the table name to "users" to bypass naming clash error with "user"
public class User {
    private String firstName;

    private String lastName;

    private String userName;

    private int age;

    private double balance;
    @ManyToOne
    private State state;

    @Id
    @GeneratedValue
    private Long id;

    public User() {}

    public User(String firstName, String lastName, String userName, int age,State state, double balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.age = age;
        this.state = state;
        this.balance = balance;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public int getAge() { return age; }

    public State getState() { return state; }

    public double getBalance() { return balance; }

    public Long getId() {
        return id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAge(int age) { this.age = age; }

    public void setState(State state) {
        this.state = state;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(userName, user.userName) && Objects.equals(state, user.state) && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", state='" + state + '\'' +
                ", id=" + id +
                '}';
    }
}
