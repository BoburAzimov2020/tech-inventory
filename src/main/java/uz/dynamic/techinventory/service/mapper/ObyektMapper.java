package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.*;
import uz.dynamic.techinventory.domain.BuyurtmaRaqam;
import uz.dynamic.techinventory.domain.Obyekt;
import uz.dynamic.techinventory.service.dto.BuyurtmaRaqamDTO;
import uz.dynamic.techinventory.service.dto.ObyektDTO;

/**
 * Mapper for the entity {@link Obyekt} and its DTO {@link ObyektDTO}.
 */
@Mapper(componentModel = "spring")
public interface ObyektMapper extends EntityMapper<ObyektDTO, Obyekt> {
    @Mapping(target = "buyurtmaRaqam", source = "buyurtmaRaqam", qualifiedByName = "buyurtmaRaqamId")
    ObyektDTO toDto(Obyekt s);

    @Named("buyurtmaRaqamId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BuyurtmaRaqamDTO toDtoBuyurtmaRaqamId(BuyurtmaRaqam buyurtmaRaqam);
}
