package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.Mapper;
import uz.dynamic.techinventory.domain.StoykaType;
import uz.dynamic.techinventory.service.dto.StoykaTypeDTO;

/**
 * Mapper for the entity {@link StoykaType} and its DTO {@link StoykaTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface StoykaTypeMapper extends EntityMapper<StoykaTypeDTO, StoykaType> {

    StoykaTypeDTO toDto(StoykaType s);

}
