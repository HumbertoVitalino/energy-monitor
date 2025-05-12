package br.com.fiap.EnergyMonitor.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_sector")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Sector {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SECTOR_SEQ"
    )
    @SequenceGenerator(
            name = "SECTOR_SEQ",
            sequenceName = "SECTOR_SEQ",
            allocationSize = 1
    )
    private long id;
    private String name;
    private Double consumptionLimit;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //Métodos problemáticos do Lombok
    public Long getId(){ return this.id; }

    public User getUser(){ return this.user; }

    public String getName(){ return this.name; }

    public Double getConsumptionLimit(){ return this.consumptionLimit; }

    public void setName(String name){ this.name = name; }

    public void setConsumptionLimit(Double consumptionLimit){ this.consumptionLimit = consumptionLimit; }

    public void setUser(User user){ this.user = user; }
}
