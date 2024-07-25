package uz.devops.sovchilaruzv2.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import uz.devops.sovchilaruzv2.domain.enumeration.EntityState;
import uz.devops.sovchilaruzv2.domain.enumeration.Gender;

/**
 * A DTO for the {@link uz.devops.sovchilaruzv2.domain.GenderTag} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GenderTagDTO implements Serializable {

    private UUID id;

    @NotNull
    @Size(min = 1, max = 50)
    private String hashtag;

    @NotNull
    private Gender gender;

    private EntityState state;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public EntityState getState() {
        return state;
    }

    public void setState(EntityState state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GenderTagDTO)) {
            return false;
        }

        GenderTagDTO genderTagDTO = (GenderTagDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, genderTagDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GenderTagDTO{" +
            "id='" + getId() + "'" +
            ", hashtag='" + getHashtag() + "'" +
            ", gender='" + getGender() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
