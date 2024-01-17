package uz.dynamic.techinventory.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.CameraBrand;

/**
 * Spring Data JPA repository for the CameraBrand entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CameraBrandRepository extends JpaRepository<CameraBrand, Long> {}
