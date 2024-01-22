package uz.dynamic.techinventory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Object tasnifi
 */
@Entity
@Table(name = "object_tasnifi")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ObjectTasnifi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 128)
    @Column(name = "name", length = 128, nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "district" }, allowSetters = true)
    private ObjectTasnifiTuri bjectTasnifiTuri;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ObjectTasnifi id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ObjectTasnifi name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObjectTasnifiTuri getBjectTasnifiTuri() {
        return this.bjectTasnifiTuri;
    }

    public void setBjectTasnifiTuri(ObjectTasnifiTuri objectTasnifiTuri) {
        this.bjectTasnifiTuri = objectTasnifiTuri;
    }

    public ObjectTasnifi bjectTasnifiTuri(ObjectTasnifiTuri objectTasnifiTuri) {
        this.setBjectTasnifiTuri(objectTasnifiTuri);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ObjectTasnifi)) {
            return false;
        }
        return getId() != null && getId().equals(((ObjectTasnifi) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ObjectTasnifi{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
