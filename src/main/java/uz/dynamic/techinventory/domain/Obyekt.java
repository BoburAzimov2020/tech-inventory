package uz.dynamic.techinventory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Локация - полный адресс.
 */
@Entity
@Table(name = "obyekt")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Obyekt implements Serializable {

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

    @Column(name = "home")
    private String home;

    @Column(name = "street")
    private String street;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Size(max = 1024)
    @Column(name = "info", length = 1024)
    private String info;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "loyiha" }, allowSetters = true)
    private BuyurtmaRaqam buyurtmaRaqam;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Obyekt id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Obyekt name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHome() {
        return this.home;
    }

    public Obyekt home(String home) {
        this.setHome(home);
        return this;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getStreet() {
        return this.street;
    }

    public Obyekt street(String street) {
        this.setStreet(street);
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public Obyekt latitude(String latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public Obyekt longitude(String longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getInfo() {
        return this.info;
    }

    public Obyekt info(String info) {
        this.setInfo(info);
        return this;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public BuyurtmaRaqam getBuyurtmaRaqam() {
        return this.buyurtmaRaqam;
    }

    public void setBuyurtmaRaqam(BuyurtmaRaqam buyurtmaRaqam) {
        this.buyurtmaRaqam = buyurtmaRaqam;
    }

    public Obyekt buyurtmaRaqam(BuyurtmaRaqam buyurtmaRaqam) {
        this.setBuyurtmaRaqam(buyurtmaRaqam);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Obyekt)) {
            return false;
        }
        return getId() != null && getId().equals(((Obyekt) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Obyekt{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", home='" + getHome() + "'" +
            ", street='" + getStreet() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", info='" + getInfo() + "'" +
            "}";
    }
}
