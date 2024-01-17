package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.*;
import uz.dynamic.techinventory.domain.Loyiha;
import uz.dynamic.techinventory.domain.ObjectTasnifi;
import uz.dynamic.techinventory.service.dto.LoyihaDTO;
import uz.dynamic.techinventory.service.dto.ObjectTasnifiDTO;

/**
 * Mapper for the entity {@link Loyiha} and its DTO {@link LoyihaDTO}.
 */
@Mapper(componentModel = "spring")
public interface LoyihaMapper extends EntityMapper<LoyihaDTO, Loyiha> {
    @Mapping(target = "objectTasnifi", source = "objectTasnifi", qualifiedByName = "objectTasnifiId")
    LoyihaDTO toDto(Loyiha s);

    @Named("objectTasnifiId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ObjectTasnifiDTO toDtoObjectTasnifiId(ObjectTasnifi objectTasnifi);
}
