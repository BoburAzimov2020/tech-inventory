package uz.dynamic.techinventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.ObjectTasnifiTuri;

/**
 * Spring Data JPA repository for the ObjectTasnifiTuri entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ObjectTasnifiTuriRepository extends JpaRepository<ObjectTasnifiTuri, Long> {

    Page<ObjectTasnifiTuri> findAllByDistrictId(Pageable pageable, Long districtId);
}
