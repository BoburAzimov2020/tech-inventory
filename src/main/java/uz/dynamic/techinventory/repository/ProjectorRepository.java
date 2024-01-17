package uz.dynamic.techinventory.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.Projector;

/**
 * Spring Data JPA repository for the Projector entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectorRepository extends JpaRepository<Projector, Long> {}
