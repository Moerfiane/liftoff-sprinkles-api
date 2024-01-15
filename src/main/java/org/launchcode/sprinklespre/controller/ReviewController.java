package org.launchcode.sprinklespre.controller;

import org.launchcode.sprinklespre.models.Review;
import org.launchcode.sprinklespre.models.data.ReviewRepository;



public class ReviewController {
    private Review review;

    private ReviewRepository reviewRepository;


    public ReviewController(Review review, ReviewRepository reviewRepository) {
        this.review = review;
        this.reviewRepository = reviewRepository;

    }

}

