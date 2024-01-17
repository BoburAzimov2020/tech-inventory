package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.*;
import uz.dynamic.techinventory.domain.Attachment;
import uz.dynamic.techinventory.domain.Obyekt;
import uz.dynamic.techinventory.service.dto.AttachmentDTO;
import uz.dynamic.techinventory.service.dto.ObyektDTO;

/**
 * Mapper for the entity {@link Attachment} and its DTO {@link AttachmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface AttachmentMapper extends EntityMapper<AttachmentDTO, Attachment> {
    @Mapping(target = "obyekt", source = "obyekt", qualifiedByName = "obyektId")
    AttachmentDTO toDto(Attachment s);

    @Named("obyektId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ObyektDTO toDtoObyektId(Obyekt obyekt);
}
