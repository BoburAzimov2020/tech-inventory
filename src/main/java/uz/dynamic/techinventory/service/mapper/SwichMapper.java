package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.*;
import uz.dynamic.techinventory.domain.Obyekt;
import uz.dynamic.techinventory.domain.Swich;
import uz.dynamic.techinventory.domain.SwichType;
import uz.dynamic.techinventory.service.dto.ObyektDTO;
import uz.dynamic.techinventory.service.dto.SwichDTO;
import uz.dynamic.techinventory.service.dto.SwichTypeDTO;

/**
 * Mapper for the entity {@link Swich} and its DTO {@link SwichDTO}.
 */
@Mapper(componentModel = "spring")
public interface SwichMapper extends EntityMapper<SwichDTO, Swich> {

    @Mapping(target = "swichType", source = "swichType", qualifiedByName = "swichTypeId")
    @Mapping(target = "obyekt", source = "obyekt", qualifiedByName = "obyektId")
    SwichDTO toDto(Swich s);

    @Named("swichTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SwichTypeDTO toDtoSwichTypeId(SwichType swichType);

    @Named("obyektId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ObyektDTO toDtoObyektId(Obyekt obyekt);

}
