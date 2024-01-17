package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.*;
import uz.dynamic.techinventory.domain.Projector;
import uz.dynamic.techinventory.domain.ProjectorType;
import uz.dynamic.techinventory.service.dto.ProjectorDTO;
import uz.dynamic.techinventory.service.dto.ProjectorTypeDTO;

/**
 * Mapper for the entity {@link Projector} and its DTO {@link ProjectorDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProjectorMapper extends EntityMapper<ProjectorDTO, Projector> {
    @Mapping(target = "projectorType", source = "projectorType", qualifiedByName = "projectorTypeId")
    ProjectorDTO toDto(Projector s);

    @Named("projectorTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProjectorTypeDTO toDtoProjectorTypeId(ProjectorType projectorType);
}
