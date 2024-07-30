package uz.devops.sovchilaruzv2.service.mapper;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.mapstruct.*;
import uz.devops.sovchilaruzv2.domain.*;
import uz.devops.sovchilaruzv2.repository.UserRepository;
import uz.devops.sovchilaruzv2.service.dto.*;

/**
 * Mapper for the entity {@link Profile} and its DTO {@link ProfileDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = { LocationMapper.class, ProfileDiscoverabilityMapper.class, NationalityMapper.class, GenderTagMapper.class, UserMapper.class }
)
public interface ProfileMapper extends EntityMapper<ProfileDTO, Profile> {
    @Mapping(target = "location", source = "location", qualifiedByName = "locationId")
    @Mapping(target = "discoverability", source = "discoverability", qualifiedByName = "profileDiscoverabilityId")
    @Mapping(target = "nationality", source = "nationality", qualifiedByName = "nationalityId")
    @Mapping(target = "genderTags", source = "genderTags")
    @Mapping(target = "userId", source = "user", qualifiedByName = "userToUserId")
    ProfileDTO toDto(Profile s);

    @Mapping(target = "removeGenderTags", ignore = true)
    @Mapping(target = "user", source = "userId", qualifiedByName = "userIdToUser")
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
    //    @Named("genderTagIdSet")
    //    default Set<GenderTagDTO> toDtoGenderTagIdSet(Set<GenderTag> genderTag) {
    //        return genderTag.stream().map(this::toDtoGenderTagId).collect(Collectors.toSet());
    //    }

    //    @Named("userIdToUser")
    //    default User userIdToUser(UUID userId, @Context UserRepository userRepository) {
    //        if (userId == null) {
    //            return null;
    //        }
    //        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
    //    }
    //
    //    @Named("userToUserId")
    //    default UUID userToUserId(User user) {
    //        return user != null ? user.getId() : null;
    //    }
}
