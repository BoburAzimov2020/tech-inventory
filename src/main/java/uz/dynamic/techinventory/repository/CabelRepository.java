package uz.dynamic.techinventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.Cabel;

/**
 * Spring Data JPA repository for the Cabel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CabelRepository extends JpaRepository<Cabel, Long> {

    Page<Cabel> findAllByCabelTypeId(Pageable pageable, Long cabelTypeId);

    Integer countByObyektId(Long obyektId);

    Page<Cabel> findAllByObyektId(Pageable pageable, Long obyektId);

}
