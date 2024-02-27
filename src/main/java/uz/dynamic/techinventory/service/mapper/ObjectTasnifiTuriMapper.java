package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.*;
import uz.dynamic.techinventory.domain.District;
import uz.dynamic.techinventory.domain.ObjectTasnifiTuri;
import uz.dynamic.techinventory.service.dto.DistrictDTO;
import uz.dynamic.techinventory.service.dto.ObjectTasnifiTuriDTO;

/**
 * Mapper for the entity {@link ObjectTasnifiTuri} and its DTO {@link ObjectTasnifiTuriDTO}.
 */
@Mapper(componentModel = "spring")
public interface ObjectTasnifiTuriMapper extends EntityMapper<ObjectTasnifiTuriDTO, ObjectTasnifiTuri> {

    ObjectTasnifiTuriDTO toDto(ObjectTasnifiTuri s);

    @Named("districtId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DistrictDTO toDtoDistrictId(District district);
}
