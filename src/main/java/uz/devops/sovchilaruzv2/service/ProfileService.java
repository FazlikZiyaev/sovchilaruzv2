package uz.devops.sovchilaruzv2.service;

import io.undertow.util.BadRequestException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.devops.sovchilaruzv2.domain.Profile;
import uz.devops.sovchilaruzv2.repository.ProfileRepository;
import uz.devops.sovchilaruzv2.service.dto.ProfileDTO;
import uz.devops.sovchilaruzv2.service.mapper.ProfileMapper;
import uz.devops.sovchilaruzv2.web.rest.errors.CustomBadRequestException;

/**
 * Service Implementation for managing {@link uz.devops.sovchilaruzv2.domain.Profile}.
 */
@Service
@Transactional
public class ProfileService {

    private final Logger log = LoggerFactory.getLogger(ProfileService.class);

    private final ProfileRepository profileRepository;

    private final ProfileMapper profileMapper;

    public ProfileService(ProfileRepository profileRepository, ProfileMapper profileMapper) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
    }

    /**
     * Save a profile.
     *
     * @param profileDTO the entity to save.
     * @return the persisted entity.
     */
    public ProfileDTO save(ProfileDTO profileDTO) {
        validateProfile(profileDTO);
        log.debug("Request to save Profile : {}", profileDTO);
        Profile profile = profileMapper.toEntity(profileDTO);
        profile = profileRepository.save(profile);
        return profileMapper.toDto(profile);
    }

    private void validateProfile(ProfileDTO profileDTO) {
        LocalDate dateOfBirth = profileDTO.getDateOfBirth();
        if (profileDTO.getUserId() == null) {
            throw new IllegalArgumentException("User id mast be not null");
        }
        if (dateOfBirth == null) {
            throw new IllegalArgumentException("Date of birth cannot be null");
        }

        LocalDate today = LocalDate.now();
        int age = Period.between(dateOfBirth, today).getYears();

        if (age < 18) {
            throw new CustomBadRequestException("User must be at least 18 years old");
        }
        if (!profileDTO.getIsHealthy() && profileDTO.getHealthIssues() == null) {
            throw new CustomBadRequestException("If user is not healthy healthyIssues mast be not null");
        }
    }

    /**
     * Update a profile.
     *
     * @param profileDTO the entity to save.
     * @return the persisted entity.
     */
    public ProfileDTO update(ProfileDTO profileDTO) {
        log.debug("Request to update Profile : {}", profileDTO);
        Profile profile = profileMapper.toEntity(profileDTO);
        profile = profileRepository.save(profile);
        return profileMapper.toDto(profile);
    }

    /**
     * Partially update a profile.
     *
     * @param profileDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProfileDTO> partialUpdate(ProfileDTO profileDTO) {
        log.debug("Request to partially update Profile : {}", profileDTO);

        return profileRepository
            .findById(profileDTO.getId())
            .map(existingProfile -> {
                profileMapper.partialUpdate(existingProfile, profileDTO);

                return existingProfile;
            })
            .map(profileRepository::save)
            .map(profileMapper::toDto);
    }

    /**
     * Get all the profiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProfileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Profiles");
        return profileRepository.findAll(pageable).map(profileMapper::toDto);
    }

    /**
     * Get all the profiles with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ProfileDTO> findAllWithEagerRelationships(Pageable pageable) {
        return profileRepository.findAllWithEagerRelationships(pageable).map(profileMapper::toDto);
    }

    /**
     * Get one profile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProfileDTO> findOne(UUID id) {
        log.debug("Request to get Profile : {}", id);
        return profileRepository.findOneWithEagerRelationships(id).map(profileMapper::toDto);
    }

    /**
     * Delete the profile by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Profile : {}", id);
        profileRepository.deleteById(id);
    }
}
