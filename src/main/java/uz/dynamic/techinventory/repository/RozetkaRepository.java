package uz.dynamic.techinventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.Rozetka;

/**
 * Spring Data JPA repository for the Rozetka entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RozetkaRepository extends JpaRepository<Rozetka, Long> {

    Page<Rozetka> findAllByObyektId(Pageable pageable, Long obyektId);

}
