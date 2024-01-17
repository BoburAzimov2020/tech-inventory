package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.*;
import uz.dynamic.techinventory.domain.Stoyka;
import uz.dynamic.techinventory.domain.StoykaType;
import uz.dynamic.techinventory.service.dto.StoykaDTO;
import uz.dynamic.techinventory.service.dto.StoykaTypeDTO;

/**
 * Mapper for the entity {@link Stoyka} and its DTO {@link StoykaDTO}.
 */
@Mapper(componentModel = "spring")
public interface StoykaMapper extends EntityMapper<StoykaDTO, Stoyka> {
    @Mapping(target = "stoykaType", source = "stoykaType", qualifiedByName = "stoykaTypeId")
    StoykaDTO toDto(Stoyka s);

    @Named("stoykaTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StoykaTypeDTO toDtoStoykaTypeId(StoykaType stoykaType);
}
