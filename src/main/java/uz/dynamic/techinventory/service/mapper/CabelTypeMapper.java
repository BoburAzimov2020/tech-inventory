package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.Mapper;
import uz.dynamic.techinventory.domain.CabelType;
import uz.dynamic.techinventory.service.dto.CabelTypeDTO;

/**
 * Mapper for the entity {@link CabelType} and its DTO {@link CabelTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface CabelTypeMapper extends EntityMapper<CabelTypeDTO, CabelType> {

    CabelTypeDTO toDto(CabelType s);

}
