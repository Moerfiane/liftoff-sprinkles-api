package org.launchcode.sprinklespre.models.data;


import org.launchcode.sprinklespre.models.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
}
