package uz.devops.sovchilaruzv2.service;

import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.devops.sovchilaruzv2.domain.ContactInfo;
import uz.devops.sovchilaruzv2.repository.ContactInfoRepository;
import uz.devops.sovchilaruzv2.service.dto.ContactInfoDTO;
import uz.devops.sovchilaruzv2.service.mapper.ContactInfoMapper;

/**
 * Service Implementation for managing {@link uz.devops.sovchilaruzv2.domain.ContactInfo}.
 */
@Service
@Transactional
public class ContactInfoService {

    private final Logger log = LoggerFactory.getLogger(ContactInfoService.class);

    private final ContactInfoRepository contactInfoRepository;

    private final ContactInfoMapper contactInfoMapper;

    public ContactInfoService(ContactInfoRepository contactInfoRepository, ContactInfoMapper contactInfoMapper) {
        this.contactInfoRepository = contactInfoRepository;
        this.contactInfoMapper = contactInfoMapper;
    }

    /**
     * Save a contactInfo.
     *
     * @param contactInfoDTO the entity to save.
     * @return the persisted entity.
     */
    public ContactInfoDTO save(ContactInfoDTO contactInfoDTO) {
        log.debug("Request to save ContactInfo : {}", contactInfoDTO);
        ContactInfo contactInfo = contactInfoMapper.toEntity(contactInfoDTO);
        contactInfo = contactInfoRepository.save(contactInfo);
        return contactInfoMapper.toDto(contactInfo);
    }

    /**
     * Update a contactInfo.
     *
     * @param contactInfoDTO the entity to save.
     * @return the persisted entity.
     */
    public ContactInfoDTO update(ContactInfoDTO contactInfoDTO) {
        log.debug("Request to update ContactInfo : {}", contactInfoDTO);
        ContactInfo contactInfo = contactInfoMapper.toEntity(contactInfoDTO);
        contactInfo = contactInfoRepository.save(contactInfo);
        return contactInfoMapper.toDto(contactInfo);
    }

    /**
     * Partially update a contactInfo.
     *
     * @param contactInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ContactInfoDTO> partialUpdate(ContactInfoDTO contactInfoDTO) {
        log.debug("Request to partially update ContactInfo : {}", contactInfoDTO);

        return contactInfoRepository
            .findById(contactInfoDTO.getId())
            .map(existingContactInfo -> {
                contactInfoMapper.partialUpdate(existingContactInfo, contactInfoDTO);

                return existingContactInfo;
            })
            .map(contactInfoRepository::save)
            .map(contactInfoMapper::toDto);
    }

    /**
     * Get all the contactInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ContactInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContactInfos");
        return contactInfoRepository.findAll(pageable).map(contactInfoMapper::toDto);
    }

    /**
     * Get one contactInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContactInfoDTO> findOne(UUID id) {
        log.debug("Request to get ContactInfo : {}", id);
        return contactInfoRepository.findById(id).map(contactInfoMapper::toDto);
    }

    /**
     * Delete the contactInfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete ContactInfo : {}", id);
        contactInfoRepository.deleteById(id);
    }
}
