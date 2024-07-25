package uz.devops.sovchilaruzv2.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import uz.devops.sovchilaruzv2.domain.enumeration.EntityState;

/**
 * A DTO for the {@link uz.devops.sovchilaruzv2.domain.Nationality} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NationalityDTO implements Serializable {

    private UUID id;

    @NotNull
    private String name;

    private Integer ord;

    private EntityState state;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NationalityDTO)) {
            return false;
        }

        NationalityDTO nationalityDTO = (NationalityDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, nationalityDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NationalityDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", ord=" + getOrd() +
            ", state='" + getState() + "'" +
            "}";
    }
}
