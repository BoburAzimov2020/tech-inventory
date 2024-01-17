package uz.dynamic.techinventory.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.Cabel;

/**
 * Spring Data JPA repository for the Cabel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CabelRepository extends JpaRepository<Cabel, Long> {}