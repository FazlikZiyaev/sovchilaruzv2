package uz.devops.sovchilaruzv2.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;
import uz.devops.sovchilaruzv2.domain.GenderTag;
import uz.devops.sovchilaruzv2.domain.Location;
import uz.devops.sovchilaruzv2.domain.Nationality;
import uz.devops.sovchilaruzv2.domain.Profile;
import uz.devops.sovchilaruzv2.domain.ProfileDiscoverability;
import uz.devops.sovchilaruzv2.service.dto.GenderTagDTO;
import uz.devops.sovchilaruzv2.service.dto.LocationDTO;
import uz.devops.sovchilaruzv2.service.dto.NationalityDTO;
import uz.devops.sovchilaruzv2.service.dto.ProfileDTO;
import uz.devops.sovchilaruzv2.service.dto.ProfileDiscoverabilityDTO;

/**
 * Mapper for the entity {@link Profile} and its DTO {@link ProfileDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProfileMapper extends EntityMapper<ProfileDTO, Profile> {
    @Mapping(target = "location", source = "location", qualifiedByName = "locationId")
    @Mapping(target = "discoverability", source = "discoverability", qualifiedByName = "profileDiscoverabilityId")
    @Mapping(target = "nationality", source = "nationality", qualifiedByName = "nationalityId")
    @Mapping(target = "genderTags", source = "genderTags", qualifiedByName = "genderTagIdSet")
    ProfileDTO toDto(Profile s);

    @Mapping(target = "removeGenderTags", ignore = true)
    Profile toEntity(ProfileDTO profileDTO);

    @Named("locationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocationDTO toDtoLocationId(Location location);

    @Named("profileDiscoverabilityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProfileDiscoverabilityDTO toDtoProfileDiscoverabilityId(ProfileDiscoverability profileDiscoverability);

    @Named("nationalityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NationalityDTO toDtoNationalityId(Nationality nationality);

    @Named("genderTagId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GenderTagDTO toDtoGenderTagId(GenderTag genderTag);

    @Named("genderTagIdSet")
    default Set<GenderTagDTO> toDtoGenderTagIdSet(Set<GenderTag> genderTag) {
        return genderTag.stream().map(this::toDtoGenderTagId).collect(Collectors.toSet());
    }
}
