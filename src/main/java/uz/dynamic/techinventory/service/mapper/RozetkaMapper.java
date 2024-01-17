package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.*;
import uz.dynamic.techinventory.domain.Obyekt;
import uz.dynamic.techinventory.domain.Rozetka;
import uz.dynamic.techinventory.service.dto.ObyektDTO;
import uz.dynamic.techinventory.service.dto.RozetkaDTO;

/**
 * Mapper for the entity {@link Rozetka} and its DTO {@link RozetkaDTO}.
 */
@Mapper(componentModel = "spring")
public interface RozetkaMapper extends EntityMapper<RozetkaDTO, Rozetka> {
    @Mapping(target = "obyekt", source = "obyekt", qualifiedByName = "obyektId")
    RozetkaDTO toDto(Rozetka s);

    @Named("obyektId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ObyektDTO toDtoObyektId(Obyekt obyekt);
}
