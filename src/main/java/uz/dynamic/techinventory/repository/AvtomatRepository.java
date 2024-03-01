package uz.dynamic.techinventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.Avtomat;

/**
 * Spring Data JPA repository for the Avtomat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvtomatRepository extends JpaRepository<Avtomat, Long> {

    Page<Avtomat> findAllByObyektId(Pageable pageable, Long obyektId);

    Integer countByObyektId(Long obyektId);

}
