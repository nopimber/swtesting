package ku.cs.flowerManagement.model;

import ku.cs.flowerManagement.common.OrderMethods;
import ku.cs.flowerManagement.common.PlantStatus;
import ku.cs.flowerManagement.common.Status;
import lombok.Data;

@Data
public class OrderFlowerRequest {

    private int FID;
    private int OID;
    private String FName;
    private double flowerPrice;
    private int orderQuantity;
    private Status status;
    private OrderMethods order_method;
    private PlantStatus plant_status;
    private double price;
    private int orderNumber;

}
