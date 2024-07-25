package uz.devops.sovchilaruzv2.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import uz.devops.sovchilaruzv2.domain.enumeration.MaritalStatus;

/**
 * A DTO for the {@link uz.devops.sovchilaruzv2.domain.ProfileDiscoverability} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProfileDiscoverabilityDTO implements Serializable {

    private Long id;

    private MaritalStatus maritalStatus;

    @Min(value = 18)
    @Max(value = 99)
    private Integer maxAge;

    @Min(value = 18)
    @Max(value = 99)
    private Integer minAge;

    private Boolean showMyPhoto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Boolean getShowMyPhoto() {
        return showMyPhoto;
    }

    public void setShowMyPhoto(Boolean showMyPhoto) {
        this.showMyPhoto = showMyPhoto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProfileDiscoverabilityDTO)) {
            return false;
        }

        ProfileDiscoverabilityDTO profileDiscoverabilityDTO = (ProfileDiscoverabilityDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, profileDiscoverabilityDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProfileDiscoverabilityDTO{" +
            "id=" + getId() +
            ", maritalStatus='" + getMaritalStatus() + "'" +
            ", maxAge=" + getMaxAge() +
            ", minAge=" + getMinAge() +
            ", showMyPhoto='" + getShowMyPhoto() + "'" +
            "}";
    }
}
