package uz.dynamic.techinventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.Stoyka;

/**
 * Spring Data JPA repository for the Stoyka entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StoykaRepository extends JpaRepository<Stoyka, Long> {

    Page<Stoyka> findAllByStoykaTypeId(Pageable pageable, Long stoykaTypeId);

    Integer countByObyektId(Long obyektId);

    Page<Stoyka> findAllByObyektId(Pageable pageable, Long obyektId);

}
