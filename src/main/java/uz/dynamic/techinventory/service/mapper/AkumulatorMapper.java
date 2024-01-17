package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.*;
import uz.dynamic.techinventory.domain.Akumulator;
import uz.dynamic.techinventory.domain.Obyekt;
import uz.dynamic.techinventory.service.dto.AkumulatorDTO;
import uz.dynamic.techinventory.service.dto.ObyektDTO;

/**
 * Mapper for the entity {@link Akumulator} and its DTO {@link AkumulatorDTO}.
 */
@Mapper(componentModel = "spring")
public interface AkumulatorMapper extends EntityMapper<AkumulatorDTO, Akumulator> {
    @Mapping(target = "obyekt", source = "obyekt", qualifiedByName = "obyektId")
    AkumulatorDTO toDto(Akumulator s);

    @Named("obyektId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ObyektDTO toDtoObyektId(Obyekt obyekt);
}
