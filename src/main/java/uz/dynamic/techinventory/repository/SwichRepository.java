package uz.dynamic.techinventory.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.Swich;

/**
 * Spring Data JPA repository for the Swich entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SwichRepository extends JpaRepository<Swich, Long> {}
