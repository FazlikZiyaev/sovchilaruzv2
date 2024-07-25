package uz.devops.sovchilaruzv2.service;

import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.devops.sovchilaruzv2.domain.GenderTag;
import uz.devops.sovchilaruzv2.repository.GenderTagRepository;
import uz.devops.sovchilaruzv2.service.dto.GenderTagDTO;
import uz.devops.sovchilaruzv2.service.mapper.GenderTagMapper;

/**
 * Service Implementation for managing {@link uz.devops.sovchilaruzv2.domain.GenderTag}.
 */
@Service
@Transactional
public class GenderTagService {

    private final Logger log = LoggerFactory.getLogger(GenderTagService.class);

    private final GenderTagRepository genderTagRepository;

    private final GenderTagMapper genderTagMapper;

    public GenderTagService(GenderTagRepository genderTagRepository, GenderTagMapper genderTagMapper) {
        this.genderTagRepository = genderTagRepository;
        this.genderTagMapper = genderTagMapper;
    }

    /**
     * Save a genderTag.
     *
     * @param genderTagDTO the entity to save.
     * @return the persisted entity.
     */
    public GenderTagDTO save(GenderTagDTO genderTagDTO) {
        log.debug("Request to save GenderTag : {}", genderTagDTO);
        GenderTag genderTag = genderTagMapper.toEntity(genderTagDTO);
        genderTag = genderTagRepository.save(genderTag);
        return genderTagMapper.toDto(genderTag);
    }

    /**
     * Update a genderTag.
     *
     * @param genderTagDTO the entity to save.
     * @return the persisted entity.
     */
    public GenderTagDTO update(GenderTagDTO genderTagDTO) {
        log.debug("Request to update GenderTag : {}", genderTagDTO);
        GenderTag genderTag = genderTagMapper.toEntity(genderTagDTO);
        genderTag = genderTagRepository.save(genderTag);
        return genderTagMapper.toDto(genderTag);
    }

    /**
     * Partially update a genderTag.
     *
     * @param genderTagDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GenderTagDTO> partialUpdate(GenderTagDTO genderTagDTO) {
        log.debug("Request to partially update GenderTag : {}", genderTagDTO);

        return genderTagRepository
            .findById(genderTagDTO.getId())
            .map(existingGenderTag -> {
                genderTagMapper.partialUpdate(existingGenderTag, genderTagDTO);

                return existingGenderTag;
            })
            .map(genderTagRepository::save)
            .map(genderTagMapper::toDto);
    }

    /**
     * Get all the genderTags.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GenderTagDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GenderTags");
        return genderTagRepository.findAll(pageable).map(genderTagMapper::toDto);
    }

    /**
     * Get one genderTag by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GenderTagDTO> findOne(UUID id) {
        log.debug("Request to get GenderTag : {}", id);
        return genderTagRepository.findById(id).map(genderTagMapper::toDto);
    }

    /**
     * Delete the genderTag by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete GenderTag : {}", id);
        genderTagRepository.deleteById(id);
    }
}
