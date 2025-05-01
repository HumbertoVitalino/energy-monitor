package br.com.fiap.EnergyMonitor.mappers;

import br.com.fiap.EnergyMonitor.dto.sector.CreateSectorDto;
import br.com.fiap.EnergyMonitor.dto.sector.SectorOutputDto;
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

    public static SectorOutputDto toDTO(Sector sector) {
        return new SectorOutputDto(sector);
    }

    public static List<SectorOutputDto> toDTOList(List<Sector> sectors) {
        return sectors.stream()
                .map(SectorMapper::toDTO)
                .toList();
    }
}

