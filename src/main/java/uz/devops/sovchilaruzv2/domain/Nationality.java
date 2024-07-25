package uz.devops.sovchilaruzv2.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uz.devops.sovchilaruzv2.domain.enumeration.EntityState;

/**
 * A Nationality.
 */
@Entity
@Table(name = "nationality")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Nationality implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "ord")
    private Integer ord;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private EntityState state;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Nationality id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Nationality name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrd() {
        return this.ord;
    }

    public Nationality ord(Integer ord) {
        this.setOrd(ord);
        return this;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }

    public EntityState getState() {
        return this.state;
    }

    public Nationality state(EntityState state) {
        this.setState(state);
        return this;
    }

    public void setState(EntityState state) {
        this.state = state;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nationality)) {
            return false;
        }
        return getId() != null && getId().equals(((Nationality) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Nationality{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", ord=" + getOrd() +
            ", state='" + getState() + "'" +
            "}";
    }
}
