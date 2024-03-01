package uz.dynamic.techinventory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Swich.
 */
@Entity
@Table(name = "swich")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Swich implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 128)
    @Column(name = "name", length = 128, unique = true)
    private String name;

    @Column(name = "model")
    private String model;

    @Column(name = "port_number")
    private String portNumber;

    @Size(max = 1024)
    @Column(name = "info", length = 1024)
    private String info;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "obyekt" }, allowSetters = true)
    private SwichType swichType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "buyurtmaRaqam" }, allowSetters = true)
    private Obyekt obyekt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Swich id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Swich name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return this.model;
    }

    public Swich model(String model) {
        this.setModel(model);
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPortNumber() {
        return this.portNumber;
    }

    public Swich portNumber(String portNumber) {
        this.setPortNumber(portNumber);
        return this;
    }

    public void setPortNumber(String portNumber) {
        this.portNumber = portNumber;
    }

    public String getInfo() {
        return this.info;
    }

    public Swich info(String info) {
        this.setInfo(info);
        return this;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public SwichType getSwichType() {
        return this.swichType;
    }

    public void setSwichType(SwichType swichType) {
        this.swichType = swichType;
    }

    public Swich swichType(SwichType swichType) {
        this.setSwichType(swichType);
        return this;
    }

    public Obyekt getObyekt() {
        return this.obyekt;
    }

    public void setObyekt(Obyekt obyekt) {
        this.obyekt = obyekt;
    }

    public Swich obyekt(Obyekt obyekt) {
        this.setObyekt(obyekt);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Swich)) {
            return false;
        }
        return getId() != null && getId().equals(((Swich) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Swich{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", model='" + getModel() + "'" +
            ", portNumber='" + getPortNumber() + "'" +
            ", info='" + getInfo() + "'" +
            "}";
    }
}
