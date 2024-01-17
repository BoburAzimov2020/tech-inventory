package uz.dynamic.techinventory.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.Loyiha;

/**
 * Spring Data JPA repository for the Loyiha entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoyihaRepository extends JpaRepository<Loyiha, Long> {}
