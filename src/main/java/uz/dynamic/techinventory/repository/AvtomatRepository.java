package uz.dynamic.techinventory.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.Avtomat;

/**
 * Spring Data JPA repository for the Avtomat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvtomatRepository extends JpaRepository<Avtomat, Long> {}
