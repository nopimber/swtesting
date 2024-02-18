package ku.cs.flowerManagement.repository;

import ku.cs.flowerManagement.entity.OrderFlower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderFlower, Integer> {

    @Query("SELECT o from OrderFlower o where o.status != 'CANCELED'")
    List<OrderFlower> findAllByStatusIsNotCanceled();

    @Query("select max(o.orderNumber) from OrderFlower o")
    Long findLatestOrderNumber();

}

