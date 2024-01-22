package uz.dynamic.techinventory.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uz.dynamic.techinventory.domain.ObjectTasnifiTuri} entity.
 */
@Schema(description = "Object tasnifi turi")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ObjectTasnifiTuriDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 128)
    private String name;

    private DistrictDTO district;

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

    public DistrictDTO getDistrict() {
        return district;
    }

    public void setDistrict(DistrictDTO district) {
        this.district = district;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ObjectTasnifiTuriDTO)) {
            return false;
        }

        ObjectTasnifiTuriDTO objectTasnifiTuriDTO = (ObjectTasnifiTuriDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, objectTasnifiTuriDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ObjectTasnifiTuriDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", district=" + getDistrict() +
            "}";
    }
}
