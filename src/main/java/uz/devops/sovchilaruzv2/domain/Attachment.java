package uz.devops.sovchilaruzv2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uz.devops.sovchilaruzv2.domain.enumeration.EntityState;

/**
 * A Attachment.
 */
@Entity
@Table(name = "attachment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Attachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Size(max = 256)
    @Column(name = "file_key", length = 256, nullable = false)
    private String fileKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private EntityState state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "location", "contactInfos", "attachments", "discoverability", "nationality", "genderTags" },
        allowSetters = true
    )
    private Profile profile;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Attachment id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFileKey() {
        return this.fileKey;
    }

    public Attachment fileKey(String fileKey) {
        this.setFileKey(fileKey);
        return this;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public EntityState getState() {
        return this.state;
    }

    public Attachment state(EntityState state) {
        this.setState(state);
        return this;
    }

    public void setState(EntityState state) {
        this.state = state;
    }

    public Profile getProfile() {
        return this.profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Attachment profile(Profile profile) {
        this.setProfile(profile);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attachment)) {
            return false;
        }
        return getId() != null && getId().equals(((Attachment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Attachment{" +
            "id=" + getId() +
            ", fileKey='" + getFileKey() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
