package uz.dynamic.techinventory.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uz.dynamic.techinventory.domain.Shelf} entity.
 */
@Schema(description = "Javon")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ShelfDTO implements Serializable {

    private Long id;

    /**
     * Seriyasi
     */
    @Size(max = 64)
    @Schema(description = "Seriyasi")
    private String serialNumber;

    /**
     * Raqami
     */
    @Size(max = 64)
    @Schema(description = "Raqami")
    private String digitNumber;

    @Size(max = 1024)
    private String info;

    private ShelfTypeDTO shelfType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDigitNumber() {
        return digitNumber;
    }

    public void setDigitNumber(String digitNumber) {
        this.digitNumber = digitNumber;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public ShelfTypeDTO getShelfType() {
        return shelfType;
    }

    public void setShelfType(ShelfTypeDTO shelfType) {
        this.shelfType = shelfType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShelfDTO)) {
            return false;
        }

        ShelfDTO shelfDTO = (ShelfDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, shelfDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShelfDTO{" +
            "id=" + getId() +
            ", serialNumber='" + getSerialNumber() + "'" +
            ", digitNumber='" + getDigitNumber() + "'" +
            ", info='" + getInfo() + "'" +
            ", shelfType=" + getShelfType() +
            "}";
    }
}
