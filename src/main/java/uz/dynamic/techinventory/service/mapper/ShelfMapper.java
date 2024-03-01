package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.*;
import uz.dynamic.techinventory.domain.Obyekt;
import uz.dynamic.techinventory.domain.Shelf;
import uz.dynamic.techinventory.domain.ShelfType;
import uz.dynamic.techinventory.service.dto.ObyektDTO;
import uz.dynamic.techinventory.service.dto.ShelfDTO;
import uz.dynamic.techinventory.service.dto.ShelfTypeDTO;

/**
 * Mapper for the entity {@link Shelf} and its DTO {@link ShelfDTO}.
 */
@Mapper(componentModel = "spring")
public interface ShelfMapper extends EntityMapper<ShelfDTO, Shelf> {

    @Mapping(target = "shelfType", source = "shelfType", qualifiedByName = "shelfTypeId")
    @Mapping(target = "obyekt", source = "obyekt", qualifiedByName = "obyektId")
    ShelfDTO toDto(Shelf s);

    @Named("shelfTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ShelfTypeDTO toDtoShelfTypeId(ShelfType shelfType);

    @Named("obyektId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ObyektDTO toDtoObyektId(Obyekt obyekt);

}
