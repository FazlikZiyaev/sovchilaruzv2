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
import uz.devops.sovchilaruzv2.domain.Nationality;
import uz.devops.sovchilaruzv2.domain.enumeration.EntityState;
import uz.devops.sovchilaruzv2.repository.NationalityRepository;
import uz.devops.sovchilaruzv2.service.dto.NationalityDTO;
import uz.devops.sovchilaruzv2.service.mapper.NationalityMapper;

/**
 * Integration tests for the {@link NationalityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NationalityResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORD = 1;
    private static final Integer UPDATED_ORD = 2;

    private static final EntityState DEFAULT_STATE = EntityState.ACTIVE;
    private static final EntityState UPDATED_STATE = EntityState.INACTIVE;

    private static final String ENTITY_API_URL = "/api/nationalities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private NationalityRepository nationalityRepository;

    @Autowired
    private NationalityMapper nationalityMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNationalityMockMvc;

    private Nationality nationality;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nationality createEntity(EntityManager em) {
        Nationality nationality = new Nationality().name(DEFAULT_NAME).ord(DEFAULT_ORD).state(DEFAULT_STATE);
        return nationality;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nationality createUpdatedEntity(EntityManager em) {
        Nationality nationality = new Nationality().name(UPDATED_NAME).ord(UPDATED_ORD).state(UPDATED_STATE);
        return nationality;
    }

    @BeforeEach
    public void initTest() {
        nationality = createEntity(em);
    }

    @Test
    @Transactional
    void createNationality() throws Exception {
        int databaseSizeBeforeCreate = nationalityRepository.findAll().size();
        // Create the Nationality
        NationalityDTO nationalityDTO = nationalityMapper.toDto(nationality);
        restNationalityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nationalityDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Nationality in the database
        List<Nationality> nationalityList = nationalityRepository.findAll();
        assertThat(nationalityList).hasSize(databaseSizeBeforeCreate + 1);
        Nationality testNationality = nationalityList.get(nationalityList.size() - 1);
        assertThat(testNationality.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNationality.getOrd()).isEqualTo(DEFAULT_ORD);
        assertThat(testNationality.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    void createNationalityWithExistingId() throws Exception {
        // Create the Nationality with an existing ID
        nationalityRepository.saveAndFlush(nationality);
        NationalityDTO nationalityDTO = nationalityMapper.toDto(nationality);

        int databaseSizeBeforeCreate = nationalityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNationalityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nationalityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nationality in the database
        List<Nationality> nationalityList = nationalityRepository.findAll();
        assertThat(nationalityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = nationalityRepository.findAll().size();
        // set the field null
        nationality.setName(null);

        // Create the Nationality, which fails.
        NationalityDTO nationalityDTO = nationalityMapper.toDto(nationality);

        restNationalityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nationalityDTO))
            )
            .andExpect(status().isBadRequest());

        List<Nationality> nationalityList = nationalityRepository.findAll();
        assertThat(nationalityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNationalities() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get all the nationalityList
        restNationalityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nationality.getId().toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].ord").value(hasItem(DEFAULT_ORD)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }

    @Test
    @Transactional
    void getNationality() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get the nationality
        restNationalityMockMvc
            .perform(get(ENTITY_API_URL_ID, nationality.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nationality.getId().toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.ord").value(DEFAULT_ORD))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingNationality() throws Exception {
        // Get the nationality
        restNationalityMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNationality() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        int databaseSizeBeforeUpdate = nationalityRepository.findAll().size();

        // Update the nationality
        Nationality updatedNationality = nationalityRepository.findById(nationality.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNationality are not directly saved in db
        em.detach(updatedNationality);
        updatedNationality.name(UPDATED_NAME).ord(UPDATED_ORD).state(UPDATED_STATE);
        NationalityDTO nationalityDTO = nationalityMapper.toDto(updatedNationality);

        restNationalityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nationalityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nationalityDTO))
            )
            .andExpect(status().isOk());

        // Validate the Nationality in the database
        List<Nationality> nationalityList = nationalityRepository.findAll();
        assertThat(nationalityList).hasSize(databaseSizeBeforeUpdate);
        Nationality testNationality = nationalityList.get(nationalityList.size() - 1);
        assertThat(testNationality.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNationality.getOrd()).isEqualTo(UPDATED_ORD);
        assertThat(testNationality.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    void putNonExistingNationality() throws Exception {
        int databaseSizeBeforeUpdate = nationalityRepository.findAll().size();
        nationality.setId(UUID.randomUUID());

        // Create the Nationality
        NationalityDTO nationalityDTO = nationalityMapper.toDto(nationality);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNationalityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nationalityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nationalityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nationality in the database
        List<Nationality> nationalityList = nationalityRepository.findAll();
        assertThat(nationalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNationality() throws Exception {
        int databaseSizeBeforeUpdate = nationalityRepository.findAll().size();
        nationality.setId(UUID.randomUUID());

        // Create the Nationality
        NationalityDTO nationalityDTO = nationalityMapper.toDto(nationality);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNationalityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nationalityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nationality in the database
        List<Nationality> nationalityList = nationalityRepository.findAll();
        assertThat(nationalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNationality() throws Exception {
        int databaseSizeBeforeUpdate = nationalityRepository.findAll().size();
        nationality.setId(UUID.randomUUID());

        // Create the Nationality
        NationalityDTO nationalityDTO = nationalityMapper.toDto(nationality);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNationalityMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nationalityDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nationality in the database
        List<Nationality> nationalityList = nationalityRepository.findAll();
        assertThat(nationalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNationalityWithPatch() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        int databaseSizeBeforeUpdate = nationalityRepository.findAll().size();

        // Update the nationality using partial update
        Nationality partialUpdatedNationality = new Nationality();
        partialUpdatedNationality.setId(nationality.getId());

        partialUpdatedNationality.name(UPDATED_NAME).state(UPDATED_STATE);

        restNationalityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNationality.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNationality))
            )
            .andExpect(status().isOk());

        // Validate the Nationality in the database
        List<Nationality> nationalityList = nationalityRepository.findAll();
        assertThat(nationalityList).hasSize(databaseSizeBeforeUpdate);
        Nationality testNationality = nationalityList.get(nationalityList.size() - 1);
        assertThat(testNationality.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNationality.getOrd()).isEqualTo(DEFAULT_ORD);
        assertThat(testNationality.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    void fullUpdateNationalityWithPatch() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        int databaseSizeBeforeUpdate = nationalityRepository.findAll().size();

        // Update the nationality using partial update
        Nationality partialUpdatedNationality = new Nationality();
        partialUpdatedNationality.setId(nationality.getId());

        partialUpdatedNationality.name(UPDATED_NAME).ord(UPDATED_ORD).state(UPDATED_STATE);

        restNationalityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNationality.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNationality))
            )
            .andExpect(status().isOk());

        // Validate the Nationality in the database
        List<Nationality> nationalityList = nationalityRepository.findAll();
        assertThat(nationalityList).hasSize(databaseSizeBeforeUpdate);
        Nationality testNationality = nationalityList.get(nationalityList.size() - 1);
        assertThat(testNationality.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNationality.getOrd()).isEqualTo(UPDATED_ORD);
        assertThat(testNationality.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    void patchNonExistingNationality() throws Exception {
        int databaseSizeBeforeUpdate = nationalityRepository.findAll().size();
        nationality.setId(UUID.randomUUID());

        // Create the Nationality
        NationalityDTO nationalityDTO = nationalityMapper.toDto(nationality);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNationalityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nationalityDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nationalityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nationality in the database
        List<Nationality> nationalityList = nationalityRepository.findAll();
        assertThat(nationalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNationality() throws Exception {
        int databaseSizeBeforeUpdate = nationalityRepository.findAll().size();
        nationality.setId(UUID.randomUUID());

        // Create the Nationality
        NationalityDTO nationalityDTO = nationalityMapper.toDto(nationality);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNationalityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nationalityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nationality in the database
        List<Nationality> nationalityList = nationalityRepository.findAll();
        assertThat(nationalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNationality() throws Exception {
        int databaseSizeBeforeUpdate = nationalityRepository.findAll().size();
        nationality.setId(UUID.randomUUID());

        // Create the Nationality
        NationalityDTO nationalityDTO = nationalityMapper.toDto(nationality);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNationalityMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nationalityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nationality in the database
        List<Nationality> nationalityList = nationalityRepository.findAll();
        assertThat(nationalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNationality() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        int databaseSizeBeforeDelete = nationalityRepository.findAll().size();

        // Delete the nationality
        restNationalityMockMvc
            .perform(delete(ENTITY_API_URL_ID, nationality.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Nationality> nationalityList = nationalityRepository.findAll();
        assertThat(nationalityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
