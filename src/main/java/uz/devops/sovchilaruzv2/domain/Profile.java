package uz.devops.sovchilaruzv2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uz.devops.sovchilaruzv2.domain.enumeration.ChildPlan;
import uz.devops.sovchilaruzv2.domain.enumeration.Education;
import uz.devops.sovchilaruzv2.domain.enumeration.Gender;
import uz.devops.sovchilaruzv2.domain.enumeration.MaritalStatus;
import uz.devops.sovchilaruzv2.domain.enumeration.ProfileState;

/**
 * A Profile.
 */
@Entity
@Table(name = "profile")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @NotNull
    @Min(value = 18)
    @Max(value = 99)
    @Column(name = "age", nullable = false)
    private Integer age;

    @NotNull
    @DecimalMin(value = "0.0")
    @Column(name = "height", nullable = false)
    private Double height;

    @NotNull
    @DecimalMin(value = "0.0")
    @Column(name = "weight", nullable = false)
    private Double weight;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "education", nullable = false)
    private Education education;

    @Size(max = 256)
    @Column(name = "profession", length = 256)
    private String profession;

    @Size(min = 1)
    @Column(name = "work_place")
    private String workPlace;

    @NotNull
    @Column(name = "is_healthy", nullable = false)
    private Boolean isHealthy;

    @Column(name = "health_issues")
    private String healthIssues;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @NotNull
    @Size(min = 1)
    @Column(name = "place_of_birth", nullable = false)
    private String placeOfBirth;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status", nullable = false)
    private MaritalStatus maritalStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "child_plan_in_future")
    private ChildPlan childPlanInFuture;

    @Min(value = 1)
    @Max(value = 20)
    @Column(name = "num_of_members_in_family")
    private Integer numOfMembersInFamily;

    @Min(value = 1)
    @Max(value = 20)
    @Column(name = "num_of_children_in_family")
    private Integer numOfChildrenInFamily;

    @Min(value = 1)
    @Max(value = 20)
    @Column(name = "birth_position_in_family")
    private Integer birthPositionInFamily;

    @Column(name = "has_own_dwelling")
    private Boolean hasOwnDwelling;

    @Column(name = "want_to_study")
    private Boolean wantToStudy;

    @Column(name = "want_to_work")
    private Boolean wantToWork;

    @Column(name = "ready_to_relocate")
    private Boolean readyToRelocate;

    @Column(name = "knowledge_of_languages")
    private String knowledgeOfLanguages;

    @Size(max = 256)
    @Column(name = "skills", length = 256)
    private String skills;

    @Size(max = 400)
    @Column(name = "bio", length = 400)
    private String bio;

    @Size(max = 256)
    @Column(name = "requirements", length = 256)
    private String requirements;

    @Enumerated(EnumType.STRING)
    @Column(name = "profile_state")
    private ProfileState profileState;

    // Table relation
    @OneToOne(mappedBy = "profile")
    private User user;

    @JsonIgnoreProperties(value = { "profile" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Location location;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "profile")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "profile" }, allowSetters = true)
    private Set<ContactInfo> contactInfos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "profile")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "profile" }, allowSetters = true)
    private Set<Attachment> attachments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private ProfileDiscoverability discoverability;

    @ManyToOne(fetch = FetchType.LAZY)
    private Nationality nationality;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_profile__gender_tags",
        joinColumns = @JoinColumn(name = "profile_id"),
        inverseJoinColumns = @JoinColumn(name = "gender_tags_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "profiles" }, allowSetters = true)
    private Set<GenderTag> genderTags = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Profile id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Gender getGender() {
        return this.gender;
    }

    public Profile gender(Gender gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return this.age;
    }

    public Profile age(Integer age) {
        this.setAge(age);
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getHeight() {
        return this.height;
    }

    public Profile height(Double height) {
        this.setHeight(height);
        return this;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return this.weight;
    }

    public Profile weight(Double weight) {
        this.setWeight(weight);
        return this;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Education getEducation() {
        return this.education;
    }

    public Profile education(Education education) {
        this.setEducation(education);
        return this;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public String getProfession() {
        return this.profession;
    }

    public Profile profession(String profession) {
        this.setProfession(profession);
        return this;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getWorkPlace() {
        return this.workPlace;
    }

    public Profile workPlace(String workPlace) {
        this.setWorkPlace(workPlace);
        return this;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public Boolean getIsHealthy() {
        return this.isHealthy;
    }

    public Profile isHealthy(Boolean isHealthy) {
        this.setIsHealthy(isHealthy);
        return this;
    }

    public void setIsHealthy(Boolean isHealthy) {
        this.isHealthy = isHealthy;
    }

    public String getHealthIssues() {
        return this.healthIssues;
    }

    public Profile healthIssues(String healthIssues) {
        this.setHealthIssues(healthIssues);
        return this;
    }

    public void setHealthIssues(String healthIssues) {
        this.healthIssues = healthIssues;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public Profile dateOfBirth(LocalDate dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return this.placeOfBirth;
    }

    public Profile placeOfBirth(String placeOfBirth) {
        this.setPlaceOfBirth(placeOfBirth);
        return this;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public MaritalStatus getMaritalStatus() {
        return this.maritalStatus;
    }

    public Profile maritalStatus(MaritalStatus maritalStatus) {
        this.setMaritalStatus(maritalStatus);
        return this;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public ChildPlan getChildPlanInFuture() {
        return this.childPlanInFuture;
    }

    public Profile childPlanInFuture(ChildPlan childPlanInFuture) {
        this.setChildPlanInFuture(childPlanInFuture);
        return this;
    }

    public void setChildPlanInFuture(ChildPlan childPlanInFuture) {
        this.childPlanInFuture = childPlanInFuture;
    }

    public Integer getNumOfMembersInFamily() {
        return this.numOfMembersInFamily;
    }

    public Profile numOfMembersInFamily(Integer numOfMembersInFamily) {
        this.setNumOfMembersInFamily(numOfMembersInFamily);
        return this;
    }

    public void setNumOfMembersInFamily(Integer numOfMembersInFamily) {
        this.numOfMembersInFamily = numOfMembersInFamily;
    }

    public Integer getNumOfChildrenInFamily() {
        return this.numOfChildrenInFamily;
    }

    public Profile numOfChildrenInFamily(Integer numOfChildrenInFamily) {
        this.setNumOfChildrenInFamily(numOfChildrenInFamily);
        return this;
    }

    public void setNumOfChildrenInFamily(Integer numOfChildrenInFamily) {
        this.numOfChildrenInFamily = numOfChildrenInFamily;
    }

    public Integer getBirthPositionInFamily() {
        return this.birthPositionInFamily;
    }

    public Profile birthPositionInFamily(Integer birthPositionInFamily) {
        this.setBirthPositionInFamily(birthPositionInFamily);
        return this;
    }

    public void setBirthPositionInFamily(Integer birthPositionInFamily) {
        this.birthPositionInFamily = birthPositionInFamily;
    }

    public Boolean getHasOwnDwelling() {
        return this.hasOwnDwelling;
    }

    public Profile hasOwnDwelling(Boolean hasOwnDwelling) {
        this.setHasOwnDwelling(hasOwnDwelling);
        return this;
    }

    public void setHasOwnDwelling(Boolean hasOwnDwelling) {
        this.hasOwnDwelling = hasOwnDwelling;
    }

    public Boolean getWantToStudy() {
        return this.wantToStudy;
    }

    public Profile wantToStudy(Boolean wantToStudy) {
        this.setWantToStudy(wantToStudy);
        return this;
    }

    public void setWantToStudy(Boolean wantToStudy) {
        this.wantToStudy = wantToStudy;
    }

    public Boolean getWantToWork() {
        return this.wantToWork;
    }

    public Profile wantToWork(Boolean wantToWork) {
        this.setWantToWork(wantToWork);
        return this;
    }

    public void setWantToWork(Boolean wantToWork) {
        this.wantToWork = wantToWork;
    }

    public Boolean getReadyToRelocate() {
        return this.readyToRelocate;
    }

    public Profile readyToRelocate(Boolean readyToRelocate) {
        this.setReadyToRelocate(readyToRelocate);
        return this;
    }

    public void setReadyToRelocate(Boolean readyToRelocate) {
        this.readyToRelocate = readyToRelocate;
    }

    public String getKnowledgeOfLanguages() {
        return this.knowledgeOfLanguages;
    }

    public Profile knowledgeOfLanguages(String knowledgeOfLanguages) {
        this.setKnowledgeOfLanguages(knowledgeOfLanguages);
        return this;
    }

    public void setKnowledgeOfLanguages(String knowledgeOfLanguages) {
        this.knowledgeOfLanguages = knowledgeOfLanguages;
    }

    public String getSkills() {
        return this.skills;
    }

    public Profile skills(String skills) {
        this.setSkills(skills);
        return this;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getBio() {
        return this.bio;
    }

    public Profile bio(String bio) {
        this.setBio(bio);
        return this;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getRequirements() {
        return this.requirements;
    }

    public Profile requirements(String requirements) {
        this.setRequirements(requirements);
        return this;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public ProfileState getProfileState() {
        return this.profileState;
    }

    public Profile profileState(ProfileState profileState) {
        this.setProfileState(profileState);
        return this;
    }

    public void setProfileState(ProfileState profileState) {
        this.profileState = profileState;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Profile location(Location location) {
        this.setLocation(location);
        return this;
    }

    public Set<ContactInfo> getContactInfos() {
        return this.contactInfos;
    }

    public void setContactInfos(Set<ContactInfo> contactInfos) {
        if (this.contactInfos != null) {
            this.contactInfos.forEach(i -> i.setProfile(null));
        }
        if (contactInfos != null) {
            contactInfos.forEach(i -> i.setProfile(this));
        }
        this.contactInfos = contactInfos;
    }

    public Profile contactInfos(Set<ContactInfo> contactInfos) {
        this.setContactInfos(contactInfos);
        return this;
    }

    public Profile addContactInfos(ContactInfo contactInfo) {
        this.contactInfos.add(contactInfo);
        contactInfo.setProfile(this);
        return this;
    }

    public Profile removeContactInfos(ContactInfo contactInfo) {
        this.contactInfos.remove(contactInfo);
        contactInfo.setProfile(null);
        return this;
    }

    public Set<Attachment> getAttachments() {
        return this.attachments;
    }

    public void setAttachments(Set<Attachment> attachments) {
        if (this.attachments != null) {
            this.attachments.forEach(i -> i.setProfile(null));
        }
        if (attachments != null) {
            attachments.forEach(i -> i.setProfile(this));
        }
        this.attachments = attachments;
    }

    public Profile attachments(Set<Attachment> attachments) {
        this.setAttachments(attachments);
        return this;
    }

    public Profile addAttachments(Attachment attachment) {
        this.attachments.add(attachment);
        attachment.setProfile(this);
        return this;
    }

    public Profile removeAttachments(Attachment attachment) {
        this.attachments.remove(attachment);
        attachment.setProfile(null);
        return this;
    }

    public ProfileDiscoverability getDiscoverability() {
        return this.discoverability;
    }

    public void setDiscoverability(ProfileDiscoverability profileDiscoverability) {
        this.discoverability = profileDiscoverability;
    }

    public Profile discoverability(ProfileDiscoverability profileDiscoverability) {
        this.setDiscoverability(profileDiscoverability);
        return this;
    }

    public Nationality getNationality() {
        return this.nationality;
    }

    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }

    public Profile nationality(Nationality nationality) {
        this.setNationality(nationality);
        return this;
    }

    public Set<GenderTag> getGenderTags() {
        return this.genderTags;
    }

    public void setGenderTags(Set<GenderTag> genderTags) {
        this.genderTags = genderTags;
    }

    public Profile genderTags(Set<GenderTag> genderTags) {
        this.setGenderTags(genderTags);
        return this;
    }

    public Profile addGenderTags(GenderTag genderTag) {
        this.genderTags.add(genderTag);
        return this;
    }

    public Profile removeGenderTags(GenderTag genderTag) {
        this.genderTags.remove(genderTag);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profile)) {
            return false;
        }
        return getId() != null && getId().equals(((Profile) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Profile{" +
            "id=" + getId() +
            ", gender='" + getGender() + "'" +
            ", age=" + getAge() +
            ", height=" + getHeight() +
            ", weight=" + getWeight() +
            ", education='" + getEducation() + "'" +
            ", profession='" + getProfession() + "'" +
            ", workPlace='" + getWorkPlace() + "'" +
            ", isHealthy='" + getIsHealthy() + "'" +
            ", healthIssues='" + getHealthIssues() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", placeOfBirth='" + getPlaceOfBirth() + "'" +
            ", maritalStatus='" + getMaritalStatus() + "'" +
            ", childPlanInFuture='" + getChildPlanInFuture() + "'" +
            ", numOfMembersInFamily=" + getNumOfMembersInFamily() +
            ", numOfChildrenInFamily=" + getNumOfChildrenInFamily() +
            ", birthPositionInFamily=" + getBirthPositionInFamily() +
            ", hasOwnDwelling='" + getHasOwnDwelling() + "'" +
            ", wantToStudy='" + getWantToStudy() + "'" +
            ", wantToWork='" + getWantToWork() + "'" +
            ", readyToRelocate='" + getReadyToRelocate() + "'" +
            ", knowledgeOfLanguages='" + getKnowledgeOfLanguages() + "'" +
            ", skills='" + getSkills() + "'" +
            ", bio='" + getBio() + "'" +
            ", requirements='" + getRequirements() + "'" +
            ", profileState='" + getProfileState() + "'" +
            "}";
    }
}
