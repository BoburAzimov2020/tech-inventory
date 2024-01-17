package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.*;
import uz.dynamic.techinventory.domain.Obyekt;
import uz.dynamic.techinventory.domain.TerminalServer;
import uz.dynamic.techinventory.service.dto.ObyektDTO;
import uz.dynamic.techinventory.service.dto.TerminalServerDTO;

/**
 * Mapper for the entity {@link TerminalServer} and its DTO {@link TerminalServerDTO}.
 */
@Mapper(componentModel = "spring")
public interface TerminalServerMapper extends EntityMapper<TerminalServerDTO, TerminalServer> {
    @Mapping(target = "obyekt", source = "obyekt", qualifiedByName = "obyektId")
    TerminalServerDTO toDto(TerminalServer s);

    @Named("obyektId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ObyektDTO toDtoObyektId(Obyekt obyekt);
}
