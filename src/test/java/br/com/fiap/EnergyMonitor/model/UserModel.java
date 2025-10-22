package br.com.fiap.EnergyMonitor.model;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class UserModel {
    @Expose(serialize = false)
    private int id;
    @Expose
    private String name;
    @Expose
    private String email;
    @Expose
    private String password;
    @Expose
    private UserRoles role;
}
