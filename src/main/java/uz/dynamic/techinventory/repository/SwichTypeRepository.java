package uz.dynamic.techinventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.SwichType;

/**
 * Spring Data JPA repository for the SwichType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SwichTypeRepository extends JpaRepository<SwichType, Long> {

    Page<SwichType> findAllByObyektId(Pageable pageable, Long obyektId);

}
