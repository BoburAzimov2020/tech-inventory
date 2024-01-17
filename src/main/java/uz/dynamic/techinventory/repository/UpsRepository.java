package uz.dynamic.techinventory.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.Ups;

/**
 * Spring Data JPA repository for the Ups entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UpsRepository extends JpaRepository<Ups, Long> {}
