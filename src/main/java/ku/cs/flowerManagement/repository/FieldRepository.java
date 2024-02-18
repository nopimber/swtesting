package ku.cs.flowerManagement.repository;

import ku.cs.flowerManagement.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface FieldRepository extends JpaRepository<Field,Long> {

    Set<Field> findAllByIsUsedFalseOrderByFieldNameAsc();
}
