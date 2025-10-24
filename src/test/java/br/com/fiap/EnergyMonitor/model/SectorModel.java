package br.com.fiap.EnergyMonitor.model;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class SectorModel {
    @Expose(serialize = false)
    private int id;
    @Expose
    private String name;
    @Expose
    private Double consumptionLimit;
    @Expose
    private User user;
}
