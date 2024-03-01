package uz.dynamic.techinventory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Javon
 */
@Entity
@Table(name = "shelf")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Shelf implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * Seriyasi
     */
    @Size(max = 64)
    @Column(name = "serial_number", length = 64)
    private String serialNumber;

    /**
     * Raqami
     */
    @Size(max = 64)
    @Column(name = "digit_number", length = 64)
    private String digitNumber;

    @Size(max = 1024)
    @Column(name = "info", length = 1024)
    private String info;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "obyekt" }, allowSetters = true)
    private ShelfType shelfType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "buyurtmaRaqam" }, allowSetters = true)
    private Obyekt obyekt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Shelf id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public Shelf serialNumber(String serialNumber) {
        this.setSerialNumber(serialNumber);
        return this;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDigitNumber() {
        return this.digitNumber;
    }

    public Shelf digitNumber(String digitNumber) {
        this.setDigitNumber(digitNumber);
        return this;
    }

    public void setDigitNumber(String digitNumber) {
        this.digitNumber = digitNumber;
    }

    public String getInfo() {
        return this.info;
    }

    public Shelf info(String info) {
        this.setInfo(info);
        return this;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public ShelfType getShelfType() {
        return this.shelfType;
    }

    public void setShelfType(ShelfType shelfType) {
        this.shelfType = shelfType;
    }

    public Shelf shelfType(ShelfType shelfType) {
        this.setShelfType(shelfType);
        return this;
    }

    public Obyekt getObyekt() {
        return this.obyekt;
    }

    public void setObyekt(Obyekt obyekt) {
        this.obyekt = obyekt;
    }

    public Shelf obyekt(Obyekt obyekt) {
        this.setObyekt(obyekt);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Shelf)) {
            return false;
        }
        return getId() != null && getId().equals(((Shelf) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Shelf{" +
            "id=" + getId() +
            ", serialNumber='" + getSerialNumber() + "'" +
            ", digitNumber='" + getDigitNumber() + "'" +
            ", info='" + getInfo() + "'" +
            "}";
    }
}
