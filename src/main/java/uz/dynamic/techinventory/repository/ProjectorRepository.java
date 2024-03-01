package uz.dynamic.techinventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.Projector;

/**
 * Spring Data JPA repository for the Projector entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectorRepository extends JpaRepository<Projector, Long> {

    Page<Projector> findAllByProjectorTypeId(Pageable pageable, Long projectorTypeId);

    Integer countByObyektId(Long obyektId);

    Page<Projector> findAllByObyektId(Pageable pageable, Long obyektId);

}
