package fr.diginamic.hello.dtos;

import fr.diginamic.hello.models.Departement;
import fr.diginamic.hello.models.Ville;
import org.springframework.stereotype.Component;

@Component
public class DepartementMapper implements IDepartementMapper {

    @Override
    public DepartementDto toDto(Departement dpt) {
        DepartementDto dto = new DepartementDto();
        dto.setCode(dpt.getCode());
        dto.setNom(dpt.getNom());

        int totalHabitants = dpt.getVilles().stream()
                .mapToInt(Ville::getNbHabitants)
                .sum();
        dto.setNbHabitants(totalHabitants);

        return dto;
    }

    @Override
    public Departement toBean(DepartementDto dto) {
        Departement dpt = new Departement();
        dpt.setCode(dto.getCode());
        dpt.setNom(dto.getNom());
        return dpt;
    }
}
