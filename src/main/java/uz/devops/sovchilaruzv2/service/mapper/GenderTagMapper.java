package uz.devops.sovchilaruzv2.service.mapper;

import org.mapstruct.*;
import uz.devops.sovchilaruzv2.domain.GenderTag;
import uz.devops.sovchilaruzv2.service.dto.GenderTagDTO;

/**
 * Mapper for the entity {@link GenderTag} and its DTO {@link GenderTagDTO}.
 */
@Mapper(componentModel = "spring")
public interface GenderTagMapper extends EntityMapper<GenderTagDTO, GenderTag> {}
