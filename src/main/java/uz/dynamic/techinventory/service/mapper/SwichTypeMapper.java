package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.*;
import uz.dynamic.techinventory.domain.Obyekt;
import uz.dynamic.techinventory.domain.SwichType;
import uz.dynamic.techinventory.service.dto.ObyektDTO;
import uz.dynamic.techinventory.service.dto.SwichTypeDTO;

/**
 * Mapper for the entity {@link SwichType} and its DTO {@link SwichTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface SwichTypeMapper extends EntityMapper<SwichTypeDTO, SwichType> {
    @Mapping(target = "obyekt", source = "obyekt", qualifiedByName = "obyektId")
    SwichTypeDTO toDto(SwichType s);

    @Named("obyektId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ObyektDTO toDtoObyektId(Obyekt obyekt);
}
