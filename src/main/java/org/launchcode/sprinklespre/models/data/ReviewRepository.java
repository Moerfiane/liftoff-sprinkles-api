package org.launchcode.sprinklespre.models.data;


import org.launchcode.sprinklespre.models.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ReviewRepository extends CrudRepository<Review, Integer> {


    Optional<Review> findByName(String name);
}
