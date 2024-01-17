package uz.dynamic.techinventory.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uz.dynamic.techinventory.domain.ObjectTasnifi} entity.
 */
@Schema(description = "Object tasnifi")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ObjectTasnifiDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 128)
    private String name;

    private ObjectTasnifiTuriDTO bjectTasnifiTuri;

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

    public ObjectTasnifiTuriDTO getBjectTasnifiTuri() {
        return bjectTasnifiTuri;
    }

    public void setBjectTasnifiTuri(ObjectTasnifiTuriDTO bjectTasnifiTuri) {
        this.bjectTasnifiTuri = bjectTasnifiTuri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ObjectTasnifiDTO)) {
            return false;
        }

        ObjectTasnifiDTO objectTasnifiDTO = (ObjectTasnifiDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, objectTasnifiDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ObjectTasnifiDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", bjectTasnifiTuri=" + getBjectTasnifiTuri() +
            "}";
    }
}
