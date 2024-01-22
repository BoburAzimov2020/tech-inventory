package uz.dynamic.techinventory.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uz.dynamic.techinventory.domain.Loyiha} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LoyihaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 128)
    private String name;

    private ObjectTasnifiDTO objectTasnifi;

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

    public ObjectTasnifiDTO getObjectTasnifi() {
        return objectTasnifi;
    }

    public void setObjectTasnifi(ObjectTasnifiDTO objectTasnifi) {
        this.objectTasnifi = objectTasnifi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoyihaDTO)) {
            return false;
        }

        LoyihaDTO loyihaDTO = (LoyihaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, loyihaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoyihaDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", objectTasnifi=" + getObjectTasnifi() +
            "}";
    }
}
