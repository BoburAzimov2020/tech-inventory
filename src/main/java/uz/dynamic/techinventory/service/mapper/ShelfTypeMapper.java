package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.*;
import uz.dynamic.techinventory.domain.Obyekt;
import uz.dynamic.techinventory.domain.ShelfType;
import uz.dynamic.techinventory.service.dto.ObyektDTO;
import uz.dynamic.techinventory.service.dto.ShelfTypeDTO;

/**
 * Mapper for the entity {@link ShelfType} and its DTO {@link ShelfTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ShelfTypeMapper extends EntityMapper<ShelfTypeDTO, ShelfType> {
    @Mapping(target = "obyekt", source = "obyekt", qualifiedByName = "obyektId")
    ShelfTypeDTO toDto(ShelfType s);

    @Named("obyektId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ObyektDTO toDtoObyektId(Obyekt obyekt);
}
