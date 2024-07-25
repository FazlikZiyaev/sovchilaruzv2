package uz.devops.sovchilaruzv2.service.mapper;

import org.mapstruct.*;
import uz.devops.sovchilaruzv2.domain.ContactInfo;
import uz.devops.sovchilaruzv2.domain.Profile;
import uz.devops.sovchilaruzv2.service.dto.ContactInfoDTO;
import uz.devops.sovchilaruzv2.service.dto.ProfileDTO;

/**
 * Mapper for the entity {@link ContactInfo} and its DTO {@link ContactInfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContactInfoMapper extends EntityMapper<ContactInfoDTO, ContactInfo> {
    @Mapping(target = "profile", source = "profile", qualifiedByName = "profileId")
    ContactInfoDTO toDto(ContactInfo s);

    @Named("profileId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProfileDTO toDtoProfileId(Profile profile);
}
