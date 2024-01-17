package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.*;
import uz.dynamic.techinventory.domain.Cabel;
import uz.dynamic.techinventory.domain.CabelType;
import uz.dynamic.techinventory.service.dto.CabelDTO;
import uz.dynamic.techinventory.service.dto.CabelTypeDTO;

/**
 * Mapper for the entity {@link Cabel} and its DTO {@link CabelDTO}.
 */
@Mapper(componentModel = "spring")
public interface CabelMapper extends EntityMapper<CabelDTO, Cabel> {
    @Mapping(target = "cabelType", source = "cabelType", qualifiedByName = "cabelTypeId")
    CabelDTO toDto(Cabel s);

    @Named("cabelTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CabelTypeDTO toDtoCabelTypeId(CabelType cabelType);
}
