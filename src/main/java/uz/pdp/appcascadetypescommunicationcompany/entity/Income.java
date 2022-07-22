package uz.pdp.appcascadetypescommunicationcompany.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Income {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private Double amount = 0.0;

    @ManyToOne
    private Simcard simcard;

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    private Timestamp date;

    @ManyToOne
    private ClientMoveType clientMoveType;

    @ManyToOne
    private PaymentType paymentType;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private Timestamp updatedAt;

    @JoinColumn(updatable = false)
    @CreatedBy
    private UUID createdBy;

    @LastModifiedBy
    private UUID updatedBy;


}
