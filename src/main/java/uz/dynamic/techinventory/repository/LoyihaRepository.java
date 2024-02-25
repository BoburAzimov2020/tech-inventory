package uz.dynamic.techinventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.Loyiha;

/**
 * Spring Data JPA repository for the Loyiha entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoyihaRepository extends JpaRepository<Loyiha, Long> {

    Page<Loyiha> findAllByObjectTasnifiId(Pageable pageable, Long objectTasnifiId);

}
