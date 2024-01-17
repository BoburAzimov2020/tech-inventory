package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.*;
import uz.dynamic.techinventory.domain.BuyurtmaRaqam;
import uz.dynamic.techinventory.domain.Loyiha;
import uz.dynamic.techinventory.service.dto.BuyurtmaRaqamDTO;
import uz.dynamic.techinventory.service.dto.LoyihaDTO;

/**
 * Mapper for the entity {@link BuyurtmaRaqam} and its DTO {@link BuyurtmaRaqamDTO}.
 */
@Mapper(componentModel = "spring")
public interface BuyurtmaRaqamMapper extends EntityMapper<BuyurtmaRaqamDTO, BuyurtmaRaqam> {
    @Mapping(target = "loyiha", source = "loyiha", qualifiedByName = "loyihaId")
    BuyurtmaRaqamDTO toDto(BuyurtmaRaqam s);

    @Named("loyihaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LoyihaDTO toDtoLoyihaId(Loyiha loyiha);
}
