package ku.cs.flowerManagement.model;

import ku.cs.flowerManagement.common.OrderMethods;
import ku.cs.flowerManagement.common.Status;
import lombok.Data;

@Data
public class OrderFlowerResponse {
    private Long oderNumber;
    private String flowerName;
    private int flowerAmount;
    private OrderMethods orderMethods;
    private Status status;
}
