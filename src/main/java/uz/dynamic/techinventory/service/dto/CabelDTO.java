package uz.dynamic.techinventory.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uz.dynamic.techinventory.domain.Cabel} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CabelDTO implements Serializable {

    private Long id;

    @Size(max = 128)
    private String name;

    private String model;

    @Size(max = 1024)
    private String info;

    private CabelTypeDTO cabelType;

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public CabelTypeDTO getCabelType() {
        return cabelType;
    }

    public void setCabelType(CabelTypeDTO cabelType) {
        this.cabelType = cabelType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CabelDTO)) {
            return false;
        }

        CabelDTO cabelDTO = (CabelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cabelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CabelDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", model='" + getModel() + "'" +
            ", info='" + getInfo() + "'" +
            ", cabelType=" + getCabelType() +
            "}";
    }
}
