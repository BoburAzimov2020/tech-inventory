package uz.dynamic.techinventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.ShelfType;

/**
 * Spring Data JPA repository for the ShelfType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShelfTypeRepository extends JpaRepository<ShelfType, Long> {

    Page<ShelfType> findAllByObyektId(Pageable pageable, Long obyektId);
}
