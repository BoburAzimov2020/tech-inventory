package uz.dynamic.techinventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.Ups;

/**
 * Spring Data JPA repository for the Ups entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UpsRepository extends JpaRepository<Ups, Long> {

    Page<Ups> findAllByObyektId(Pageable pageable, Long obyektId);

    Integer countByObyektId(Long obyektId);

}
