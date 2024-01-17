package uz.dynamic.techinventory.service.mapper;

import org.mapstruct.*;
import uz.dynamic.techinventory.domain.District;
import uz.dynamic.techinventory.domain.Region;
import uz.dynamic.techinventory.service.dto.DistrictDTO;
import uz.dynamic.techinventory.service.dto.RegionDTO;

/**
 * Mapper for the entity {@link District} and its DTO {@link DistrictDTO}.
 */
@Mapper(componentModel = "spring")
public interface DistrictMapper extends EntityMapper<DistrictDTO, District> {
    @Mapping(target = "region", source = "region", qualifiedByName = "regionId")
    DistrictDTO toDto(District s);

    @Named("regionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RegionDTO toDtoRegionId(Region region);
}
