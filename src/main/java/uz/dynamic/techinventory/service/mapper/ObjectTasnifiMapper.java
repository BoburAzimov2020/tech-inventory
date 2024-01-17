package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.*;
import uz.dynamic.techinventory.domain.ObjectTasnifi;
import uz.dynamic.techinventory.domain.ObjectTasnifiTuri;
import uz.dynamic.techinventory.service.dto.ObjectTasnifiDTO;
import uz.dynamic.techinventory.service.dto.ObjectTasnifiTuriDTO;

/**
 * Mapper for the entity {@link ObjectTasnifi} and its DTO {@link ObjectTasnifiDTO}.
 */
@Mapper(componentModel = "spring")
public interface ObjectTasnifiMapper extends EntityMapper<ObjectTasnifiDTO, ObjectTasnifi> {
    @Mapping(target = "bjectTasnifiTuri", source = "bjectTasnifiTuri", qualifiedByName = "objectTasnifiTuriId")
    ObjectTasnifiDTO toDto(ObjectTasnifi s);

    @Named("objectTasnifiTuriId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ObjectTasnifiTuriDTO toDtoObjectTasnifiTuriId(ObjectTasnifiTuri objectTasnifiTuri);
}
