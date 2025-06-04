package fr.diginamic.hello.dtos;

import fr.diginamic.hello.models.Departement;

public interface IDepartementMapper {
    DepartementDto toDto(Departement dpt);

    Departement toBean(DepartementDto dto);
}
