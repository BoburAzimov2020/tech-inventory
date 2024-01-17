package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.*;
import uz.dynamic.techinventory.domain.Obyekt;
import uz.dynamic.techinventory.domain.StoykaType;
import uz.dynamic.techinventory.service.dto.ObyektDTO;
import uz.dynamic.techinventory.service.dto.StoykaTypeDTO;

/**
 * Mapper for the entity {@link StoykaType} and its DTO {@link StoykaTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface StoykaTypeMapper extends EntityMapper<StoykaTypeDTO, StoykaType> {
    @Mapping(target = "obyekt", source = "obyekt", qualifiedByName = "obyektId")
    StoykaTypeDTO toDto(StoykaType s);

    @Named("obyektId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ObyektDTO toDtoObyektId(Obyekt obyekt);
}
