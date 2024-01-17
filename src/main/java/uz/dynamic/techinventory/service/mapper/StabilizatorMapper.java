package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.*;
import uz.dynamic.techinventory.domain.Obyekt;
import uz.dynamic.techinventory.domain.Stabilizator;
import uz.dynamic.techinventory.service.dto.ObyektDTO;
import uz.dynamic.techinventory.service.dto.StabilizatorDTO;

/**
 * Mapper for the entity {@link Stabilizator} and its DTO {@link StabilizatorDTO}.
 */
@Mapper(componentModel = "spring")
public interface StabilizatorMapper extends EntityMapper<StabilizatorDTO, Stabilizator> {
    @Mapping(target = "obyekt", source = "obyekt", qualifiedByName = "obyektId")
    StabilizatorDTO toDto(Stabilizator s);

    @Named("obyektId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ObyektDTO toDtoObyektId(Obyekt obyekt);
}
