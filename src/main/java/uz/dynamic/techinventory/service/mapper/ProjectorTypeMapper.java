package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.Mapper;
import uz.dynamic.techinventory.domain.ProjectorType;
import uz.dynamic.techinventory.service.dto.ProjectorTypeDTO;

/**
 * Mapper for the entity {@link ProjectorType} and its DTO {@link ProjectorTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProjectorTypeMapper extends EntityMapper<ProjectorTypeDTO, ProjectorType> {

    ProjectorTypeDTO toDto(ProjectorType s);

}
