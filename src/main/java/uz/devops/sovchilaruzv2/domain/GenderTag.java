package uz.devops.sovchilaruzv2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uz.devops.sovchilaruzv2.domain.enumeration.EntityState;
import uz.devops.sovchilaruzv2.domain.enumeration.Gender;

/**
 * A GenderTag.
 */
@Entity
@Table(name = "gender_tag")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GenderTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "hashtag", length = 50, nullable = false)
    private String hashtag;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private EntityState state;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "genderTags")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "location", "contactInfos", "attachments", "discoverability", "nationality", "genderTags" },
        allowSetters = true
    )
    private Set<Profile> profiles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public GenderTag id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getHashtag() {
        return this.hashtag;
    }

    public GenderTag hashtag(String hashtag) {
        this.setHashtag(hashtag);
        return this;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public Gender getGender() {
        return this.gender;
    }

    public GenderTag gender(Gender gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public EntityState getState() {
        return this.state;
    }

    public GenderTag state(EntityState state) {
        this.setState(state);
        return this;
    }

    public void setState(EntityState state) {
        this.state = state;
    }

    public Set<Profile> getProfiles() {
        return this.profiles;
    }

    public void setProfiles(Set<Profile> profiles) {
        if (this.profiles != null) {
            this.profiles.forEach(i -> i.removeGenderTags(this));
        }
        if (profiles != null) {
            profiles.forEach(i -> i.addGenderTags(this));
        }
        this.profiles = profiles;
    }

    public GenderTag profiles(Set<Profile> profiles) {
        this.setProfiles(profiles);
        return this;
    }

    public GenderTag addProfile(Profile profile) {
        this.profiles.add(profile);
        profile.getGenderTags().add(this);
        return this;
    }

    public GenderTag removeProfile(Profile profile) {
        this.profiles.remove(profile);
        profile.getGenderTags().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GenderTag)) {
            return false;
        }
        return getId() != null && getId().equals(((GenderTag) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GenderTag{" +
            "id=" + getId() +
            ", hashtag='" + getHashtag() + "'" +
            ", gender='" + getGender() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
