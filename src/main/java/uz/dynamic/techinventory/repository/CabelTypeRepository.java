package uz.dynamic.techinventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.CabelType;

/**
 * Spring Data JPA repository for the CabelType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CabelTypeRepository extends JpaRepository<CabelType, Long> {

    Page<CabelType> findAllByObyektId(Pageable pageable, Long obyektId);

}
