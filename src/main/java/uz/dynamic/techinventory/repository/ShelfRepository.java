package uz.dynamic.techinventory.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.Shelf;

/**
 * Spring Data JPA repository for the Shelf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShelfRepository extends JpaRepository<Shelf, Long> {}
