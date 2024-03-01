package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.Mapper;
import uz.dynamic.techinventory.domain.SwichType;
import uz.dynamic.techinventory.service.dto.SwichTypeDTO;

/**
 * Mapper for the entity {@link SwichType} and its DTO {@link SwichTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface SwichTypeMapper extends EntityMapper<SwichTypeDTO, SwichType> {

    SwichTypeDTO toDto(SwichType s);

}
