package br.com.fiap.EnergyMonitor.mappers;

import br.com.fiap.EnergyMonitor.dto.equipment.CreateEquipmentDto;
import br.com.fiap.EnergyMonitor.model.Equipment;
import br.com.fiap.EnergyMonitor.model.Sector;
import br.com.fiap.EnergyMonitor.model.User;

public class EquipmentMapper {

    public static Equipment toEntity(CreateEquipmentDto dto, User user, Sector sector) {
        Equipment eq = new Equipment();
        eq.setName(dto.name());
        eq.setConsumptionPerHour(dto.consumptionPerHour());
        eq.setMaxActiveHours(dto.maxActiveHours());
        eq.setSector(sector);
        eq.setUser(user);
        eq.setActive(false);
        eq.setLastActivatedAt(null);
        return eq;
    }
}
