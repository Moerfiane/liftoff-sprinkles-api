package org.launchcode.sprinklespre.models.data;

import org.launchcode.sprinklespre.models.User;
import org.springframework.data.repository.CrudRepository;

public interface
UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);

}