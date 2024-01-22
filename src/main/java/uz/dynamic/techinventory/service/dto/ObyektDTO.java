package uz.dynamic.techinventory.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uz.dynamic.techinventory.domain.Obyekt} entity.
 */
@Schema(description = "Локация - полный адресс.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ObyektDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 128)
    private String name;

    private String home;

    private String street;

    private String latitude;

    private String longitude;

    @Size(max = 1024)
    private String info;

    private BuyurtmaRaqamDTO buyurtmaRaqam;

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

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public BuyurtmaRaqamDTO getBuyurtmaRaqam() {
        return buyurtmaRaqam;
    }

    public void setBuyurtmaRaqam(BuyurtmaRaqamDTO buyurtmaRaqam) {
        this.buyurtmaRaqam = buyurtmaRaqam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ObyektDTO)) {
            return false;
        }

        ObyektDTO obyektDTO = (ObyektDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, obyektDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ObyektDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", home='" + getHome() + "'" +
            ", street='" + getStreet() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", info='" + getInfo() + "'" +
            ", buyurtmaRaqam=" + getBuyurtmaRaqam() +
            "}";
    }
}
