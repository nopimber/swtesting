package ku.cs.flowerManagement.repository;

import ku.cs.flowerManagement.entity.Flower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowerRepository extends JpaRepository<Flower, Integer> {

}

