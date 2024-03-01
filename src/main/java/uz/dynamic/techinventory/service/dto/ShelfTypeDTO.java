package uz.dynamic.techinventory.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uz.dynamic.techinventory.domain.ShelfType} entity.
 */
@Schema(description = "Javon Turi")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ShelfTypeDTO implements Serializable {

    private Long id;

    /**
     * Javon turlari
     */
    @Size(max = 64)
    @Schema(description = "Javon turlari")
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
        if (!(o instanceof ShelfTypeDTO)) {
            return false;
        }

        ShelfTypeDTO shelfTypeDTO = (ShelfTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, shelfTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShelfTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", info='" + getInfo() + "'" +
            "}";
    }
}
