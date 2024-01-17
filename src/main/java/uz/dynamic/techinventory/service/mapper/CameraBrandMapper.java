package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.*;
import uz.dynamic.techinventory.domain.CameraBrand;
import uz.dynamic.techinventory.service.dto.CameraBrandDTO;

/**
 * Mapper for the entity {@link CameraBrand} and its DTO {@link CameraBrandDTO}.
 */
@Mapper(componentModel = "spring")
public interface CameraBrandMapper extends EntityMapper<CameraBrandDTO, CameraBrand> {}
