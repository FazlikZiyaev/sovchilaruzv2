package uz.devops.sovchilaruzv2.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import uz.devops.sovchilaruzv2.IntegrationTest;
import uz.devops.sovchilaruzv2.domain.Profile;
import uz.devops.sovchilaruzv2.domain.enumeration.ChildPlan;
import uz.devops.sovchilaruzv2.domain.enumeration.Education;
import uz.devops.sovchilaruzv2.domain.enumeration.Gender;
import uz.devops.sovchilaruzv2.domain.enumeration.MaritalStatus;
import uz.devops.sovchilaruzv2.domain.enumeration.ProfileState;
import uz.devops.sovchilaruzv2.repository.ProfileRepository;
import uz.devops.sovchilaruzv2.service.ProfileService;
import uz.devops.sovchilaruzv2.service.dto.ProfileDTO;
import uz.devops.sovchilaruzv2.service.mapper.ProfileMapper;

/**
 * Integration tests for the {@link ProfileResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProfileResourceIT {

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final Integer DEFAULT_AGE = 18;
    private static final Integer UPDATED_AGE = 19;

    private static final Double DEFAULT_HEIGHT = 0.0D;
    private static final Double UPDATED_HEIGHT = 1D;

    private static final Double DEFAULT_WEIGHT = 0.0D;
    private static final Double UPDATED_WEIGHT = 1D;

    private static final Education DEFAULT_EDUCATION = Education.PRIMARY_EDUCATION;
    private static final Education UPDATED_EDUCATION = Education.SECONDARY_EDUCATION;

    private static final String DEFAULT_PROFESSION = "AAAAAAAAAA";
    private static final String UPDATED_PROFESSION = "BBBBBBBBBB";

    private static final String DEFAULT_WORK_PLACE = "AAAAAAAAAA";
    private static final String UPDATED_WORK_PLACE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_HEALTHY = false;
    private static final Boolean UPDATED_IS_HEALTHY = true;

    private static final String DEFAULT_HEALTH_ISSUES = "AAAAAAAAAA";
    private static final String UPDATED_HEALTH_ISSUES = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PLACE_OF_BIRTH = "AAAAAAAAAA";
    private static final String UPDATED_PLACE_OF_BIRTH = "BBBBBBBBBB";

    private static final MaritalStatus DEFAULT_MARITAL_STATUS = MaritalStatus.UNMARRIED;
    private static final MaritalStatus UPDATED_MARITAL_STATUS = MaritalStatus.DIVORCED_THROUGH_COURT;

    private static final ChildPlan DEFAULT_CHILD_PLAN_IN_FUTURE = ChildPlan.WANT_CHILDREN;
    private static final ChildPlan UPDATED_CHILD_PLAN_IN_FUTURE = ChildPlan.DONT_WANT_CHILDREN;

    private static final Integer DEFAULT_NUM_OF_MEMBERS_IN_FAMILY = 1;
    private static final Integer UPDATED_NUM_OF_MEMBERS_IN_FAMILY = 2;

    private static final Integer DEFAULT_NUM_OF_CHILDREN_IN_FAMILY = 1;
    private static final Integer UPDATED_NUM_OF_CHILDREN_IN_FAMILY = 2;

    private static final Integer DEFAULT_BIRTH_POSITION_IN_FAMILY = 1;
    private static final Integer UPDATED_BIRTH_POSITION_IN_FAMILY = 2;

    private static final Boolean DEFAULT_HAS_OWN_DWELLING = false;
    private static final Boolean UPDATED_HAS_OWN_DWELLING = true;

    private static final Boolean DEFAULT_WANT_TO_STUDY = false;
    private static final Boolean UPDATED_WANT_TO_STUDY = true;

    private static final Boolean DEFAULT_WANT_TO_WORK = false;
    private static final Boolean UPDATED_WANT_TO_WORK = true;

    private static final Boolean DEFAULT_READY_TO_RELOCATE = false;
    private static final Boolean UPDATED_READY_TO_RELOCATE = true;

    private static final String DEFAULT_KNOWLEDGE_OF_LANGUAGES = "AAAAAAAAAA";
    private static final String UPDATED_KNOWLEDGE_OF_LANGUAGES = "BBBBBBBBBB";

    private static final String DEFAULT_SKILLS = "AAAAAAAAAA";
    private static final String UPDATED_SKILLS = "BBBBBBBBBB";

    private static final String DEFAULT_BIO = "AAAAAAAAAA";
    private static final String UPDATED_BIO = "BBBBBBBBBB";

    private static final String DEFAULT_REQUIREMENTS = "AAAAAAAAAA";
    private static final String UPDATED_REQUIREMENTS = "BBBBBBBBBB";

    private static final ProfileState DEFAULT_PROFILE_STATE = ProfileState.NEW;
    private static final ProfileState UPDATED_PROFILE_STATE = ProfileState.ACCEPTED;

    private static final String ENTITY_API_URL = "/api/profiles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ProfileRepository profileRepository;

    @Mock
    private ProfileRepository profileRepositoryMock;

    @Autowired
    private ProfileMapper profileMapper;

    @Mock
    private ProfileService profileServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProfileMockMvc;

    private Profile profile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profile createEntity(EntityManager em) {
        Profile profile = new Profile()
            .gender(DEFAULT_GENDER)
            .age(DEFAULT_AGE)
            .height(DEFAULT_HEIGHT)
            .weight(DEFAULT_WEIGHT)
            .education(DEFAULT_EDUCATION)
            .profession(DEFAULT_PROFESSION)
            .workPlace(DEFAULT_WORK_PLACE)
            .isHealthy(DEFAULT_IS_HEALTHY)
            .healthIssues(DEFAULT_HEALTH_ISSUES)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .placeOfBirth(DEFAULT_PLACE_OF_BIRTH)
            .maritalStatus(DEFAULT_MARITAL_STATUS)
            .childPlanInFuture(DEFAULT_CHILD_PLAN_IN_FUTURE)
            .numOfMembersInFamily(DEFAULT_NUM_OF_MEMBERS_IN_FAMILY)
            .numOfChildrenInFamily(DEFAULT_NUM_OF_CHILDREN_IN_FAMILY)
            .birthPositionInFamily(DEFAULT_BIRTH_POSITION_IN_FAMILY)
            .hasOwnDwelling(DEFAULT_HAS_OWN_DWELLING)
            .wantToStudy(DEFAULT_WANT_TO_STUDY)
            .wantToWork(DEFAULT_WANT_TO_WORK)
            .readyToRelocate(DEFAULT_READY_TO_RELOCATE)
            .knowledgeOfLanguages(DEFAULT_KNOWLEDGE_OF_LANGUAGES)
            .skills(DEFAULT_SKILLS)
            .bio(DEFAULT_BIO)
            .requirements(DEFAULT_REQUIREMENTS)
            .profileState(DEFAULT_PROFILE_STATE);
        return profile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profile createUpdatedEntity(EntityManager em) {
        Profile profile = new Profile()
            .gender(UPDATED_GENDER)
            .age(UPDATED_AGE)
            .height(UPDATED_HEIGHT)
            .weight(UPDATED_WEIGHT)
            .education(UPDATED_EDUCATION)
            .profession(UPDATED_PROFESSION)
            .workPlace(UPDATED_WORK_PLACE)
            .isHealthy(UPDATED_IS_HEALTHY)
            .healthIssues(UPDATED_HEALTH_ISSUES)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .placeOfBirth(UPDATED_PLACE_OF_BIRTH)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .childPlanInFuture(UPDATED_CHILD_PLAN_IN_FUTURE)
            .numOfMembersInFamily(UPDATED_NUM_OF_MEMBERS_IN_FAMILY)
            .numOfChildrenInFamily(UPDATED_NUM_OF_CHILDREN_IN_FAMILY)
            .birthPositionInFamily(UPDATED_BIRTH_POSITION_IN_FAMILY)
            .hasOwnDwelling(UPDATED_HAS_OWN_DWELLING)
            .wantToStudy(UPDATED_WANT_TO_STUDY)
            .wantToWork(UPDATED_WANT_TO_WORK)
            .readyToRelocate(UPDATED_READY_TO_RELOCATE)
            .knowledgeOfLanguages(UPDATED_KNOWLEDGE_OF_LANGUAGES)
            .skills(UPDATED_SKILLS)
            .bio(UPDATED_BIO)
            .requirements(UPDATED_REQUIREMENTS)
            .profileState(UPDATED_PROFILE_STATE);
        return profile;
    }

    @BeforeEach
    public void initTest() {
        profile = createEntity(em);
    }

    @Test
    @Transactional
    void createProfile() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();
        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);
        restProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isCreated());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate + 1);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testProfile.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testProfile.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testProfile.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testProfile.getEducation()).isEqualTo(DEFAULT_EDUCATION);
        assertThat(testProfile.getProfession()).isEqualTo(DEFAULT_PROFESSION);
        assertThat(testProfile.getWorkPlace()).isEqualTo(DEFAULT_WORK_PLACE);
        assertThat(testProfile.getIsHealthy()).isEqualTo(DEFAULT_IS_HEALTHY);
        assertThat(testProfile.getHealthIssues()).isEqualTo(DEFAULT_HEALTH_ISSUES);
        assertThat(testProfile.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testProfile.getPlaceOfBirth()).isEqualTo(DEFAULT_PLACE_OF_BIRTH);
        assertThat(testProfile.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testProfile.getChildPlanInFuture()).isEqualTo(DEFAULT_CHILD_PLAN_IN_FUTURE);
        assertThat(testProfile.getNumOfMembersInFamily()).isEqualTo(DEFAULT_NUM_OF_MEMBERS_IN_FAMILY);
        assertThat(testProfile.getNumOfChildrenInFamily()).isEqualTo(DEFAULT_NUM_OF_CHILDREN_IN_FAMILY);
        assertThat(testProfile.getBirthPositionInFamily()).isEqualTo(DEFAULT_BIRTH_POSITION_IN_FAMILY);
        assertThat(testProfile.getHasOwnDwelling()).isEqualTo(DEFAULT_HAS_OWN_DWELLING);
        assertThat(testProfile.getWantToStudy()).isEqualTo(DEFAULT_WANT_TO_STUDY);
        assertThat(testProfile.getWantToWork()).isEqualTo(DEFAULT_WANT_TO_WORK);
        assertThat(testProfile.getReadyToRelocate()).isEqualTo(DEFAULT_READY_TO_RELOCATE);
        assertThat(testProfile.getKnowledgeOfLanguages()).isEqualTo(DEFAULT_KNOWLEDGE_OF_LANGUAGES);
        assertThat(testProfile.getSkills()).isEqualTo(DEFAULT_SKILLS);
        assertThat(testProfile.getBio()).isEqualTo(DEFAULT_BIO);
        assertThat(testProfile.getRequirements()).isEqualTo(DEFAULT_REQUIREMENTS);
        assertThat(testProfile.getProfileState()).isEqualTo(DEFAULT_PROFILE_STATE);
    }

    @Test
    @Transactional
    void createProfileWithExistingId() throws Exception {
        // Create the Profile with an existing ID
        profileRepository.saveAndFlush(profile);
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setGender(null);

        // Create the Profile, which fails.
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        restProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setAge(null);

        // Create the Profile, which fails.
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        restProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setHeight(null);

        // Create the Profile, which fails.
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        restProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setWeight(null);

        // Create the Profile, which fails.
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        restProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEducationIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setEducation(null);

        // Create the Profile, which fails.
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        restProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsHealthyIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setIsHealthy(null);

        // Create the Profile, which fails.
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        restProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateOfBirthIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setDateOfBirth(null);

        // Create the Profile, which fails.
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        restProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPlaceOfBirthIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setPlaceOfBirth(null);

        // Create the Profile, which fails.
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        restProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMaritalStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setMaritalStatus(null);

        // Create the Profile, which fails.
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        restProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProfiles() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList
        restProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].education").value(hasItem(DEFAULT_EDUCATION.toString())))
            .andExpect(jsonPath("$.[*].profession").value(hasItem(DEFAULT_PROFESSION)))
            .andExpect(jsonPath("$.[*].workPlace").value(hasItem(DEFAULT_WORK_PLACE)))
            .andExpect(jsonPath("$.[*].isHealthy").value(hasItem(DEFAULT_IS_HEALTHY.booleanValue())))
            .andExpect(jsonPath("$.[*].healthIssues").value(hasItem(DEFAULT_HEALTH_ISSUES)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].placeOfBirth").value(hasItem(DEFAULT_PLACE_OF_BIRTH)))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].childPlanInFuture").value(hasItem(DEFAULT_CHILD_PLAN_IN_FUTURE.toString())))
            .andExpect(jsonPath("$.[*].numOfMembersInFamily").value(hasItem(DEFAULT_NUM_OF_MEMBERS_IN_FAMILY)))
            .andExpect(jsonPath("$.[*].numOfChildrenInFamily").value(hasItem(DEFAULT_NUM_OF_CHILDREN_IN_FAMILY)))
            .andExpect(jsonPath("$.[*].birthPositionInFamily").value(hasItem(DEFAULT_BIRTH_POSITION_IN_FAMILY)))
            .andExpect(jsonPath("$.[*].hasOwnDwelling").value(hasItem(DEFAULT_HAS_OWN_DWELLING.booleanValue())))
            .andExpect(jsonPath("$.[*].wantToStudy").value(hasItem(DEFAULT_WANT_TO_STUDY.booleanValue())))
            .andExpect(jsonPath("$.[*].wantToWork").value(hasItem(DEFAULT_WANT_TO_WORK.booleanValue())))
            .andExpect(jsonPath("$.[*].readyToRelocate").value(hasItem(DEFAULT_READY_TO_RELOCATE.booleanValue())))
            .andExpect(jsonPath("$.[*].knowledgeOfLanguages").value(hasItem(DEFAULT_KNOWLEDGE_OF_LANGUAGES)))
            .andExpect(jsonPath("$.[*].skills").value(hasItem(DEFAULT_SKILLS)))
            .andExpect(jsonPath("$.[*].bio").value(hasItem(DEFAULT_BIO)))
            .andExpect(jsonPath("$.[*].requirements").value(hasItem(DEFAULT_REQUIREMENTS)))
            .andExpect(jsonPath("$.[*].profileState").value(hasItem(DEFAULT_PROFILE_STATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProfilesWithEagerRelationshipsIsEnabled() throws Exception {
        when(profileServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProfileMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(profileServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProfilesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(profileServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProfileMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(profileRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get the profile
        restProfileMockMvc
            .perform(get(ENTITY_API_URL_ID, profile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(profile.getId().toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT.doubleValue()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.education").value(DEFAULT_EDUCATION.toString()))
            .andExpect(jsonPath("$.profession").value(DEFAULT_PROFESSION))
            .andExpect(jsonPath("$.workPlace").value(DEFAULT_WORK_PLACE))
            .andExpect(jsonPath("$.isHealthy").value(DEFAULT_IS_HEALTHY.booleanValue()))
            .andExpect(jsonPath("$.healthIssues").value(DEFAULT_HEALTH_ISSUES))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.placeOfBirth").value(DEFAULT_PLACE_OF_BIRTH))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS.toString()))
            .andExpect(jsonPath("$.childPlanInFuture").value(DEFAULT_CHILD_PLAN_IN_FUTURE.toString()))
            .andExpect(jsonPath("$.numOfMembersInFamily").value(DEFAULT_NUM_OF_MEMBERS_IN_FAMILY))
            .andExpect(jsonPath("$.numOfChildrenInFamily").value(DEFAULT_NUM_OF_CHILDREN_IN_FAMILY))
            .andExpect(jsonPath("$.birthPositionInFamily").value(DEFAULT_BIRTH_POSITION_IN_FAMILY))
            .andExpect(jsonPath("$.hasOwnDwelling").value(DEFAULT_HAS_OWN_DWELLING.booleanValue()))
            .andExpect(jsonPath("$.wantToStudy").value(DEFAULT_WANT_TO_STUDY.booleanValue()))
            .andExpect(jsonPath("$.wantToWork").value(DEFAULT_WANT_TO_WORK.booleanValue()))
            .andExpect(jsonPath("$.readyToRelocate").value(DEFAULT_READY_TO_RELOCATE.booleanValue()))
            .andExpect(jsonPath("$.knowledgeOfLanguages").value(DEFAULT_KNOWLEDGE_OF_LANGUAGES))
            .andExpect(jsonPath("$.skills").value(DEFAULT_SKILLS))
            .andExpect(jsonPath("$.bio").value(DEFAULT_BIO))
            .andExpect(jsonPath("$.requirements").value(DEFAULT_REQUIREMENTS))
            .andExpect(jsonPath("$.profileState").value(DEFAULT_PROFILE_STATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProfile() throws Exception {
        // Get the profile
        restProfileMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Update the profile
        Profile updatedProfile = profileRepository.findById(profile.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProfile are not directly saved in db
        em.detach(updatedProfile);
        updatedProfile
            .gender(UPDATED_GENDER)
            .age(UPDATED_AGE)
            .height(UPDATED_HEIGHT)
            .weight(UPDATED_WEIGHT)
            .education(UPDATED_EDUCATION)
            .profession(UPDATED_PROFESSION)
            .workPlace(UPDATED_WORK_PLACE)
            .isHealthy(UPDATED_IS_HEALTHY)
            .healthIssues(UPDATED_HEALTH_ISSUES)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .placeOfBirth(UPDATED_PLACE_OF_BIRTH)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .childPlanInFuture(UPDATED_CHILD_PLAN_IN_FUTURE)
            .numOfMembersInFamily(UPDATED_NUM_OF_MEMBERS_IN_FAMILY)
            .numOfChildrenInFamily(UPDATED_NUM_OF_CHILDREN_IN_FAMILY)
            .birthPositionInFamily(UPDATED_BIRTH_POSITION_IN_FAMILY)
            .hasOwnDwelling(UPDATED_HAS_OWN_DWELLING)
            .wantToStudy(UPDATED_WANT_TO_STUDY)
            .wantToWork(UPDATED_WANT_TO_WORK)
            .readyToRelocate(UPDATED_READY_TO_RELOCATE)
            .knowledgeOfLanguages(UPDATED_KNOWLEDGE_OF_LANGUAGES)
            .skills(UPDATED_SKILLS)
            .bio(UPDATED_BIO)
            .requirements(UPDATED_REQUIREMENTS)
            .profileState(UPDATED_PROFILE_STATE);
        ProfileDTO profileDTO = profileMapper.toDto(updatedProfile);

        restProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, profileDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profileDTO))
            )
            .andExpect(status().isOk());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testProfile.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testProfile.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testProfile.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testProfile.getEducation()).isEqualTo(UPDATED_EDUCATION);
        assertThat(testProfile.getProfession()).isEqualTo(UPDATED_PROFESSION);
        assertThat(testProfile.getWorkPlace()).isEqualTo(UPDATED_WORK_PLACE);
        assertThat(testProfile.getIsHealthy()).isEqualTo(UPDATED_IS_HEALTHY);
        assertThat(testProfile.getHealthIssues()).isEqualTo(UPDATED_HEALTH_ISSUES);
        assertThat(testProfile.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testProfile.getPlaceOfBirth()).isEqualTo(UPDATED_PLACE_OF_BIRTH);
        assertThat(testProfile.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testProfile.getChildPlanInFuture()).isEqualTo(UPDATED_CHILD_PLAN_IN_FUTURE);
        assertThat(testProfile.getNumOfMembersInFamily()).isEqualTo(UPDATED_NUM_OF_MEMBERS_IN_FAMILY);
        assertThat(testProfile.getNumOfChildrenInFamily()).isEqualTo(UPDATED_NUM_OF_CHILDREN_IN_FAMILY);
        assertThat(testProfile.getBirthPositionInFamily()).isEqualTo(UPDATED_BIRTH_POSITION_IN_FAMILY);
        assertThat(testProfile.getHasOwnDwelling()).isEqualTo(UPDATED_HAS_OWN_DWELLING);
        assertThat(testProfile.getWantToStudy()).isEqualTo(UPDATED_WANT_TO_STUDY);
        assertThat(testProfile.getWantToWork()).isEqualTo(UPDATED_WANT_TO_WORK);
        assertThat(testProfile.getReadyToRelocate()).isEqualTo(UPDATED_READY_TO_RELOCATE);
        assertThat(testProfile.getKnowledgeOfLanguages()).isEqualTo(UPDATED_KNOWLEDGE_OF_LANGUAGES);
        assertThat(testProfile.getSkills()).isEqualTo(UPDATED_SKILLS);
        assertThat(testProfile.getBio()).isEqualTo(UPDATED_BIO);
        assertThat(testProfile.getRequirements()).isEqualTo(UPDATED_REQUIREMENTS);
        assertThat(testProfile.getProfileState()).isEqualTo(UPDATED_PROFILE_STATE);
    }

    @Test
    @Transactional
    void putNonExistingProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();
        profile.setId(UUID.randomUUID());

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, profileDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();
        profile.setId(UUID.randomUUID());

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();
        profile.setId(UUID.randomUUID());

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfileMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProfileWithPatch() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Update the profile using partial update
        Profile partialUpdatedProfile = new Profile();
        partialUpdatedProfile.setId(profile.getId());

        partialUpdatedProfile
            .gender(UPDATED_GENDER)
            .workPlace(UPDATED_WORK_PLACE)
            .isHealthy(UPDATED_IS_HEALTHY)
            .numOfMembersInFamily(UPDATED_NUM_OF_MEMBERS_IN_FAMILY)
            .wantToStudy(UPDATED_WANT_TO_STUDY)
            .wantToWork(UPDATED_WANT_TO_WORK)
            .skills(UPDATED_SKILLS)
            .bio(UPDATED_BIO);

        restProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProfile))
            )
            .andExpect(status().isOk());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testProfile.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testProfile.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testProfile.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testProfile.getEducation()).isEqualTo(DEFAULT_EDUCATION);
        assertThat(testProfile.getProfession()).isEqualTo(DEFAULT_PROFESSION);
        assertThat(testProfile.getWorkPlace()).isEqualTo(UPDATED_WORK_PLACE);
        assertThat(testProfile.getIsHealthy()).isEqualTo(UPDATED_IS_HEALTHY);
        assertThat(testProfile.getHealthIssues()).isEqualTo(DEFAULT_HEALTH_ISSUES);
        assertThat(testProfile.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testProfile.getPlaceOfBirth()).isEqualTo(DEFAULT_PLACE_OF_BIRTH);
        assertThat(testProfile.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testProfile.getChildPlanInFuture()).isEqualTo(DEFAULT_CHILD_PLAN_IN_FUTURE);
        assertThat(testProfile.getNumOfMembersInFamily()).isEqualTo(UPDATED_NUM_OF_MEMBERS_IN_FAMILY);
        assertThat(testProfile.getNumOfChildrenInFamily()).isEqualTo(DEFAULT_NUM_OF_CHILDREN_IN_FAMILY);
        assertThat(testProfile.getBirthPositionInFamily()).isEqualTo(DEFAULT_BIRTH_POSITION_IN_FAMILY);
        assertThat(testProfile.getHasOwnDwelling()).isEqualTo(DEFAULT_HAS_OWN_DWELLING);
        assertThat(testProfile.getWantToStudy()).isEqualTo(UPDATED_WANT_TO_STUDY);
        assertThat(testProfile.getWantToWork()).isEqualTo(UPDATED_WANT_TO_WORK);
        assertThat(testProfile.getReadyToRelocate()).isEqualTo(DEFAULT_READY_TO_RELOCATE);
        assertThat(testProfile.getKnowledgeOfLanguages()).isEqualTo(DEFAULT_KNOWLEDGE_OF_LANGUAGES);
        assertThat(testProfile.getSkills()).isEqualTo(UPDATED_SKILLS);
        assertThat(testProfile.getBio()).isEqualTo(UPDATED_BIO);
        assertThat(testProfile.getRequirements()).isEqualTo(DEFAULT_REQUIREMENTS);
        assertThat(testProfile.getProfileState()).isEqualTo(DEFAULT_PROFILE_STATE);
    }

    @Test
    @Transactional
    void fullUpdateProfileWithPatch() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Update the profile using partial update
        Profile partialUpdatedProfile = new Profile();
        partialUpdatedProfile.setId(profile.getId());

        partialUpdatedProfile
            .gender(UPDATED_GENDER)
            .age(UPDATED_AGE)
            .height(UPDATED_HEIGHT)
            .weight(UPDATED_WEIGHT)
            .education(UPDATED_EDUCATION)
            .profession(UPDATED_PROFESSION)
            .workPlace(UPDATED_WORK_PLACE)
            .isHealthy(UPDATED_IS_HEALTHY)
            .healthIssues(UPDATED_HEALTH_ISSUES)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .placeOfBirth(UPDATED_PLACE_OF_BIRTH)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .childPlanInFuture(UPDATED_CHILD_PLAN_IN_FUTURE)
            .numOfMembersInFamily(UPDATED_NUM_OF_MEMBERS_IN_FAMILY)
            .numOfChildrenInFamily(UPDATED_NUM_OF_CHILDREN_IN_FAMILY)
            .birthPositionInFamily(UPDATED_BIRTH_POSITION_IN_FAMILY)
            .hasOwnDwelling(UPDATED_HAS_OWN_DWELLING)
            .wantToStudy(UPDATED_WANT_TO_STUDY)
            .wantToWork(UPDATED_WANT_TO_WORK)
            .readyToRelocate(UPDATED_READY_TO_RELOCATE)
            .knowledgeOfLanguages(UPDATED_KNOWLEDGE_OF_LANGUAGES)
            .skills(UPDATED_SKILLS)
            .bio(UPDATED_BIO)
            .requirements(UPDATED_REQUIREMENTS)
            .profileState(UPDATED_PROFILE_STATE);

        restProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProfile))
            )
            .andExpect(status().isOk());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testProfile.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testProfile.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testProfile.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testProfile.getEducation()).isEqualTo(UPDATED_EDUCATION);
        assertThat(testProfile.getProfession()).isEqualTo(UPDATED_PROFESSION);
        assertThat(testProfile.getWorkPlace()).isEqualTo(UPDATED_WORK_PLACE);
        assertThat(testProfile.getIsHealthy()).isEqualTo(UPDATED_IS_HEALTHY);
        assertThat(testProfile.getHealthIssues()).isEqualTo(UPDATED_HEALTH_ISSUES);
        assertThat(testProfile.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testProfile.getPlaceOfBirth()).isEqualTo(UPDATED_PLACE_OF_BIRTH);
        assertThat(testProfile.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testProfile.getChildPlanInFuture()).isEqualTo(UPDATED_CHILD_PLAN_IN_FUTURE);
        assertThat(testProfile.getNumOfMembersInFamily()).isEqualTo(UPDATED_NUM_OF_MEMBERS_IN_FAMILY);
        assertThat(testProfile.getNumOfChildrenInFamily()).isEqualTo(UPDATED_NUM_OF_CHILDREN_IN_FAMILY);
        assertThat(testProfile.getBirthPositionInFamily()).isEqualTo(UPDATED_BIRTH_POSITION_IN_FAMILY);
        assertThat(testProfile.getHasOwnDwelling()).isEqualTo(UPDATED_HAS_OWN_DWELLING);
        assertThat(testProfile.getWantToStudy()).isEqualTo(UPDATED_WANT_TO_STUDY);
        assertThat(testProfile.getWantToWork()).isEqualTo(UPDATED_WANT_TO_WORK);
        assertThat(testProfile.getReadyToRelocate()).isEqualTo(UPDATED_READY_TO_RELOCATE);
        assertThat(testProfile.getKnowledgeOfLanguages()).isEqualTo(UPDATED_KNOWLEDGE_OF_LANGUAGES);
        assertThat(testProfile.getSkills()).isEqualTo(UPDATED_SKILLS);
        assertThat(testProfile.getBio()).isEqualTo(UPDATED_BIO);
        assertThat(testProfile.getRequirements()).isEqualTo(UPDATED_REQUIREMENTS);
        assertThat(testProfile.getProfileState()).isEqualTo(UPDATED_PROFILE_STATE);
    }

    @Test
    @Transactional
    void patchNonExistingProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();
        profile.setId(UUID.randomUUID());

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, profileDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(profileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();
        profile.setId(UUID.randomUUID());

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(profileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();
        profile.setId(UUID.randomUUID());

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfileMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(profileDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeDelete = profileRepository.findAll().size();

        // Delete the profile
        restProfileMockMvc
            .perform(delete(ENTITY_API_URL_ID, profile.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
