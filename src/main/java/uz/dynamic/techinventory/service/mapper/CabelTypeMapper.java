package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.*;
import uz.dynamic.techinventory.domain.CabelType;
import uz.dynamic.techinventory.domain.Obyekt;
import uz.dynamic.techinventory.service.dto.CabelTypeDTO;
import uz.dynamic.techinventory.service.dto.ObyektDTO;

/**
 * Mapper for the entity {@link CabelType} and its DTO {@link CabelTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface CabelTypeMapper extends EntityMapper<CabelTypeDTO, CabelType> {
    @Mapping(target = "obyekt", source = "obyekt", qualifiedByName = "obyektId")
    CabelTypeDTO toDto(CabelType s);

    @Named("obyektId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ObyektDTO toDtoObyektId(Obyekt obyekt);
}
