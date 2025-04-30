package br.com.fiap.EnergyMonitor.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_equipments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Equipment {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "EQUIPMENT_SEQ"
    )
    @SequenceGenerator(
            name = "EQUIPMENT_SEQ",
            sequenceName = "EQUIPMENT_SEQ",
            allocationSize = 1
    )
    private Long id;

    private String name;

    private boolean active;

    private Double consumptionPerHour;

    private Integer maxActiveHours;

    private LocalDateTime lastActivatedAt;

    @ManyToOne
    @JoinColumn(name = "sector_id", nullable = false)
    private Sector sector;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

