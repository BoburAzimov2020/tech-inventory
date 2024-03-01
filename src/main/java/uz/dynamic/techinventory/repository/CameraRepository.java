package uz.dynamic.techinventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.Camera;

/**
 * Spring Data JPA repository for the Camera entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CameraRepository extends JpaRepository<Camera, Long> {

    Page<Camera> findAllByCameraTypeId(Pageable pageable, Long cameraTypeId);

    Page<Camera> findAllByCameraBrandId(Pageable pageable, Long cameraBrandId);

    Integer countByObyektId(Long obyektId);

    Page<Camera> findAllByObyektId(Pageable pageable, Long obyektId);

}
