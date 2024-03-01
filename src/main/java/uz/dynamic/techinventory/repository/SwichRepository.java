package uz.dynamic.techinventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.Swich;

/**
 * Spring Data JPA repository for the Swich entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SwichRepository extends JpaRepository<Swich, Long> {

    Page<Swich> findAllBySwichTypeId(Pageable pageable, Long swichTypeId);

    Integer countByObyektId(Long obyektId);

    Page<Swich> findAllByObyektId(Pageable pageable, Long obyektId);

}
