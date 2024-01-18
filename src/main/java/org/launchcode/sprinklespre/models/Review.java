package org.launchcode.sprinklespre.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Review extends AbstractEntity {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Comment is required")
    @Column(columnDefinition = "TEXT")
    private String comment;

    @NotNull(message = "Rating is required")
    private Integer rating;

    public Review() {

    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }
}