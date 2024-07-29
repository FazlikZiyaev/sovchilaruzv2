package uz.devops.sovchilaruzv2.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import uz.devops.sovchilaruzv2.domain.enumeration.ChildPlan;
import uz.devops.sovchilaruzv2.domain.enumeration.Education;
import uz.devops.sovchilaruzv2.domain.enumeration.Gender;
import uz.devops.sovchilaruzv2.domain.enumeration.MaritalStatus;
import uz.devops.sovchilaruzv2.domain.enumeration.ProfileState;

/**
 * A DTO for the {@link uz.devops.sovchilaruzv2.domain.Profile} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProfileDTO implements Serializable {

    private UUID id;

    @NotNull
    private UUID userId;

    @NotNull
    private Gender gender;

    @NotNull
    @Min(value = 18)
    @Max(value = 99)
    private Integer age;

    @NotNull
    @DecimalMin(value = "0.0")
    private Double height;

    @NotNull
    @DecimalMin(value = "0.0")
    private Double weight;

    @NotNull
    private Education education;

    @Size(max = 256)
    private String profession;

    @Size(min = 1)
    private String workPlace;

    @NotNull
    private Boolean isHealthy;

    private String healthIssues;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    @Size(min = 1)
    private String placeOfBirth;

    @NotNull
    private MaritalStatus maritalStatus;

    private ChildPlan childPlanInFuture;

    @Min(value = 1)
    @Max(value = 20)
    private Integer numOfMembersInFamily;

    @Min(value = 1)
    @Max(value = 20)
    private Integer numOfChildrenInFamily;

    @Min(value = 1)
    @Max(value = 20)
    private Integer birthPositionInFamily;

    private Boolean hasOwnDwelling;

    private Boolean wantToStudy;

    private Boolean wantToWork;

    private Boolean readyToRelocate;

    private String knowledgeOfLanguages;

    @Size(max = 256)
    private String skills;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @Size(max = 400)
    private String bio;

    @Size(max = 256)
    private String requirements;

    private ProfileState profileState;

    private LocationDTO location;

    private ProfileDiscoverabilityDTO discoverability;

    private NationalityDTO nationality;

    private Set<GenderTagDTO> genderTags = new HashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public Boolean getIsHealthy() {
        return isHealthy;
    }

    public void setIsHealthy(Boolean isHealthy) {
        this.isHealthy = isHealthy;
    }

    public String getHealthIssues() {
        return healthIssues;
    }

    public void setHealthIssues(String healthIssues) {
        this.healthIssues = healthIssues;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public ChildPlan getChildPlanInFuture() {
        return childPlanInFuture;
    }

    public void setChildPlanInFuture(ChildPlan childPlanInFuture) {
        this.childPlanInFuture = childPlanInFuture;
    }

    public Integer getNumOfMembersInFamily() {
        return numOfMembersInFamily;
    }

    public void setNumOfMembersInFamily(Integer numOfMembersInFamily) {
        this.numOfMembersInFamily = numOfMembersInFamily;
    }

    public Integer getNumOfChildrenInFamily() {
        return numOfChildrenInFamily;
    }

    public void setNumOfChildrenInFamily(Integer numOfChildrenInFamily) {
        this.numOfChildrenInFamily = numOfChildrenInFamily;
    }

    public Integer getBirthPositionInFamily() {
        return birthPositionInFamily;
    }

    public void setBirthPositionInFamily(Integer birthPositionInFamily) {
        this.birthPositionInFamily = birthPositionInFamily;
    }

    public Boolean getHasOwnDwelling() {
        return hasOwnDwelling;
    }

    public void setHasOwnDwelling(Boolean hasOwnDwelling) {
        this.hasOwnDwelling = hasOwnDwelling;
    }

    public Boolean getWantToStudy() {
        return wantToStudy;
    }

    public void setWantToStudy(Boolean wantToStudy) {
        this.wantToStudy = wantToStudy;
    }

    public Boolean getWantToWork() {
        return wantToWork;
    }

    public void setWantToWork(Boolean wantToWork) {
        this.wantToWork = wantToWork;
    }

    public Boolean getReadyToRelocate() {
        return readyToRelocate;
    }

    public void setReadyToRelocate(Boolean readyToRelocate) {
        this.readyToRelocate = readyToRelocate;
    }

    public String getKnowledgeOfLanguages() {
        return knowledgeOfLanguages;
    }

    public void setKnowledgeOfLanguages(String knowledgeOfLanguages) {
        this.knowledgeOfLanguages = knowledgeOfLanguages;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public ProfileState getProfileState() {
        return profileState;
    }

    public void setProfileState(ProfileState profileState) {
        this.profileState = profileState;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public ProfileDiscoverabilityDTO getDiscoverability() {
        return discoverability;
    }

    public void setDiscoverability(ProfileDiscoverabilityDTO discoverability) {
        this.discoverability = discoverability;
    }

    public NationalityDTO getNationality() {
        return nationality;
    }

    public void setNationality(NationalityDTO nationality) {
        this.nationality = nationality;
    }

    public Set<GenderTagDTO> getGenderTags() {
        return genderTags;
    }

    public void setGenderTags(Set<GenderTagDTO> genderTags) {
        this.genderTags = genderTags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProfileDTO)) {
            return false;
        }

        ProfileDTO profileDTO = (ProfileDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, profileDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProfileDTO{" +
            "id='" + getId() + "'" +
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
            ", location=" + getLocation() +
            ", discoverability=" + getDiscoverability() +
            ", nationality=" + getNationality() +
            ", genderTags=" + getGenderTags() +
            "}";
    }
}
