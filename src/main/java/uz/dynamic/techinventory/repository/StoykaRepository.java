package uz.dynamic.techinventory.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.Stoyka;

/**
 * Spring Data JPA repository for the Stoyka entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StoykaRepository extends JpaRepository<Stoyka, Long> {}
