package fr.diginamic.hello.dtos;

import fr.diginamic.hello.models.Ville;

public interface IVilleMapper {
    VilleDto toDto(Ville ville);

    Ville toBean(VilleDto dto);
}
