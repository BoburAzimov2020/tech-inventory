package uz.dynamic.techinventory.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import uz.dynamic.techinventory.domain.enumeration.CameraStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A DTO for the {@link uz.dynamic.techinventory.domain.Camera} entity.
 */
@Schema(description = "Camera")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CameraDTO implements Serializable {

    private Long id;

    /**
     * Nomi 'Camera1'
     */
    @NotNull
    @Size(max = 64)
    @Schema(description = "Nomi 'Camera1'", required = true)
    private String name;

    /**
     * Model.
     */
    @Size(max = 64)
    @Schema(description = "Model.")
    private String model;

    /**
     * Seriya raqami.
     */
    @Size(max = 64)
    @Schema(description = "Seriya raqami.")
    private String serialNumber;

    /**
     * IP
     */
    @NotNull
    @Size(max = 64)
    @Schema(description = "IP", required = true)
    private String ip;

    /**
     * Status
     */
    @Schema(description = "Status")
    private CameraStatus status;

    @Size(max = 1024)
    private String info;

    private CameraTypeDTO cameraType;

    private CameraBrandDTO cameraBrand;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public CameraStatus getStatus() {
        return status;
    }

    public void setStatus(CameraStatus status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public CameraTypeDTO getCameraType() {
        return cameraType;
    }

    public void setCameraType(CameraTypeDTO cameraType) {
        this.cameraType = cameraType;
    }

    public CameraBrandDTO getCameraBrand() {
        return cameraBrand;
    }

    public void setCameraBrand(CameraBrandDTO cameraBrand) {
        this.cameraBrand = cameraBrand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CameraDTO)) {
            return false;
        }

        CameraDTO cameraDTO = (CameraDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cameraDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CameraDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", model='" + getModel() + "'" +
            ", serialNumber='" + getSerialNumber() + "'" +
            ", ip='" + getIp() + "'" +
            ", status='" + getStatus() + "'" +
            ", info='" + getInfo() + "'" +
            ", cameraType=" + getCameraType() +
            ", cameraBrand=" + getCameraBrand() +
            "}";
    }
}
