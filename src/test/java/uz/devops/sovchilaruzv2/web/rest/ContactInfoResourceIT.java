package uz.devops.sovchilaruzv2.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import uz.devops.sovchilaruzv2.IntegrationTest;
import uz.devops.sovchilaruzv2.domain.ContactInfo;
import uz.devops.sovchilaruzv2.domain.enumeration.EntityState;
import uz.devops.sovchilaruzv2.domain.enumeration.InfoType;
import uz.devops.sovchilaruzv2.repository.ContactInfoRepository;
import uz.devops.sovchilaruzv2.service.dto.ContactInfoDTO;
import uz.devops.sovchilaruzv2.service.mapper.ContactInfoMapper;

/**
 * Integration tests for the {@link ContactInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContactInfoResourceIT {

    private static final InfoType DEFAULT_TYPE = InfoType.PHONE_NUMBER;
    private static final InfoType UPDATED_TYPE = InfoType.INSTAGRAM;

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORD = 1;
    private static final Integer UPDATED_ORD = 2;

    private static final EntityState DEFAULT_STATE = EntityState.ACTIVE;
    private static final EntityState UPDATED_STATE = EntityState.INACTIVE;

    private static final String ENTITY_API_URL = "/api/contact-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ContactInfoRepository contactInfoRepository;

    @Autowired
    private ContactInfoMapper contactInfoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContactInfoMockMvc;

    private ContactInfo contactInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactInfo createEntity(EntityManager em) {
        ContactInfo contactInfo = new ContactInfo().type(DEFAULT_TYPE).text(DEFAULT_TEXT).ord(DEFAULT_ORD).state(DEFAULT_STATE);
        return contactInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactInfo createUpdatedEntity(EntityManager em) {
        ContactInfo contactInfo = new ContactInfo().type(UPDATED_TYPE).text(UPDATED_TEXT).ord(UPDATED_ORD).state(UPDATED_STATE);
        return contactInfo;
    }

    @BeforeEach
    public void initTest() {
        contactInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createContactInfo() throws Exception {
        int databaseSizeBeforeCreate = contactInfoRepository.findAll().size();
        // Create the ContactInfo
        ContactInfoDTO contactInfoDTO = contactInfoMapper.toDto(contactInfo);
        restContactInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactInfoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeCreate + 1);
        ContactInfo testContactInfo = contactInfoList.get(contactInfoList.size() - 1);
        assertThat(testContactInfo.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testContactInfo.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testContactInfo.getOrd()).isEqualTo(DEFAULT_ORD);
        assertThat(testContactInfo.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    void createContactInfoWithExistingId() throws Exception {
        // Create the ContactInfo with an existing ID
        contactInfoRepository.saveAndFlush(contactInfo);
        ContactInfoDTO contactInfoDTO = contactInfoMapper.toDto(contactInfo);

        int databaseSizeBeforeCreate = contactInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactInfoRepository.findAll().size();
        // set the field null
        contactInfo.setType(null);

        // Create the ContactInfo, which fails.
        ContactInfoDTO contactInfoDTO = contactInfoMapper.toDto(contactInfo);

        restContactInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactInfoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactInfoRepository.findAll().size();
        // set the field null
        contactInfo.setText(null);

        // Create the ContactInfo, which fails.
        ContactInfoDTO contactInfoDTO = contactInfoMapper.toDto(contactInfo);

        restContactInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactInfoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllContactInfos() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get all the contactInfoList
        restContactInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactInfo.getId().toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].ord").value(hasItem(DEFAULT_ORD)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }

    @Test
    @Transactional
    void getContactInfo() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get the contactInfo
        restContactInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, contactInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contactInfo.getId().toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.ord").value(DEFAULT_ORD))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingContactInfo() throws Exception {
        // Get the contactInfo
        restContactInfoMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingContactInfo() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        int databaseSizeBeforeUpdate = contactInfoRepository.findAll().size();

        // Update the contactInfo
        ContactInfo updatedContactInfo = contactInfoRepository.findById(contactInfo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedContactInfo are not directly saved in db
        em.detach(updatedContactInfo);
        updatedContactInfo.type(UPDATED_TYPE).text(UPDATED_TEXT).ord(UPDATED_ORD).state(UPDATED_STATE);
        ContactInfoDTO contactInfoDTO = contactInfoMapper.toDto(updatedContactInfo);

        restContactInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contactInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactInfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeUpdate);
        ContactInfo testContactInfo = contactInfoList.get(contactInfoList.size() - 1);
        assertThat(testContactInfo.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testContactInfo.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testContactInfo.getOrd()).isEqualTo(UPDATED_ORD);
        assertThat(testContactInfo.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    void putNonExistingContactInfo() throws Exception {
        int databaseSizeBeforeUpdate = contactInfoRepository.findAll().size();
        contactInfo.setId(UUID.randomUUID());

        // Create the ContactInfo
        ContactInfoDTO contactInfoDTO = contactInfoMapper.toDto(contactInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contactInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContactInfo() throws Exception {
        int databaseSizeBeforeUpdate = contactInfoRepository.findAll().size();
        contactInfo.setId(UUID.randomUUID());

        // Create the ContactInfo
        ContactInfoDTO contactInfoDTO = contactInfoMapper.toDto(contactInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContactInfo() throws Exception {
        int databaseSizeBeforeUpdate = contactInfoRepository.findAll().size();
        contactInfo.setId(UUID.randomUUID());

        // Create the ContactInfo
        ContactInfoDTO contactInfoDTO = contactInfoMapper.toDto(contactInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactInfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactInfoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContactInfoWithPatch() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        int databaseSizeBeforeUpdate = contactInfoRepository.findAll().size();

        // Update the contactInfo using partial update
        ContactInfo partialUpdatedContactInfo = new ContactInfo();
        partialUpdatedContactInfo.setId(contactInfo.getId());

        partialUpdatedContactInfo.type(UPDATED_TYPE).text(UPDATED_TEXT).state(UPDATED_STATE);

        restContactInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContactInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContactInfo))
            )
            .andExpect(status().isOk());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeUpdate);
        ContactInfo testContactInfo = contactInfoList.get(contactInfoList.size() - 1);
        assertThat(testContactInfo.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testContactInfo.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testContactInfo.getOrd()).isEqualTo(DEFAULT_ORD);
        assertThat(testContactInfo.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    void fullUpdateContactInfoWithPatch() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        int databaseSizeBeforeUpdate = contactInfoRepository.findAll().size();

        // Update the contactInfo using partial update
        ContactInfo partialUpdatedContactInfo = new ContactInfo();
        partialUpdatedContactInfo.setId(contactInfo.getId());

        partialUpdatedContactInfo.type(UPDATED_TYPE).text(UPDATED_TEXT).ord(UPDATED_ORD).state(UPDATED_STATE);

        restContactInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContactInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContactInfo))
            )
            .andExpect(status().isOk());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeUpdate);
        ContactInfo testContactInfo = contactInfoList.get(contactInfoList.size() - 1);
        assertThat(testContactInfo.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testContactInfo.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testContactInfo.getOrd()).isEqualTo(UPDATED_ORD);
        assertThat(testContactInfo.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    void patchNonExistingContactInfo() throws Exception {
        int databaseSizeBeforeUpdate = contactInfoRepository.findAll().size();
        contactInfo.setId(UUID.randomUUID());

        // Create the ContactInfo
        ContactInfoDTO contactInfoDTO = contactInfoMapper.toDto(contactInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contactInfoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContactInfo() throws Exception {
        int databaseSizeBeforeUpdate = contactInfoRepository.findAll().size();
        contactInfo.setId(UUID.randomUUID());

        // Create the ContactInfo
        ContactInfoDTO contactInfoDTO = contactInfoMapper.toDto(contactInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContactInfo() throws Exception {
        int databaseSizeBeforeUpdate = contactInfoRepository.findAll().size();
        contactInfo.setId(UUID.randomUUID());

        // Create the ContactInfo
        ContactInfoDTO contactInfoDTO = contactInfoMapper.toDto(contactInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactInfoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(contactInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContactInfo() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        int databaseSizeBeforeDelete = contactInfoRepository.findAll().size();

        // Delete the contactInfo
        restContactInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, contactInfo.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
