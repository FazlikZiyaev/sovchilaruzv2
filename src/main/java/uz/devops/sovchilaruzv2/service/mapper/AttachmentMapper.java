package uz.devops.sovchilaruzv2.service.mapper;

import org.mapstruct.*;
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
    AttachmentDTO toDto(Attachment s);

    @Named("profileId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProfileDTO toDtoProfileId(Profile profile);
}
