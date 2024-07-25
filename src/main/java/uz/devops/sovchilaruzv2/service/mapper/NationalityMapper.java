package uz.devops.sovchilaruzv2.service.mapper;

import org.mapstruct.*;
import uz.devops.sovchilaruzv2.domain.Nationality;
import uz.devops.sovchilaruzv2.service.dto.NationalityDTO;

/**
 * Mapper for the entity {@link Nationality} and its DTO {@link NationalityDTO}.
 */
@Mapper(componentModel = "spring")
public interface NationalityMapper extends EntityMapper<NationalityDTO, Nationality> {}
