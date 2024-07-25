package uz.devops.sovchilaruzv2.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import uz.devops.sovchilaruzv2.IntegrationTest;
import uz.devops.sovchilaruzv2.domain.ProfileDiscoverability;
import uz.devops.sovchilaruzv2.domain.enumeration.MaritalStatus;
import uz.devops.sovchilaruzv2.repository.ProfileDiscoverabilityRepository;
import uz.devops.sovchilaruzv2.service.dto.ProfileDiscoverabilityDTO;
import uz.devops.sovchilaruzv2.service.mapper.ProfileDiscoverabilityMapper;

/**
 * Integration tests for the {@link ProfileDiscoverabilityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProfileDiscoverabilityResourceIT {

    private static final MaritalStatus DEFAULT_MARITAL_STATUS = MaritalStatus.UNMARRIED;
    private static final MaritalStatus UPDATED_MARITAL_STATUS = MaritalStatus.DIVORCED_THROUGH_COURT;

    private static final Integer DEFAULT_MAX_AGE = 18;
    private static final Integer UPDATED_MAX_AGE = 19;

    private static final Integer DEFAULT_MIN_AGE = 18;
    private static final Integer UPDATED_MIN_AGE = 19;

    private static final Boolean DEFAULT_SHOW_MY_PHOTO = false;
    private static final Boolean UPDATED_SHOW_MY_PHOTO = true;

    private static final String ENTITY_API_URL = "/api/profile-discoverabilities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProfileDiscoverabilityRepository profileDiscoverabilityRepository;

    @Autowired
    private ProfileDiscoverabilityMapper profileDiscoverabilityMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProfileDiscoverabilityMockMvc;

    private ProfileDiscoverability profileDiscoverability;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProfileDiscoverability createEntity(EntityManager em) {
        ProfileDiscoverability profileDiscoverability = new ProfileDiscoverability()
            .maritalStatus(DEFAULT_MARITAL_STATUS)
            .maxAge(DEFAULT_MAX_AGE)
            .minAge(DEFAULT_MIN_AGE)
            .showMyPhoto(DEFAULT_SHOW_MY_PHOTO);
        return profileDiscoverability;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProfileDiscoverability createUpdatedEntity(EntityManager em) {
        ProfileDiscoverability profileDiscoverability = new ProfileDiscoverability()
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .maxAge(UPDATED_MAX_AGE)
            .minAge(UPDATED_MIN_AGE)
            .showMyPhoto(UPDATED_SHOW_MY_PHOTO);
        return profileDiscoverability;
    }

    @BeforeEach
    public void initTest() {
        profileDiscoverability = createEntity(em);
    }

    @Test
    @Transactional
    void createProfileDiscoverability() throws Exception {
        int databaseSizeBeforeCreate = profileDiscoverabilityRepository.findAll().size();
        // Create the ProfileDiscoverability
        ProfileDiscoverabilityDTO profileDiscoverabilityDTO = profileDiscoverabilityMapper.toDto(profileDiscoverability);
        restProfileDiscoverabilityMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profileDiscoverabilityDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProfileDiscoverability in the database
        List<ProfileDiscoverability> profileDiscoverabilityList = profileDiscoverabilityRepository.findAll();
        assertThat(profileDiscoverabilityList).hasSize(databaseSizeBeforeCreate + 1);
        ProfileDiscoverability testProfileDiscoverability = profileDiscoverabilityList.get(profileDiscoverabilityList.size() - 1);
        assertThat(testProfileDiscoverability.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testProfileDiscoverability.getMaxAge()).isEqualTo(DEFAULT_MAX_AGE);
        assertThat(testProfileDiscoverability.getMinAge()).isEqualTo(DEFAULT_MIN_AGE);
        assertThat(testProfileDiscoverability.getShowMyPhoto()).isEqualTo(DEFAULT_SHOW_MY_PHOTO);
    }

    @Test
    @Transactional
    void createProfileDiscoverabilityWithExistingId() throws Exception {
        // Create the ProfileDiscoverability with an existing ID
        profileDiscoverability.setId(1L);
        ProfileDiscoverabilityDTO profileDiscoverabilityDTO = profileDiscoverabilityMapper.toDto(profileDiscoverability);

        int databaseSizeBeforeCreate = profileDiscoverabilityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileDiscoverabilityMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profileDiscoverabilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProfileDiscoverability in the database
        List<ProfileDiscoverability> profileDiscoverabilityList = profileDiscoverabilityRepository.findAll();
        assertThat(profileDiscoverabilityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProfileDiscoverabilities() throws Exception {
        // Initialize the database
        profileDiscoverabilityRepository.saveAndFlush(profileDiscoverability);

        // Get all the profileDiscoverabilityList
        restProfileDiscoverabilityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profileDiscoverability.getId().intValue())))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].maxAge").value(hasItem(DEFAULT_MAX_AGE)))
            .andExpect(jsonPath("$.[*].minAge").value(hasItem(DEFAULT_MIN_AGE)))
            .andExpect(jsonPath("$.[*].showMyPhoto").value(hasItem(DEFAULT_SHOW_MY_PHOTO.booleanValue())));
    }

    @Test
    @Transactional
    void getProfileDiscoverability() throws Exception {
        // Initialize the database
        profileDiscoverabilityRepository.saveAndFlush(profileDiscoverability);

        // Get the profileDiscoverability
        restProfileDiscoverabilityMockMvc
            .perform(get(ENTITY_API_URL_ID, profileDiscoverability.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(profileDiscoverability.getId().intValue()))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS.toString()))
            .andExpect(jsonPath("$.maxAge").value(DEFAULT_MAX_AGE))
            .andExpect(jsonPath("$.minAge").value(DEFAULT_MIN_AGE))
            .andExpect(jsonPath("$.showMyPhoto").value(DEFAULT_SHOW_MY_PHOTO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingProfileDiscoverability() throws Exception {
        // Get the profileDiscoverability
        restProfileDiscoverabilityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProfileDiscoverability() throws Exception {
        // Initialize the database
        profileDiscoverabilityRepository.saveAndFlush(profileDiscoverability);

        int databaseSizeBeforeUpdate = profileDiscoverabilityRepository.findAll().size();

        // Update the profileDiscoverability
        ProfileDiscoverability updatedProfileDiscoverability = profileDiscoverabilityRepository
            .findById(profileDiscoverability.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedProfileDiscoverability are not directly saved in db
        em.detach(updatedProfileDiscoverability);
        updatedProfileDiscoverability
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .maxAge(UPDATED_MAX_AGE)
            .minAge(UPDATED_MIN_AGE)
            .showMyPhoto(UPDATED_SHOW_MY_PHOTO);
        ProfileDiscoverabilityDTO profileDiscoverabilityDTO = profileDiscoverabilityMapper.toDto(updatedProfileDiscoverability);

        restProfileDiscoverabilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, profileDiscoverabilityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profileDiscoverabilityDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProfileDiscoverability in the database
        List<ProfileDiscoverability> profileDiscoverabilityList = profileDiscoverabilityRepository.findAll();
        assertThat(profileDiscoverabilityList).hasSize(databaseSizeBeforeUpdate);
        ProfileDiscoverability testProfileDiscoverability = profileDiscoverabilityList.get(profileDiscoverabilityList.size() - 1);
        assertThat(testProfileDiscoverability.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testProfileDiscoverability.getMaxAge()).isEqualTo(UPDATED_MAX_AGE);
        assertThat(testProfileDiscoverability.getMinAge()).isEqualTo(UPDATED_MIN_AGE);
        assertThat(testProfileDiscoverability.getShowMyPhoto()).isEqualTo(UPDATED_SHOW_MY_PHOTO);
    }

    @Test
    @Transactional
    void putNonExistingProfileDiscoverability() throws Exception {
        int databaseSizeBeforeUpdate = profileDiscoverabilityRepository.findAll().size();
        profileDiscoverability.setId(longCount.incrementAndGet());

        // Create the ProfileDiscoverability
        ProfileDiscoverabilityDTO profileDiscoverabilityDTO = profileDiscoverabilityMapper.toDto(profileDiscoverability);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfileDiscoverabilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, profileDiscoverabilityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profileDiscoverabilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProfileDiscoverability in the database
        List<ProfileDiscoverability> profileDiscoverabilityList = profileDiscoverabilityRepository.findAll();
        assertThat(profileDiscoverabilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProfileDiscoverability() throws Exception {
        int databaseSizeBeforeUpdate = profileDiscoverabilityRepository.findAll().size();
        profileDiscoverability.setId(longCount.incrementAndGet());

        // Create the ProfileDiscoverability
        ProfileDiscoverabilityDTO profileDiscoverabilityDTO = profileDiscoverabilityMapper.toDto(profileDiscoverability);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfileDiscoverabilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profileDiscoverabilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProfileDiscoverability in the database
        List<ProfileDiscoverability> profileDiscoverabilityList = profileDiscoverabilityRepository.findAll();
        assertThat(profileDiscoverabilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProfileDiscoverability() throws Exception {
        int databaseSizeBeforeUpdate = profileDiscoverabilityRepository.findAll().size();
        profileDiscoverability.setId(longCount.incrementAndGet());

        // Create the ProfileDiscoverability
        ProfileDiscoverabilityDTO profileDiscoverabilityDTO = profileDiscoverabilityMapper.toDto(profileDiscoverability);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfileDiscoverabilityMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profileDiscoverabilityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProfileDiscoverability in the database
        List<ProfileDiscoverability> profileDiscoverabilityList = profileDiscoverabilityRepository.findAll();
        assertThat(profileDiscoverabilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProfileDiscoverabilityWithPatch() throws Exception {
        // Initialize the database
        profileDiscoverabilityRepository.saveAndFlush(profileDiscoverability);

        int databaseSizeBeforeUpdate = profileDiscoverabilityRepository.findAll().size();

        // Update the profileDiscoverability using partial update
        ProfileDiscoverability partialUpdatedProfileDiscoverability = new ProfileDiscoverability();
        partialUpdatedProfileDiscoverability.setId(profileDiscoverability.getId());

        partialUpdatedProfileDiscoverability.maritalStatus(UPDATED_MARITAL_STATUS).showMyPhoto(UPDATED_SHOW_MY_PHOTO);

        restProfileDiscoverabilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfileDiscoverability.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProfileDiscoverability))
            )
            .andExpect(status().isOk());

        // Validate the ProfileDiscoverability in the database
        List<ProfileDiscoverability> profileDiscoverabilityList = profileDiscoverabilityRepository.findAll();
        assertThat(profileDiscoverabilityList).hasSize(databaseSizeBeforeUpdate);
        ProfileDiscoverability testProfileDiscoverability = profileDiscoverabilityList.get(profileDiscoverabilityList.size() - 1);
        assertThat(testProfileDiscoverability.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testProfileDiscoverability.getMaxAge()).isEqualTo(DEFAULT_MAX_AGE);
        assertThat(testProfileDiscoverability.getMinAge()).isEqualTo(DEFAULT_MIN_AGE);
        assertThat(testProfileDiscoverability.getShowMyPhoto()).isEqualTo(UPDATED_SHOW_MY_PHOTO);
    }

    @Test
    @Transactional
    void fullUpdateProfileDiscoverabilityWithPatch() throws Exception {
        // Initialize the database
        profileDiscoverabilityRepository.saveAndFlush(profileDiscoverability);

        int databaseSizeBeforeUpdate = profileDiscoverabilityRepository.findAll().size();

        // Update the profileDiscoverability using partial update
        ProfileDiscoverability partialUpdatedProfileDiscoverability = new ProfileDiscoverability();
        partialUpdatedProfileDiscoverability.setId(profileDiscoverability.getId());

        partialUpdatedProfileDiscoverability
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .maxAge(UPDATED_MAX_AGE)
            .minAge(UPDATED_MIN_AGE)
            .showMyPhoto(UPDATED_SHOW_MY_PHOTO);

        restProfileDiscoverabilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfileDiscoverability.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProfileDiscoverability))
            )
            .andExpect(status().isOk());

        // Validate the ProfileDiscoverability in the database
        List<ProfileDiscoverability> profileDiscoverabilityList = profileDiscoverabilityRepository.findAll();
        assertThat(profileDiscoverabilityList).hasSize(databaseSizeBeforeUpdate);
        ProfileDiscoverability testProfileDiscoverability = profileDiscoverabilityList.get(profileDiscoverabilityList.size() - 1);
        assertThat(testProfileDiscoverability.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testProfileDiscoverability.getMaxAge()).isEqualTo(UPDATED_MAX_AGE);
        assertThat(testProfileDiscoverability.getMinAge()).isEqualTo(UPDATED_MIN_AGE);
        assertThat(testProfileDiscoverability.getShowMyPhoto()).isEqualTo(UPDATED_SHOW_MY_PHOTO);
    }

    @Test
    @Transactional
    void patchNonExistingProfileDiscoverability() throws Exception {
        int databaseSizeBeforeUpdate = profileDiscoverabilityRepository.findAll().size();
        profileDiscoverability.setId(longCount.incrementAndGet());

        // Create the ProfileDiscoverability
        ProfileDiscoverabilityDTO profileDiscoverabilityDTO = profileDiscoverabilityMapper.toDto(profileDiscoverability);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfileDiscoverabilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, profileDiscoverabilityDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(profileDiscoverabilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProfileDiscoverability in the database
        List<ProfileDiscoverability> profileDiscoverabilityList = profileDiscoverabilityRepository.findAll();
        assertThat(profileDiscoverabilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProfileDiscoverability() throws Exception {
        int databaseSizeBeforeUpdate = profileDiscoverabilityRepository.findAll().size();
        profileDiscoverability.setId(longCount.incrementAndGet());

        // Create the ProfileDiscoverability
        ProfileDiscoverabilityDTO profileDiscoverabilityDTO = profileDiscoverabilityMapper.toDto(profileDiscoverability);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfileDiscoverabilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(profileDiscoverabilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProfileDiscoverability in the database
        List<ProfileDiscoverability> profileDiscoverabilityList = profileDiscoverabilityRepository.findAll();
        assertThat(profileDiscoverabilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProfileDiscoverability() throws Exception {
        int databaseSizeBeforeUpdate = profileDiscoverabilityRepository.findAll().size();
        profileDiscoverability.setId(longCount.incrementAndGet());

        // Create the ProfileDiscoverability
        ProfileDiscoverabilityDTO profileDiscoverabilityDTO = profileDiscoverabilityMapper.toDto(profileDiscoverability);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfileDiscoverabilityMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(profileDiscoverabilityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProfileDiscoverability in the database
        List<ProfileDiscoverability> profileDiscoverabilityList = profileDiscoverabilityRepository.findAll();
        assertThat(profileDiscoverabilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProfileDiscoverability() throws Exception {
        // Initialize the database
        profileDiscoverabilityRepository.saveAndFlush(profileDiscoverability);

        int databaseSizeBeforeDelete = profileDiscoverabilityRepository.findAll().size();

        // Delete the profileDiscoverability
        restProfileDiscoverabilityMockMvc
            .perform(delete(ENTITY_API_URL_ID, profileDiscoverability.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProfileDiscoverability> profileDiscoverabilityList = profileDiscoverabilityRepository.findAll();
        assertThat(profileDiscoverabilityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
