package uz.dynamic.techinventory.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uz.dynamic.techinventory.domain.BuyurtmaRaqam} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BuyurtmaRaqamDTO implements Serializable {

    private Long id;

    private String name;

    private String numberOfOrder;

    private LoyihaDTO loyiha;

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

    public String getNumberOfOrder() {
        return numberOfOrder;
    }

    public void setNumberOfOrder(String numberOfOrder) {
        this.numberOfOrder = numberOfOrder;
    }

    public LoyihaDTO getLoyiha() {
        return loyiha;
    }

    public void setLoyiha(LoyihaDTO loyiha) {
        this.loyiha = loyiha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BuyurtmaRaqamDTO)) {
            return false;
        }

        BuyurtmaRaqamDTO buyurtmaRaqamDTO = (BuyurtmaRaqamDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, buyurtmaRaqamDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BuyurtmaRaqamDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", numberOfOrder='" + getNumberOfOrder() + "'" +
            ", loyiha=" + getLoyiha() +
            "}";
    }
}
