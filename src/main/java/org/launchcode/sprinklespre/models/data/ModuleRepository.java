package org.launchcode.sprinklespre.models.data;

import org.springframework.data.repository.CrudRepository;
import org.launchcode.sprinklespre.models.Module;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository  extends CrudRepository<Module, Integer> {

}
