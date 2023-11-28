package org.launchcode.sprinklespre.models.data;

import org.launchcode.sprinklespre.models.HelloWorld;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends CrudRepository<HelloWorld, Integer> {
}
