package uz.dynamic.techinventory.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.dynamic.techinventory.service.dto.StabilizatorDTO;

/**
 * Service Interface for managing {@link uz.dynamic.techinventory.domain.Stabilizator}.
 */
public interface StabilizatorService {
    /**
     * Save a stabilizator.
     *
     * @param stabilizatorDTO the entity to save.
     * @return the persisted entity.
     */
    StabilizatorDTO save(StabilizatorDTO stabilizatorDTO);

    /**
     * Updates a stabilizator.
     *
     * @param stabilizatorDTO the entity to update.
     * @return the persisted entity.
     */
    StabilizatorDTO update(StabilizatorDTO stabilizatorDTO);

    /**
     * Partially updates a stabilizator.
     *
     * @param stabilizatorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StabilizatorDTO> partialUpdate(StabilizatorDTO stabilizatorDTO);

    /**
     * Get all the stabilizators.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StabilizatorDTO> findAll(Pageable pageable);

    Page<StabilizatorDTO> findAllByObyekt(Pageable pageable, Long obyektId);

    /**
     * Get the "id" stabilizator.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StabilizatorDTO> findOne(Long id);

    /**
     * Delete the "id" stabilizator.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
