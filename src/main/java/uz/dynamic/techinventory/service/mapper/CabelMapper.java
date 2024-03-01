package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.*;
import uz.dynamic.techinventory.domain.Cabel;
import uz.dynamic.techinventory.domain.CabelType;
import uz.dynamic.techinventory.domain.Obyekt;
import uz.dynamic.techinventory.service.dto.CabelDTO;
import uz.dynamic.techinventory.service.dto.CabelTypeDTO;
import uz.dynamic.techinventory.service.dto.ObyektDTO;

/**
 * Mapper for the entity {@link Cabel} and its DTO {@link CabelDTO}.
 */
@Mapper(componentModel = "spring")
public interface CabelMapper extends EntityMapper<CabelDTO, Cabel> {

    @Mapping(target = "cabelType", source = "cabelType", qualifiedByName = "cabelTypeId")
    @Mapping(target = "obyekt", source = "obyekt", qualifiedByName = "obyektId")
    CabelDTO toDto(Cabel s);

    @Named("cabelTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CabelTypeDTO toDtoCabelTypeId(CabelType cabelType);

    @Named("obyektId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ObyektDTO toDtoObyektId(Obyekt obyekt);

}
