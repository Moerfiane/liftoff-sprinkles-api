package org.launchcode.sprinklespre.models;

import jakarta.persistence.Entity;
import java.util.Objects;

@Entity
public class Course extends AbstractEntity {

    private String title;

    private int enrolled;

    public Course(String title, int enrolled) {
        this.title = title;
        this.enrolled = enrolled;
    }

    public Course() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(int enrolled) {
        this.enrolled = enrolled;
    }

    @Override
    public String toString() {
        return "Course{" +
                "title='" + title + '\'' +
                ", enrolled=" + enrolled +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return enrolled == course.enrolled && Objects.equals(title, course.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, enrolled);
    }

    public void enrollStudent() {
        this.enrolled++;
    }
}