package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.*;
import uz.dynamic.techinventory.domain.Obyekt;
import uz.dynamic.techinventory.domain.SvitaforDetector;
import uz.dynamic.techinventory.service.dto.ObyektDTO;
import uz.dynamic.techinventory.service.dto.SvitaforDetectorDTO;

/**
 * Mapper for the entity {@link SvitaforDetector} and its DTO {@link SvitaforDetectorDTO}.
 */
@Mapper(componentModel = "spring")
public interface SvitaforDetectorMapper extends EntityMapper<SvitaforDetectorDTO, SvitaforDetector> {
    @Mapping(target = "obyekt", source = "obyekt", qualifiedByName = "obyektId")
    SvitaforDetectorDTO toDto(SvitaforDetector s);

    @Named("obyektId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ObyektDTO toDtoObyektId(Obyekt obyekt);
}
