package uz.dynamic.techinventory.service.dto;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uz.dynamic.techinventory.domain.Swich} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SwichDTO implements Serializable {

    private Long id;

    @Size(max = 128)
    private String name;

    private String model;

    private String portNumber;

    @Size(max = 1024)
    private String info;

    private SwichTypeDTO swichType;

    private ObyektDTO obyekt;

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

    public ObyektDTO getObyekt() {
        return obyekt;
    }

    public void setObyekt(ObyektDTO obyekt) {
        this.obyekt = obyekt;
    }

    public String getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(String portNumber) {
        this.portNumber = portNumber;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public SwichTypeDTO getSwichType() {
        return swichType;
    }

    public void setSwichType(SwichTypeDTO swichType) {
        this.swichType = swichType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SwichDTO)) {
            return false;
        }

        SwichDTO swichDTO = (SwichDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, swichDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SwichDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", model='" + getModel() + "'" +
            ", portNumber='" + getPortNumber() + "'" +
            ", info='" + getInfo() + "'" +
            ", swichType=" + getSwichType() +
            "}";
    }
}
