package uk.ac.ucl.comp0010.repository;

import org.springframework.data.repository.CrudRepository;
import uk.ac.ucl.comp0010.model.Grade;

/**
 * Grade interface for managing {@link Grade} entities.
 */
public interface GradeRepository extends CrudRepository<Grade, Long> {
  void deleteByStudentId(long studentId);

}
