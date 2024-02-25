package uz.dynamic.techinventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.StoykaType;

/**
 * Spring Data JPA repository for the StoykaType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StoykaTypeRepository extends JpaRepository<StoykaType, Long> {

    Page<StoykaType> findAllByObyektId(Pageable pageable, Long obyektId);

}
