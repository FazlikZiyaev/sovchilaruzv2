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
import uz.devops.sovchilaruzv2.domain.GenderTag;
import uz.devops.sovchilaruzv2.domain.enumeration.EntityState;
import uz.devops.sovchilaruzv2.domain.enumeration.Gender;
import uz.devops.sovchilaruzv2.repository.GenderTagRepository;
import uz.devops.sovchilaruzv2.service.dto.GenderTagDTO;
import uz.devops.sovchilaruzv2.service.mapper.GenderTagMapper;

/**
 * Integration tests for the {@link GenderTagResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GenderTagResourceIT {

    private static final String DEFAULT_HASHTAG = "AAAAAAAAAA";
    private static final String UPDATED_HASHTAG = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final EntityState DEFAULT_STATE = EntityState.ACTIVE;
    private static final EntityState UPDATED_STATE = EntityState.INACTIVE;

    private static final String ENTITY_API_URL = "/api/gender-tags";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private GenderTagRepository genderTagRepository;

    @Autowired
    private GenderTagMapper genderTagMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGenderTagMockMvc;

    private GenderTag genderTag;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GenderTag createEntity(EntityManager em) {
        GenderTag genderTag = new GenderTag().hashtag(DEFAULT_HASHTAG).gender(DEFAULT_GENDER).state(DEFAULT_STATE);
        return genderTag;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GenderTag createUpdatedEntity(EntityManager em) {
        GenderTag genderTag = new GenderTag().hashtag(UPDATED_HASHTAG).gender(UPDATED_GENDER).state(UPDATED_STATE);
        return genderTag;
    }

    @BeforeEach
    public void initTest() {
        genderTag = createEntity(em);
    }

    @Test
    @Transactional
    void createGenderTag() throws Exception {
        int databaseSizeBeforeCreate = genderTagRepository.findAll().size();
        // Create the GenderTag
        GenderTagDTO genderTagDTO = genderTagMapper.toDto(genderTag);
        restGenderTagMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(genderTagDTO)))
            .andExpect(status().isCreated());

        // Validate the GenderTag in the database
        List<GenderTag> genderTagList = genderTagRepository.findAll();
        assertThat(genderTagList).hasSize(databaseSizeBeforeCreate + 1);
        GenderTag testGenderTag = genderTagList.get(genderTagList.size() - 1);
        assertThat(testGenderTag.getHashtag()).isEqualTo(DEFAULT_HASHTAG);
        assertThat(testGenderTag.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testGenderTag.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    void createGenderTagWithExistingId() throws Exception {
        // Create the GenderTag with an existing ID
        genderTagRepository.saveAndFlush(genderTag);
        GenderTagDTO genderTagDTO = genderTagMapper.toDto(genderTag);

        int databaseSizeBeforeCreate = genderTagRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGenderTagMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(genderTagDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GenderTag in the database
        List<GenderTag> genderTagList = genderTagRepository.findAll();
        assertThat(genderTagList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkHashtagIsRequired() throws Exception {
        int databaseSizeBeforeTest = genderTagRepository.findAll().size();
        // set the field null
        genderTag.setHashtag(null);

        // Create the GenderTag, which fails.
        GenderTagDTO genderTagDTO = genderTagMapper.toDto(genderTag);

        restGenderTagMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(genderTagDTO)))
            .andExpect(status().isBadRequest());

        List<GenderTag> genderTagList = genderTagRepository.findAll();
        assertThat(genderTagList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = genderTagRepository.findAll().size();
        // set the field null
        genderTag.setGender(null);

        // Create the GenderTag, which fails.
        GenderTagDTO genderTagDTO = genderTagMapper.toDto(genderTag);

        restGenderTagMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(genderTagDTO)))
            .andExpect(status().isBadRequest());

        List<GenderTag> genderTagList = genderTagRepository.findAll();
        assertThat(genderTagList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGenderTags() throws Exception {
        // Initialize the database
        genderTagRepository.saveAndFlush(genderTag);

        // Get all the genderTagList
        restGenderTagMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(genderTag.getId().toString())))
            .andExpect(jsonPath("$.[*].hashtag").value(hasItem(DEFAULT_HASHTAG)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }

    @Test
    @Transactional
    void getGenderTag() throws Exception {
        // Initialize the database
        genderTagRepository.saveAndFlush(genderTag);

        // Get the genderTag
        restGenderTagMockMvc
            .perform(get(ENTITY_API_URL_ID, genderTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(genderTag.getId().toString()))
            .andExpect(jsonPath("$.hashtag").value(DEFAULT_HASHTAG))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingGenderTag() throws Exception {
        // Get the genderTag
        restGenderTagMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGenderTag() throws Exception {
        // Initialize the database
        genderTagRepository.saveAndFlush(genderTag);

        int databaseSizeBeforeUpdate = genderTagRepository.findAll().size();

        // Update the genderTag
        GenderTag updatedGenderTag = genderTagRepository.findById(genderTag.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGenderTag are not directly saved in db
        em.detach(updatedGenderTag);
        updatedGenderTag.hashtag(UPDATED_HASHTAG).gender(UPDATED_GENDER).state(UPDATED_STATE);
        GenderTagDTO genderTagDTO = genderTagMapper.toDto(updatedGenderTag);

        restGenderTagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, genderTagDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(genderTagDTO))
            )
            .andExpect(status().isOk());

        // Validate the GenderTag in the database
        List<GenderTag> genderTagList = genderTagRepository.findAll();
        assertThat(genderTagList).hasSize(databaseSizeBeforeUpdate);
        GenderTag testGenderTag = genderTagList.get(genderTagList.size() - 1);
        assertThat(testGenderTag.getHashtag()).isEqualTo(UPDATED_HASHTAG);
        assertThat(testGenderTag.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testGenderTag.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    void putNonExistingGenderTag() throws Exception {
        int databaseSizeBeforeUpdate = genderTagRepository.findAll().size();
        genderTag.setId(UUID.randomUUID());

        // Create the GenderTag
        GenderTagDTO genderTagDTO = genderTagMapper.toDto(genderTag);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGenderTagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, genderTagDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(genderTagDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GenderTag in the database
        List<GenderTag> genderTagList = genderTagRepository.findAll();
        assertThat(genderTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGenderTag() throws Exception {
        int databaseSizeBeforeUpdate = genderTagRepository.findAll().size();
        genderTag.setId(UUID.randomUUID());

        // Create the GenderTag
        GenderTagDTO genderTagDTO = genderTagMapper.toDto(genderTag);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGenderTagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(genderTagDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GenderTag in the database
        List<GenderTag> genderTagList = genderTagRepository.findAll();
        assertThat(genderTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGenderTag() throws Exception {
        int databaseSizeBeforeUpdate = genderTagRepository.findAll().size();
        genderTag.setId(UUID.randomUUID());

        // Create the GenderTag
        GenderTagDTO genderTagDTO = genderTagMapper.toDto(genderTag);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGenderTagMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(genderTagDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GenderTag in the database
        List<GenderTag> genderTagList = genderTagRepository.findAll();
        assertThat(genderTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGenderTagWithPatch() throws Exception {
        // Initialize the database
        genderTagRepository.saveAndFlush(genderTag);

        int databaseSizeBeforeUpdate = genderTagRepository.findAll().size();

        // Update the genderTag using partial update
        GenderTag partialUpdatedGenderTag = new GenderTag();
        partialUpdatedGenderTag.setId(genderTag.getId());

        partialUpdatedGenderTag.state(UPDATED_STATE);

        restGenderTagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGenderTag.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGenderTag))
            )
            .andExpect(status().isOk());

        // Validate the GenderTag in the database
        List<GenderTag> genderTagList = genderTagRepository.findAll();
        assertThat(genderTagList).hasSize(databaseSizeBeforeUpdate);
        GenderTag testGenderTag = genderTagList.get(genderTagList.size() - 1);
        assertThat(testGenderTag.getHashtag()).isEqualTo(DEFAULT_HASHTAG);
        assertThat(testGenderTag.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testGenderTag.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    void fullUpdateGenderTagWithPatch() throws Exception {
        // Initialize the database
        genderTagRepository.saveAndFlush(genderTag);

        int databaseSizeBeforeUpdate = genderTagRepository.findAll().size();

        // Update the genderTag using partial update
        GenderTag partialUpdatedGenderTag = new GenderTag();
        partialUpdatedGenderTag.setId(genderTag.getId());

        partialUpdatedGenderTag.hashtag(UPDATED_HASHTAG).gender(UPDATED_GENDER).state(UPDATED_STATE);

        restGenderTagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGenderTag.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGenderTag))
            )
            .andExpect(status().isOk());

        // Validate the GenderTag in the database
        List<GenderTag> genderTagList = genderTagRepository.findAll();
        assertThat(genderTagList).hasSize(databaseSizeBeforeUpdate);
        GenderTag testGenderTag = genderTagList.get(genderTagList.size() - 1);
        assertThat(testGenderTag.getHashtag()).isEqualTo(UPDATED_HASHTAG);
        assertThat(testGenderTag.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testGenderTag.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    void patchNonExistingGenderTag() throws Exception {
        int databaseSizeBeforeUpdate = genderTagRepository.findAll().size();
        genderTag.setId(UUID.randomUUID());

        // Create the GenderTag
        GenderTagDTO genderTagDTO = genderTagMapper.toDto(genderTag);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGenderTagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, genderTagDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(genderTagDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GenderTag in the database
        List<GenderTag> genderTagList = genderTagRepository.findAll();
        assertThat(genderTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGenderTag() throws Exception {
        int databaseSizeBeforeUpdate = genderTagRepository.findAll().size();
        genderTag.setId(UUID.randomUUID());

        // Create the GenderTag
        GenderTagDTO genderTagDTO = genderTagMapper.toDto(genderTag);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGenderTagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(genderTagDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GenderTag in the database
        List<GenderTag> genderTagList = genderTagRepository.findAll();
        assertThat(genderTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGenderTag() throws Exception {
        int databaseSizeBeforeUpdate = genderTagRepository.findAll().size();
        genderTag.setId(UUID.randomUUID());

        // Create the GenderTag
        GenderTagDTO genderTagDTO = genderTagMapper.toDto(genderTag);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGenderTagMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(genderTagDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GenderTag in the database
        List<GenderTag> genderTagList = genderTagRepository.findAll();
        assertThat(genderTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGenderTag() throws Exception {
        // Initialize the database
        genderTagRepository.saveAndFlush(genderTag);

        int databaseSizeBeforeDelete = genderTagRepository.findAll().size();

        // Delete the genderTag
        restGenderTagMockMvc
            .perform(delete(ENTITY_API_URL_ID, genderTag.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GenderTag> genderTagList = genderTagRepository.findAll();
        assertThat(genderTagList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
