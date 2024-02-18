package ku.cs.flowerManagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "field")
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String fieldName;

    private Boolean isUsed;

    @EqualsAndHashCode.Exclude

    @ManyToOne()
    @JoinColumn(name="queue_id")
    private Queue queue;
}
