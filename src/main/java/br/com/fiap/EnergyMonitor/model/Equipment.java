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

    //Métodos problemáticos do Lombok
    public void setActive(Boolean state) { this.active = state; }

    public void setLastActivatedAt(LocalDateTime localDateTime) { this.lastActivatedAt = localDateTime; }

    public void setName(String name){ this.name = name; }

    public void setConsumptionPerHour(Double consumptionPerHour){ this.consumptionPerHour = consumptionPerHour; }

    public void setMaxActiveHours(Integer maxActiveHours){ this.maxActiveHours = maxActiveHours; }

    public void setSector(Sector sector){ this.sector = sector; }

    public void setUser(User user){ this.user = user; }

    public Long getId(){ return this.id; }

    public String getName(){ return this.name; }

    public User getUser(){ return this.user; }

    public Sector getSector(){ return this.sector; }

    public Boolean isActive(){ return this.active; }

    public LocalDateTime getLastActivatedAt(){ return this.lastActivatedAt; }

    public Double getConsumptionPerHour(){ return this.consumptionPerHour; }

    public Integer getMaxActiveHours(){ return this.maxActiveHours; }
}

