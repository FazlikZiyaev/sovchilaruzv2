package uz.devops.sovchilaruzv2.service.mapper;

import org.mapstruct.*;
import uz.devops.sovchilaruzv2.domain.ProfileDiscoverability;
import uz.devops.sovchilaruzv2.service.dto.ProfileDiscoverabilityDTO;

/**
 * Mapper for the entity {@link ProfileDiscoverability} and its DTO {@link ProfileDiscoverabilityDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProfileDiscoverabilityMapper extends EntityMapper<ProfileDiscoverabilityDTO, ProfileDiscoverability> {}
