package org.example.marketplace.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "users") // Change the table name to "users" to bypass naming clash error with "user"
public class User {
    private String firstName;

    private String lastName;

    private String userName;

    private Integer age;

    @OneToOne
    private State state;

    @Id
    @GeneratedValue
    private Long id;

    public User() {}

    public User(String firstName, String lastName, String userName, Integer age,State state) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.age = age;
        this.state = state;
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

    public Integer getAge() { return age; }

    public State getState() { return state; }

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

    public void setAge(Integer age) { this.age = age; }

    public void setState(State state) {
        this.state = state;
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
