package uz.devops.sovchilaruzv2.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import uz.devops.sovchilaruzv2.domain.enumeration.EntityState;
import uz.devops.sovchilaruzv2.domain.enumeration.InfoType;

/**
 * A DTO for the {@link uz.devops.sovchilaruzv2.domain.ContactInfo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContactInfoDTO implements Serializable {

    private UUID id;

    @NotNull
    private InfoType type;

    @NotNull
    @Size(max = 256)
    private String text;

    private Integer ord;

    private EntityState state;

    private ProfileDTO profile;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public InfoType getType() {
        return type;
    }

    public void setType(InfoType type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getOrd() {
        return ord;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }

    public EntityState getState() {
        return state;
    }

    public void setState(EntityState state) {
        this.state = state;
    }

    public ProfileDTO getProfile() {
        return profile;
    }

    public void setProfile(ProfileDTO profile) {
        this.profile = profile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContactInfoDTO)) {
            return false;
        }

        ContactInfoDTO contactInfoDTO = (ContactInfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, contactInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContactInfoDTO{" +
            "id='" + getId() + "'" +
            ", type='" + getType() + "'" +
            ", text='" + getText() + "'" +
            ", ord=" + getOrd() +
            ", state='" + getState() + "'" +
            ", profile=" + getProfile() +
            "}";
    }
}
