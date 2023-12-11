package org.launchcode.sprinklespre.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;

import java.util.Objects;

@Entity
public class UserEnroll extends AbstractEntity {

    private String name;
    private boolean enrolled;

    public UserEnroll(String name, boolean enrolled) {
        this.name = name;
        this.enrolled = enrolled;
    }

    public UserEnroll() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(boolean enrolled) {
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
        UserEnroll user = (UserEnroll) o;
        return enrolled == user.enrolled && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, enrolled);
    }
}