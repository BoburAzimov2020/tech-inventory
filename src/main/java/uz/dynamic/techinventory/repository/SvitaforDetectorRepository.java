package uz.dynamic.techinventory.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.SvitaforDetector;

/**
 * Spring Data JPA repository for the SvitaforDetector entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SvitaforDetectorRepository extends JpaRepository<SvitaforDetector, Long> {}
