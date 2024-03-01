package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.Mapper;
import uz.dynamic.techinventory.domain.ShelfType;
import uz.dynamic.techinventory.service.dto.ShelfTypeDTO;

/**
 * Mapper for the entity {@link ShelfType} and its DTO {@link ShelfTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ShelfTypeMapper extends EntityMapper<ShelfTypeDTO, ShelfType> {

    ShelfTypeDTO toDto(ShelfType s);

}
