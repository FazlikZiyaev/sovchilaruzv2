package uz.devops.sovchilaruzv2.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import uz.devops.sovchilaruzv2.domain.Attachment;
import uz.devops.sovchilaruzv2.domain.Profile;
import uz.devops.sovchilaruzv2.service.dto.AttachmentDTO;
import uz.devops.sovchilaruzv2.service.dto.ProfileDTO;

/**
 * Mapper for the entity {@link Attachment} and its DTO {@link AttachmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface AttachmentMapper extends EntityMapper<AttachmentDTO, Attachment> {
    @Mapping(target = "profile", source = "profile", qualifiedByName = "profileId")
    @Mapping(target = "extension", source = "extension")
    AttachmentDTO toDto(Attachment s);

    @Named("profileId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProfileDTO toDtoProfileId(Profile profile);
}
