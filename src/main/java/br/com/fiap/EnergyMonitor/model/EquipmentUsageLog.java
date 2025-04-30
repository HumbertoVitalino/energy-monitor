package br.com.fiap.EnergyMonitor.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_equipment_usage_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EquipmentUsageLog {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "EQUIPMENT_LOG_SEQ"
    )
    @SequenceGenerator(
            name = "EQUIPMENT_LOG_SEQ",
            sequenceName = "EQUIPMENT_LOG_SEQ",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    private Double estimatedConsumption;
}
