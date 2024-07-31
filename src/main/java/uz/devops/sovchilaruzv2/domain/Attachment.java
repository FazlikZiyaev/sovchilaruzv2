package uz.devops.sovchilaruzv2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uz.devops.sovchilaruzv2.domain.enumeration.AttachmentStatus;
import uz.devops.sovchilaruzv2.domain.enumeration.EntityState;
import uz.devops.sovchilaruzv2.domain.enumeration.Extension;

/**
 * A Attachment.
 */
@Entity
@Table(name = "attachment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Attachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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

    @Enumerated(EnumType.STRING)
    @Column(name = "extension")
    private Extension extension;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AttachmentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties(value = "attachments", allowSetters = true)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AttachmentStatus getStatus() {
        return status;
    }

    public void setStatus(AttachmentStatus status) {
        this.status = status;
    }

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
            ", extension='" + getExtension() + "'" +
            "}";
    }

    public Extension getExtension() {
        return extension;
    }

    public void setExtension(Extension extension) {
        this.extension = extension;
    }
}
