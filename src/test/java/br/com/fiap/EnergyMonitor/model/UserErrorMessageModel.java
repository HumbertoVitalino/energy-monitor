package br.com.fiap.EnergyMonitor.model;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class UserErrorMessageModel {
    @Expose
    private String message;
}
