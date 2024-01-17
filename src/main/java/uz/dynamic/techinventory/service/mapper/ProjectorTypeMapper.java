package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.*;
import uz.dynamic.techinventory.domain.Obyekt;
import uz.dynamic.techinventory.domain.ProjectorType;
import uz.dynamic.techinventory.service.dto.ObyektDTO;
import uz.dynamic.techinventory.service.dto.ProjectorTypeDTO;

/**
 * Mapper for the entity {@link ProjectorType} and its DTO {@link ProjectorTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProjectorTypeMapper extends EntityMapper<ProjectorTypeDTO, ProjectorType> {
    @Mapping(target = "obyekt", source = "obyekt", qualifiedByName = "obyektId")
    ProjectorTypeDTO toDto(ProjectorType s);

    @Named("obyektId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ObyektDTO toDtoObyektId(Obyekt obyekt);
}
