package uz.dynamic.techinventory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uz.dynamic.techinventory.domain.enumeration.CameraStatus;

/**
 * Camera
 */
@Entity
@Table(name = "camera")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Camera implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * Nomi 'Camera1'
     */
    @NotNull
    @Size(max = 64)
    @Column(name = "name", length = 64, nullable = false)
    private String name;

    /**
     * Model.
     */
    @Size(max = 64)
    @Column(name = "model", length = 64)
    private String model;

    /**
     * Seriya raqami.
     */
    @Size(max = 64)
    @Column(name = "serial_number", length = 64)
    private String serialNumber;

    /**
     * IP
     */
    @NotNull
    @Size(max = 64)
    @Column(name = "ip", length = 64, nullable = false)
    private String ip;

    /**
     * Status
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CameraStatus status;

    @Size(max = 1024)
    @Column(name = "info", length = 1024)
    private String info;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "obyekt" }, allowSetters = true)
    private CameraType cameraType;

    @ManyToOne(fetch = FetchType.LAZY)
    private CameraBrand cameraBrand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "buyurtmaRaqam" }, allowSetters = true)
    private Obyekt obyekt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Camera id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Camera name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return this.model;
    }

    public Camera model(String model) {
        this.setModel(model);
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public Camera serialNumber(String serialNumber) {
        this.setSerialNumber(serialNumber);
        return this;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getIp() {
        return this.ip;
    }

    public Camera ip(String ip) {
        this.setIp(ip);
        return this;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public CameraStatus getStatus() {
        return this.status;
    }

    public Camera status(CameraStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(CameraStatus status) {
        this.status = status;
    }

    public String getInfo() {
        return this.info;
    }

    public Camera info(String info) {
        this.setInfo(info);
        return this;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public CameraType getCameraType() {
        return this.cameraType;
    }

    public void setCameraType(CameraType cameraType) {
        this.cameraType = cameraType;
    }

    public Camera cameraType(CameraType cameraType) {
        this.setCameraType(cameraType);
        return this;
    }

    public CameraBrand getCameraBrand() {
        return this.cameraBrand;
    }

    public void setCameraBrand(CameraBrand cameraBrand) {
        this.cameraBrand = cameraBrand;
    }

    public Camera cameraBrand(CameraBrand cameraBrand) {
        this.setCameraBrand(cameraBrand);
        return this;
    }

    public Obyekt getObyekt() {
        return this.obyekt;
    }

    public void setObyekt(Obyekt obyekt) {
        this.obyekt = obyekt;
    }

    public Camera obyekt(Obyekt obyekt) {
        this.setObyekt(obyekt);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Camera)) {
            return false;
        }
        return getId() != null && getId().equals(((Camera) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Camera{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", model='" + getModel() + "'" +
            ", serialNumber='" + getSerialNumber() + "'" +
            ", ip='" + getIp() + "'" +
            ", status='" + getStatus() + "'" +
            ", info='" + getInfo() + "'" +
            "}";
    }
}
