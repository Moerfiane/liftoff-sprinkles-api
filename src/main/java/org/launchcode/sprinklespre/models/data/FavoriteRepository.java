package org.launchcode.sprinklespre.models.data;

import org.launchcode.sprinklespre.models.Course;
import org.springframework.data.repository.CrudRepository;

public interface FavoriteRepository extends CrudRepository<Course, Integer> {
}
