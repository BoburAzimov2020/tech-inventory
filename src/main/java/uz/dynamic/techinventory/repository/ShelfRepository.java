package uz.dynamic.techinventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.Shelf;

/**
 * Spring Data JPA repository for the Shelf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShelfRepository extends JpaRepository<Shelf, Long> {

    Page<Shelf> findAllByShelfTypeId(Pageable pageable, Long shelfTypeId);

    Integer countByObyektId(Long obyektId);

    Page<Shelf> findAllByObyektId(Pageable pageable, Long obyektId);

}
