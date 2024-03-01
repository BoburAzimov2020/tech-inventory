package uz.dynamic.techinventory.service.dto;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uz.dynamic.techinventory.domain.Projector} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProjectorDTO implements Serializable {

    private Long id;

    @Size(max = 128)
    private String name;

    private String model;

    @Size(max = 1024)
    private String info;

    private ProjectorTypeDTO projectorType;

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public ProjectorTypeDTO getProjectorType() {
        return projectorType;
    }

    public void setProjectorType(ProjectorTypeDTO projectorType) {
        this.projectorType = projectorType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectorDTO)) {
            return false;
        }

        ProjectorDTO projectorDTO = (ProjectorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, projectorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectorDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", model='" + getModel() + "'" +
            ", info='" + getInfo() + "'" +
            ", projectorType=" + getProjectorType() +
            "}";
    }
}
