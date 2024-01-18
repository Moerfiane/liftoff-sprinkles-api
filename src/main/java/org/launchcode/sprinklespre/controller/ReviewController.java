package org.launchcode.sprinklespre.controller;

import org.launchcode.sprinklespre.models.Review;
import org.launchcode.sprinklespre.models.data.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5180", maxAge = 3600, methods = {RequestMethod.POST} )
public class ReviewController {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewController(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    // POST: Create a new review
    @PostMapping("/feedback")
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        Review savedReview = reviewRepository.save(review);
        return ResponseEntity.ok(savedReview);
    }

    // GET: Retrieve all reviews
    @GetMapping("/feedback")
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = (List<Review>) reviewRepository.findAll();
        return ResponseEntity.ok(reviews);
    }

    // PUT: Update a review by name
    @PutMapping("/feedback")
    public ResponseEntity<Review> updateReview(@RequestParam String name, @RequestBody Review reviewDetails) {
        Optional<Review> existingReviewOpt = reviewRepository.findByName(name);
        if (existingReviewOpt.isPresent()) {
            Review existingReview = existingReviewOpt.get();
            existingReview.setComment(reviewDetails.getComment());
            existingReview.setRating(reviewDetails.getRating());
            // Update other fields as necessary
            return ResponseEntity.ok(reviewRepository.save(existingReview));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Delete a review by name
    @DeleteMapping("/feedback")
    public ResponseEntity<?> deleteReview(@RequestParam String name) {
        Optional<Review> reviewOpt = reviewRepository.findByName(name);
        if (reviewOpt.isPresent()) {
            reviewRepository.delete(reviewOpt.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}