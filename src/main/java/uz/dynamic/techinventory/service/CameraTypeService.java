package uz.dynamic.techinventory.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.dynamic.techinventory.service.dto.CameraTypeDTO;

/**
 * Service Interface for managing {@link uz.dynamic.techinventory.domain.CameraType}.
 */
public interface CameraTypeService {
    /**
     * Save a cameraType.
     *
     * @param cameraTypeDTO the entity to save.
     * @return the persisted entity.
     */
    CameraTypeDTO save(CameraTypeDTO cameraTypeDTO);

    /**
     * Updates a cameraType.
     *
     * @param cameraTypeDTO the entity to update.
     * @return the persisted entity.
     */
    CameraTypeDTO update(CameraTypeDTO cameraTypeDTO);

    /**
     * Partially updates a cameraType.
     *
     * @param cameraTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CameraTypeDTO> partialUpdate(CameraTypeDTO cameraTypeDTO);

    /**
     * Get all the cameraTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CameraTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cameraType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CameraTypeDTO> findOne(Long id);

    /**
     * Delete the "id" cameraType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
