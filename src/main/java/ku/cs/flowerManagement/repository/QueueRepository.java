package ku.cs.flowerManagement.repository;

import ku.cs.flowerManagement.entity.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QueueRepository extends JpaRepository<Queue, Long> {

    @Query("select q from Queue q where q.id = (select min(q2.id) from Queue q2 where q2.softDelete = false )")
    Queue findMinQueue();

    List<Queue> findAllBySoftDeleteIsFalse();
}
