package uz.devops.sovchilaruzv2.service;

import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.devops.sovchilaruzv2.domain.ProfileDiscoverability;
import uz.devops.sovchilaruzv2.repository.ProfileDiscoverabilityRepository;
import uz.devops.sovchilaruzv2.service.dto.ProfileDiscoverabilityDTO;
import uz.devops.sovchilaruzv2.service.mapper.ProfileDiscoverabilityMapper;

/**
 * Service Implementation for managing {@link uz.devops.sovchilaruzv2.domain.ProfileDiscoverability}.
 */
@Service
@Transactional
public class ProfileDiscoverabilityService {

    private final Logger log = LoggerFactory.getLogger(ProfileDiscoverabilityService.class);

    private final ProfileDiscoverabilityRepository profileDiscoverabilityRepository;

    private final ProfileDiscoverabilityMapper profileDiscoverabilityMapper;

    public ProfileDiscoverabilityService(
        ProfileDiscoverabilityRepository profileDiscoverabilityRepository,
        ProfileDiscoverabilityMapper profileDiscoverabilityMapper
    ) {
        this.profileDiscoverabilityRepository = profileDiscoverabilityRepository;
        this.profileDiscoverabilityMapper = profileDiscoverabilityMapper;
    }

    /**
     * Save a profileDiscoverability.
     *
     * @param profileDiscoverabilityDTO the entity to save.
     * @return the persisted entity.
     */
    public ProfileDiscoverabilityDTO save(ProfileDiscoverabilityDTO profileDiscoverabilityDTO) {
        log.debug("Request to save ProfileDiscoverability : {}", profileDiscoverabilityDTO);
        ProfileDiscoverability profileDiscoverability = profileDiscoverabilityMapper.toEntity(profileDiscoverabilityDTO);
        profileDiscoverability = profileDiscoverabilityRepository.save(profileDiscoverability);
        return profileDiscoverabilityMapper.toDto(profileDiscoverability);
    }

    /**
     * Update a profileDiscoverability.
     *
     * @param profileDiscoverabilityDTO the entity to save.
     * @return the persisted entity.
     */
    public ProfileDiscoverabilityDTO update(ProfileDiscoverabilityDTO profileDiscoverabilityDTO) {
        log.debug("Request to update ProfileDiscoverability : {}", profileDiscoverabilityDTO);
        ProfileDiscoverability profileDiscoverability = profileDiscoverabilityMapper.toEntity(profileDiscoverabilityDTO);
        profileDiscoverability = profileDiscoverabilityRepository.save(profileDiscoverability);
        return profileDiscoverabilityMapper.toDto(profileDiscoverability);
    }

    /**
     * Partially update a profileDiscoverability.
     *
     * @param profileDiscoverabilityDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProfileDiscoverabilityDTO> partialUpdate(ProfileDiscoverabilityDTO profileDiscoverabilityDTO) {
        log.debug("Request to partially update ProfileDiscoverability : {}", profileDiscoverabilityDTO);

        return profileDiscoverabilityRepository
            .findById(profileDiscoverabilityDTO.getId())
            .map(existingProfileDiscoverability -> {
                profileDiscoverabilityMapper.partialUpdate(existingProfileDiscoverability, profileDiscoverabilityDTO);

                return existingProfileDiscoverability;
            })
            .map(profileDiscoverabilityRepository::save)
            .map(profileDiscoverabilityMapper::toDto);
    }

    /**
     * Get all the profileDiscoverabilities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProfileDiscoverabilityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProfileDiscoverabilities");
        return profileDiscoverabilityRepository.findAll(pageable).map(profileDiscoverabilityMapper::toDto);
    }

    /**
     * Get one profileDiscoverability by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProfileDiscoverabilityDTO> findOne(UUID id) {
        log.debug("Request to get ProfileDiscoverability : {}", id);
        return profileDiscoverabilityRepository.findById(id).map(profileDiscoverabilityMapper::toDto);
    }

    /**
     * Delete the profileDiscoverability by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete ProfileDiscoverability : {}", id);
        profileDiscoverabilityRepository.deleteById(id);
    }
}
