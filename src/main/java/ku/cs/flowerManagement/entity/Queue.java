package ku.cs.flowerManagement.entity;

import jakarta.persistence.*;
import ku.cs.flowerManagement.common.FieldStatus;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "queue")
public class Queue {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private int plantedAmount;

    private int fieldNeeded;

    private FieldStatus status;

    @Column(columnDefinition = "bit default 0 ")
    private boolean softDelete;

    @OneToOne
    @JoinColumn(name = "oid")
    private OrderFlower orderFlower;

    @OneToMany(mappedBy = "queue",cascade = CascadeType.ALL,fetch=FetchType.LAZY)
    private Set<Field> fields;
}
