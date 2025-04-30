package br.com.fiap.EnergyMonitor.mappers;

import br.com.fiap.EnergyMonitor.dto.CreateSectorDto;
import br.com.fiap.EnergyMonitor.dto.SectorDto;
import br.com.fiap.EnergyMonitor.model.Sector;
import br.com.fiap.EnergyMonitor.model.User;

import java.util.List;

public class SectorMapper {

    public static Sector MapToEntity(CreateSectorDto dto, User user) {
        Sector sector = new Sector();
        sector.setName(dto.name());
        sector.setConsumptionLimit(dto.consumptionLimit());
        sector.setUser(user);
        return sector;
    }

    public static SectorDto toDTO(Sector sector) {
        return new SectorDto(
                sector.getId(),
                sector.getName(),
                sector.getConsumptionLimit()
        );
    }

    public static List<SectorDto> toDTOList(List<Sector> sectors) {
        return sectors.stream()
                .map(SectorMapper::toDTO)
                .toList();
    }
}
