package org.launchcode.sprinklespre.models.data;

import org.launchcode.sprinklespre.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollRepository extends CrudRepository<User, Integer> {
}
