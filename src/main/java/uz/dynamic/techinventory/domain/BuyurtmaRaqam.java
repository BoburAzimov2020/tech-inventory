package uz.dynamic.techinventory.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

/**
 * A BuyurtmaRaqam.
 */
@Entity
@Table(name = "buyurtma_raqam")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BuyurtmaRaqam implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "number_of_order")
    private String numberOfOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    private Loyiha loyiha;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BuyurtmaRaqam id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public BuyurtmaRaqam name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberOfOrder() {
        return this.numberOfOrder;
    }

    public BuyurtmaRaqam numberOfOrder(String numberOfOrder) {
        this.setNumberOfOrder(numberOfOrder);
        return this;
    }

    public void setNumberOfOrder(String numberOfOrder) {
        this.numberOfOrder = numberOfOrder;
    }

    public Loyiha getLoyiha() {
        return this.loyiha;
    }

    public void setLoyiha(Loyiha loyiha) {
        this.loyiha = loyiha;
    }

    public BuyurtmaRaqam loyiha(Loyiha loyiha) {
        this.setLoyiha(loyiha);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BuyurtmaRaqam)) {
            return false;
        }
        return getId() != null && getId().equals(((BuyurtmaRaqam) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BuyurtmaRaqam{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", numberOfOrder='" + getNumberOfOrder() + "'" +
            "}";
    }
}
