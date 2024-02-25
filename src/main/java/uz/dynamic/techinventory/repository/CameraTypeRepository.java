package uz.dynamic.techinventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.CameraType;

/**
 * Spring Data JPA repository for the CameraType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CameraTypeRepository extends JpaRepository<CameraType, Long> {

    Page<CameraType> findAllByObyektId(Pageable pageable, Long obyektId);

}
