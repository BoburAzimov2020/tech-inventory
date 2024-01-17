package uz.dynamic.techinventory.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.TerminalServer;

/**
 * Spring Data JPA repository for the TerminalServer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TerminalServerRepository extends JpaRepository<TerminalServer, Long> {}
