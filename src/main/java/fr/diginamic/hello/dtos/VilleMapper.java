package fr.diginamic.hello.dtos;

import fr.diginamic.hello.models.Departement;
import fr.diginamic.hello.models.Ville;
import fr.diginamic.hello.repos.DepartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VilleMapper implements IVilleMapper {

    @Autowired
    private DepartementRepository departementRepository;

    @Override
    public VilleDto toDto(Ville ville) {
        VilleDto dto = new VilleDto();
        dto.setNom(ville.getNom());
        dto.setNbHabitants(ville.getNbHabitants());
        dto.setCodeDepartement(ville.getDepartement().getCode());
        dto.setNomDepartement(ville.getDepartement().getNom());
        return dto;
    }

    @Override
    public Ville toBean(VilleDto dto) {
        Ville ville = new Ville();
        ville.setNom(dto.getNom());
        ville.setNbHabitants(dto.getNbHabitants());

        Departement departement = departementRepository.findByCode(dto.getCodeDepartement())
                .orElseThrow(() -> new IllegalArgumentException("Code départemenent invalide : " + dto.getCodeDepartement()));
        ville.setDepartement(departement);

        return ville;
    }
}
