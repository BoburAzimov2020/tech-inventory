package uz.dynamic.techinventory.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uz.dynamic.techinventory.domain.Region} entity.
 */
@Schema(description = "Регион-Viloyat.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RegionDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 128)
    private String name;

    @Size(max = 1024)
    private String info;

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegionDTO)) {
            return false;
        }

        RegionDTO regionDTO = (RegionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, regionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RegionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", info='" + getInfo() + "'" +
            "}";
    }
}
