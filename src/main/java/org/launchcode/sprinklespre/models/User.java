package org.launchcode.sprinklespre.models;

import jakarta.persistence.Entity;
import java.util.Objects;

@Entity
public class User extends AbstractEntity {

    private String name;
    private int enrolled;

    public User(String name, int enrolled) {
        this.name = name;
        this.enrolled = enrolled;
    }

    public User() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(int enrolled) {
        this.enrolled = enrolled;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", enrolled=" + enrolled +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return enrolled == user.enrolled && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, enrolled);
    }
}