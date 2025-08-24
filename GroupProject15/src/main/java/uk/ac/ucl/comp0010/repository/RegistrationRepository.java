package uk.ac.ucl.comp0010.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import uk.ac.ucl.comp0010.model.Registration;

/**
 * Registration interface for managing {@link Registration} entities.
 */
public interface RegistrationRepository extends CrudRepository<Registration, Long> {
  void deleteByStudentId(Long studentId);
}
