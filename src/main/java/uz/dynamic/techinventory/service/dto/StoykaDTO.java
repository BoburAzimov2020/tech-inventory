package uz.dynamic.techinventory.service.dto;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uz.dynamic.techinventory.domain.Stoyka} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StoykaDTO implements Serializable {

    private Long id;

    @Size(max = 128)
    private String name;

    @Size(max = 1024)
    private String info;

    private StoykaTypeDTO stoykaType;

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

    public StoykaTypeDTO getStoykaType() {
        return stoykaType;
    }

    public void setStoykaType(StoykaTypeDTO stoykaType) {
        this.stoykaType = stoykaType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StoykaDTO)) {
            return false;
        }

        StoykaDTO stoykaDTO = (StoykaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, stoykaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StoykaDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", info='" + getInfo() + "'" +
            ", stoykaType=" + getStoykaType() +
            "}";
    }
}
