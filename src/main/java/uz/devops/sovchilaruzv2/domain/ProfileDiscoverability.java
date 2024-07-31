package uz.devops.sovchilaruzv2.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uz.devops.sovchilaruzv2.domain.enumeration.MaritalStatus;

/**
 * A ProfileDiscoverability.
 */
@Entity
@Table(name = "profile_discoverability")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProfileDiscoverability implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status")
    private MaritalStatus maritalStatus;

    @Min(value = 18)
    @Max(value = 99)
    @Column(name = "max_age")
    private Integer maxAge;

    @Min(value = 18)
    @Max(value = 99)
    @Column(name = "min_age")
    private Integer minAge;

    @Column(name = "show_my_photo")
    private Boolean showMyPhoto;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public ProfileDiscoverability id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public MaritalStatus getMaritalStatus() {
        return this.maritalStatus;
    }

    public ProfileDiscoverability maritalStatus(MaritalStatus maritalStatus) {
        this.setMaritalStatus(maritalStatus);
        return this;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Integer getMaxAge() {
        return this.maxAge;
    }

    public ProfileDiscoverability maxAge(Integer maxAge) {
        this.setMaxAge(maxAge);
        return this;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Integer getMinAge() {
        return this.minAge;
    }

    public ProfileDiscoverability minAge(Integer minAge) {
        this.setMinAge(minAge);
        return this;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Boolean getShowMyPhoto() {
        return this.showMyPhoto;
    }

    public ProfileDiscoverability showMyPhoto(Boolean showMyPhoto) {
        this.setShowMyPhoto(showMyPhoto);
        return this;
    }

    public void setShowMyPhoto(Boolean showMyPhoto) {
        this.showMyPhoto = showMyPhoto;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProfileDiscoverability)) {
            return false;
        }
        return getId() != null && getId().equals(((ProfileDiscoverability) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProfileDiscoverability{" +
            "id=" + getId() +
            ", maritalStatus='" + getMaritalStatus() + "'" +
            ", maxAge=" + getMaxAge() +
            ", minAge=" + getMinAge() +
            ", showMyPhoto='" + getShowMyPhoto() + "'" +
            "}";
    }
}
