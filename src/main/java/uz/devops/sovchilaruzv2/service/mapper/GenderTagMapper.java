package uz.devops.sovchilaruzv2.service.mapper;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.mapstruct.*;
import uz.devops.sovchilaruzv2.domain.GenderTag;
import uz.devops.sovchilaruzv2.repository.GenderTagRepository;
import uz.devops.sovchilaruzv2.service.dto.GenderTagDTO;

/**
 * Mapper for the entity {@link GenderTag} and its DTO {@link GenderTagDTO}.
 */
@Mapper(componentModel = "spring")
public interface GenderTagMapper extends EntityMapper<GenderTagDTO, GenderTag> {
    @Named("toDtoGenderTagSet")
    default Set<GenderTagDTO> toDtoGenderTagSet(Set<GenderTag> genderTag) {
        return genderTag.stream().map(this::toDto).collect(Collectors.toSet());
    }
}
