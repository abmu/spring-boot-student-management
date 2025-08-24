package uk.ac.ucl.comp0010.repository;

import org.springframework.data.repository.CrudRepository;
import uk.ac.ucl.comp0010.model.Module;

/**
 * Module interface for managing {@link Module} entities.
 */
public interface ModuleRepository extends CrudRepository<Module, String> {

}
