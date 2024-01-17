package uz.dynamic.techinventory.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.Akumulator;

/**
 * Spring Data JPA repository for the Akumulator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AkumulatorRepository extends JpaRepository<Akumulator, Long> {}
