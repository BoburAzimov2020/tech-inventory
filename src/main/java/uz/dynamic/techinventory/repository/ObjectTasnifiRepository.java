package uz.dynamic.techinventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.ObjectTasnifi;

/**
 * Spring Data JPA repository for the ObjectTasnifi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ObjectTasnifiRepository extends JpaRepository<ObjectTasnifi, Long> {

    Page<ObjectTasnifi> findAllByBjectTasnifiTuriId(Pageable pageable, Long objectTasnifiTuriId);
}
