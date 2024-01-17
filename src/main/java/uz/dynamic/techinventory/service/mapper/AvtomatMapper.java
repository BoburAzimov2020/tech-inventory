package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.*;
import uz.dynamic.techinventory.domain.Avtomat;
import uz.dynamic.techinventory.domain.Obyekt;
import uz.dynamic.techinventory.service.dto.AvtomatDTO;
import uz.dynamic.techinventory.service.dto.ObyektDTO;

/**
 * Mapper for the entity {@link Avtomat} and its DTO {@link AvtomatDTO}.
 */
@Mapper(componentModel = "spring")
public interface AvtomatMapper extends EntityMapper<AvtomatDTO, Avtomat> {
    @Mapping(target = "obyekt", source = "obyekt", qualifiedByName = "obyektId")
    AvtomatDTO toDto(Avtomat s);

    @Named("obyektId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ObyektDTO toDtoObyektId(Obyekt obyekt);
}
