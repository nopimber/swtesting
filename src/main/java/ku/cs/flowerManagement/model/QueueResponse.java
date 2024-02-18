package ku.cs.flowerManagement.model;

import ku.cs.flowerManagement.common.FieldStatus;
import lombok.Data;

@Data
public class QueueResponse {
    private long id;

    private int plantedAmount;

    private int fieldNeeded;

    private FieldStatus status;

    private String fieldName;

    private long OrderNumber;

    private String flowerName;

    private int flowerAmount;
}
