package uz.dynamic.techinventory.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.dynamic.techinventory.service.dto.ProjectorDTO;

/**
 * Service Interface for managing {@link uz.dynamic.techinventory.domain.Projector}.
 */
public interface ProjectorService {
    /**
     * Save a projector.
     *
     * @param projectorDTO the entity to save.
     * @return the persisted entity.
     */
    ProjectorDTO save(ProjectorDTO projectorDTO);

    /**
     * Updates a projector.
     *
     * @param projectorDTO the entity to update.
     * @return the persisted entity.
     */
    ProjectorDTO update(ProjectorDTO projectorDTO);

    /**
     * Partially updates a projector.
     *
     * @param projectorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProjectorDTO> partialUpdate(ProjectorDTO projectorDTO);

    /**
     * Get all the projectors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProjectorDTO> findAll(Pageable pageable);

    Page<ProjectorDTO> findAllByProjectorType(Pageable pageable, Long projectorTypeId);

    /**
     * Get the "id" projector.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProjectorDTO> findOne(Long id);

    /**
     * Delete the "id" projector.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
