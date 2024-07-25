package uz.devops.sovchilaruzv2.service;

import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.devops.sovchilaruzv2.domain.Nationality;
import uz.devops.sovchilaruzv2.repository.NationalityRepository;
import uz.devops.sovchilaruzv2.service.dto.NationalityDTO;
import uz.devops.sovchilaruzv2.service.mapper.NationalityMapper;

/**
 * Service Implementation for managing {@link uz.devops.sovchilaruzv2.domain.Nationality}.
 */
@Service
@Transactional
public class NationalityService {

    private final Logger log = LoggerFactory.getLogger(NationalityService.class);

    private final NationalityRepository nationalityRepository;

    private final NationalityMapper nationalityMapper;

    public NationalityService(NationalityRepository nationalityRepository, NationalityMapper nationalityMapper) {
        this.nationalityRepository = nationalityRepository;
        this.nationalityMapper = nationalityMapper;
    }

    /**
     * Save a nationality.
     *
     * @param nationalityDTO the entity to save.
     * @return the persisted entity.
     */
    public NationalityDTO save(NationalityDTO nationalityDTO) {
        log.debug("Request to save Nationality : {}", nationalityDTO);
        Nationality nationality = nationalityMapper.toEntity(nationalityDTO);
        nationality = nationalityRepository.save(nationality);
        return nationalityMapper.toDto(nationality);
    }

    /**
     * Update a nationality.
     *
     * @param nationalityDTO the entity to save.
     * @return the persisted entity.
     */
    public NationalityDTO update(NationalityDTO nationalityDTO) {
        log.debug("Request to update Nationality : {}", nationalityDTO);
        Nationality nationality = nationalityMapper.toEntity(nationalityDTO);
        nationality = nationalityRepository.save(nationality);
        return nationalityMapper.toDto(nationality);
    }

    /**
     * Partially update a nationality.
     *
     * @param nationalityDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NationalityDTO> partialUpdate(NationalityDTO nationalityDTO) {
        log.debug("Request to partially update Nationality : {}", nationalityDTO);

        return nationalityRepository
            .findById(nationalityDTO.getId())
            .map(existingNationality -> {
                nationalityMapper.partialUpdate(existingNationality, nationalityDTO);

                return existingNationality;
            })
            .map(nationalityRepository::save)
            .map(nationalityMapper::toDto);
    }

    /**
     * Get all the nationalities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NationalityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Nationalities");
        return nationalityRepository.findAll(pageable).map(nationalityMapper::toDto);
    }

    /**
     * Get one nationality by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NationalityDTO> findOne(UUID id) {
        log.debug("Request to get Nationality : {}", id);
        return nationalityRepository.findById(id).map(nationalityMapper::toDto);
    }

    /**
     * Delete the nationality by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Nationality : {}", id);
        nationalityRepository.deleteById(id);
    }
}
