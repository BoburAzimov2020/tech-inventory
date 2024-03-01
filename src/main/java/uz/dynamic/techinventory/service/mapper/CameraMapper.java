package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.*;
import uz.dynamic.techinventory.domain.Camera;
import uz.dynamic.techinventory.domain.CameraBrand;
import uz.dynamic.techinventory.domain.CameraType;
import uz.dynamic.techinventory.domain.Obyekt;
import uz.dynamic.techinventory.service.dto.CameraBrandDTO;
import uz.dynamic.techinventory.service.dto.CameraDTO;
import uz.dynamic.techinventory.service.dto.CameraTypeDTO;
import uz.dynamic.techinventory.service.dto.ObyektDTO;

/**
 * Mapper for the entity {@link Camera} and its DTO {@link CameraDTO}.
 */
@Mapper(componentModel = "spring")
public interface CameraMapper extends EntityMapper<CameraDTO, Camera> {

    @Mapping(target = "cameraType", source = "cameraType", qualifiedByName = "cameraTypeId")
    @Mapping(target = "cameraBrand", source = "cameraBrand", qualifiedByName = "cameraBrandId")
    @Mapping(target = "obyekt", source = "obyekt", qualifiedByName = "obyektId")
    CameraDTO toDto(Camera s);

    @Named("cameraTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CameraTypeDTO toDtoCameraTypeId(CameraType cameraType);

    @Named("cameraBrandId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CameraBrandDTO toDtoCameraBrandId(CameraBrand cameraBrand);

    @Named("obyektId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ObyektDTO toDtoObyektId(Obyekt obyekt);

}
