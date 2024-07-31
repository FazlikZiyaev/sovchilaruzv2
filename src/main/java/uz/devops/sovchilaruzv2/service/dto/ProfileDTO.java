package uz.devops.sovchilaruzv2.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.Data;
import uz.devops.sovchilaruzv2.domain.GenderTag;
import uz.devops.sovchilaruzv2.domain.enumeration.ChildPlan;
import uz.devops.sovchilaruzv2.domain.enumeration.Education;
import uz.devops.sovchilaruzv2.domain.enumeration.Gender;
import uz.devops.sovchilaruzv2.domain.enumeration.MaritalStatus;
import uz.devops.sovchilaruzv2.domain.enumeration.ProfileState;

/**
 * A DTO for the {@link uz.devops.sovchilaruzv2.domain.Profile} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
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

    @Size(max = 400)
    private String bio;

    @Size(max = 256)
    private String requirements;

    private ProfileState profileState;

    private LocationDTO location;

    private ProfileDiscoverabilityDTO discoverability;

    private NationalityDTO nationality;

    private Set<GenderTag> genderTags = new HashSet<>();

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
            ", nationality=" + getNationality() +
            ", genderTags=" + getGenderTags() +
            "}";
    }
}
