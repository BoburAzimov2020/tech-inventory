package uz.dynamic.techinventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.Akumulator;

/**
 * Spring Data JPA repository for the Akumulator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AkumulatorRepository extends JpaRepository<Akumulator, Long> {

    Page<Akumulator> findAllByObyektId(Pageable pageable, Long obyektId);

}
