package uz.dynamic.techinventory.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.dynamic.techinventory.domain.*;
import uz.dynamic.techinventory.service.dto.ObyektFilterDTO;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ObyektSpecification {

    public Specification<Obyekt> getObyekts(ObyektFilterDTO request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request != null) {
                if (request.getRegionId() != null) { // regionID = ?
                    Join<Obyekt, Region> join = root.join("region");
                    predicates.add(criteriaBuilder.equal(join.get("id"), request.getRegionId()));
                }

                if (request.getDistrictId() != null) { // districtID = ?
                    Join<Obyekt, District> join = root.join("district");
                    predicates.add(criteriaBuilder.equal(join.get("id"), request.getDistrictId()));
                }

                if (request.getObjectTasnifiId() != null) {
                    Join<Obyekt, ObjectTasnifi> join = root.join("objectTasnifi"); // objectTasnifiID = ?
                    predicates.add(criteriaBuilder.equal(join.get("id"), request.getObjectTasnifiId()));
                }

                if (request.getObjectTasnifiTuriId() != null) {
                    Join<Obyekt, ObjectTasnifiTuri> join = root.join("objectTasnifiTuri"); // objectTasnifiTuriID = ?
                    predicates.add(criteriaBuilder.equal(join.get("id"), request.getObjectTasnifiTuriId()));
                }

                if (request.getLoyihaId() != null) {
                    Join<Obyekt, Loyiha> join = root.join("loyiha"); // loyihaID = ?
                    predicates.add(criteriaBuilder.equal(join.get("id"), request.getLoyihaId()));
                }

                if (request.getBuyurtmaRaqamId() != null) {
                    Join<Obyekt, BuyurtmaRaqam> join = root.join("buyurtmaRaqam"); // buyurtmaRaqamID = ?
                    predicates.add(criteriaBuilder.equal(join.get("id"), request.getBuyurtmaRaqamId()));
                }
            }

            query.orderBy(criteriaBuilder.desc(root.get("name")));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
