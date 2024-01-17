package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.*;
import uz.dynamic.techinventory.domain.CameraType;
import uz.dynamic.techinventory.domain.Obyekt;
import uz.dynamic.techinventory.service.dto.CameraTypeDTO;
import uz.dynamic.techinventory.service.dto.ObyektDTO;

/**
 * Mapper for the entity {@link CameraType} and its DTO {@link CameraTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface CameraTypeMapper extends EntityMapper<CameraTypeDTO, CameraType> {
    @Mapping(target = "obyekt", source = "obyekt", qualifiedByName = "obyektId")
    CameraTypeDTO toDto(CameraType s);

    @Named("obyektId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ObyektDTO toDtoObyektId(Obyekt obyekt);
}
