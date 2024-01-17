package uz.dynamic.techinventory.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.Obyekt;

/**
 * Spring Data JPA repository for the Obyekt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ObyektRepository extends JpaRepository<Obyekt, Long> {}
