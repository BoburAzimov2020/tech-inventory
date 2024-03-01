package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.Mapper;
import uz.dynamic.techinventory.domain.CameraType;
import uz.dynamic.techinventory.service.dto.CameraTypeDTO;

/**
 * Mapper for the entity {@link CameraType} and its DTO {@link CameraTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface CameraTypeMapper extends EntityMapper<CameraTypeDTO, CameraType> {

    CameraTypeDTO toDto(CameraType s);

}
