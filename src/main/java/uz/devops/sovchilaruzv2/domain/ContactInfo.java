package uz.devops.sovchilaruzv2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uz.devops.sovchilaruzv2.domain.enumeration.EntityState;
import uz.devops.sovchilaruzv2.domain.enumeration.InfoType;

/**
 * A ContactInfo.
 */
@Entity
@Table(name = "contact_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContactInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private InfoType type;

    @NotNull
    @Size(max = 256)
    @Column(name = "text", length = 256, nullable = false)
    private String text;

    @Column(name = "ord")
    private Integer ord;

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

    public ContactInfo id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public InfoType getType() {
        return this.type;
    }

    public ContactInfo type(InfoType type) {
        this.setType(type);
        return this;
    }

    public void setType(InfoType type) {
        this.type = type;
    }

    public String getText() {
        return this.text;
    }

    public ContactInfo text(String text) {
        this.setText(text);
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getOrd() {
        return this.ord;
    }

    public ContactInfo ord(Integer ord) {
        this.setOrd(ord);
        return this;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }

    public EntityState getState() {
        return this.state;
    }

    public ContactInfo state(EntityState state) {
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

    public ContactInfo profile(Profile profile) {
        this.setProfile(profile);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContactInfo)) {
            return false;
        }
        return getId() != null && getId().equals(((ContactInfo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContactInfo{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", text='" + getText() + "'" +
            ", ord=" + getOrd() +
            ", state='" + getState() + "'" +
            "}";
    }
}
