package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.*;
import uz.dynamic.techinventory.domain.Obyekt;
import uz.dynamic.techinventory.domain.Ups;
import uz.dynamic.techinventory.service.dto.ObyektDTO;
import uz.dynamic.techinventory.service.dto.UpsDTO;

/**
 * Mapper for the entity {@link Ups} and its DTO {@link UpsDTO}.
 */
@Mapper(componentModel = "spring")
public interface UpsMapper extends EntityMapper<UpsDTO, Ups> {
    @Mapping(target = "obyekt", source = "obyekt", qualifiedByName = "obyektId")
    UpsDTO toDto(Ups s);

    @Named("obyektId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ObyektDTO toDtoObyektId(Obyekt obyekt);
}
