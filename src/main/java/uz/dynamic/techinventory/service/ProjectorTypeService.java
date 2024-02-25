package uz.dynamic.techinventory.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.dynamic.techinventory.service.dto.ProjectorTypeDTO;

/**
 * Service Interface for managing {@link uz.dynamic.techinventory.domain.ProjectorType}.
 */
public interface ProjectorTypeService {
    /**
     * Save a projectorType.
     *
     * @param projectorTypeDTO the entity to save.
     * @return the persisted entity.
     */
    ProjectorTypeDTO save(ProjectorTypeDTO projectorTypeDTO);

    /**
     * Updates a projectorType.
     *
     * @param projectorTypeDTO the entity to update.
     * @return the persisted entity.
     */
    ProjectorTypeDTO update(ProjectorTypeDTO projectorTypeDTO);

    /**
     * Partially updates a projectorType.
     *
     * @param projectorTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProjectorTypeDTO> partialUpdate(ProjectorTypeDTO projectorTypeDTO);

    /**
     * Get all the projectorTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProjectorTypeDTO> findAll(Pageable pageable);

    Page<ProjectorTypeDTO> findAllByOByekt(Pageable pageable, Long obyektId);

    /**
     * Get the "id" projectorType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProjectorTypeDTO> findOne(Long id);

    /**
     * Delete the "id" projectorType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
