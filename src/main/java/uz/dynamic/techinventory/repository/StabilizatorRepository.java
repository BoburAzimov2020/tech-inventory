package uz.dynamic.techinventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.Stabilizator;

/**
 * Spring Data JPA repository for the Stabilizator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StabilizatorRepository extends JpaRepository<Stabilizator, Long> {

    Page<Stabilizator> findAllByObyektId(Pageable pageable, Long obyektId);

    Integer countByObyektId(Long obyektId);

}
