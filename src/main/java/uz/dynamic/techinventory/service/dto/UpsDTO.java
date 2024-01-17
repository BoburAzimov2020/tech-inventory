package uz.dynamic.techinventory.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uz.dynamic.techinventory.domain.Ups} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UpsDTO implements Serializable {

    private Long id;

    @Size(max = 128)
    private String name;

    private String model;

    private String power;

    @Size(max = 1024)
    private String info;

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

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public ObyektDTO getObyekt() {
        return obyekt;
    }

    public void setObyekt(ObyektDTO obyekt) {
        this.obyekt = obyekt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UpsDTO)) {
            return false;
        }

        UpsDTO upsDTO = (UpsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, upsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UpsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", model='" + getModel() + "'" +
            ", power='" + getPower() + "'" +
            ", info='" + getInfo() + "'" +
            ", obyekt=" + getObyekt() +
            "}";
    }
}
