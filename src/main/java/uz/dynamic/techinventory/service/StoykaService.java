package uz.dynamic.techinventory.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.dynamic.techinventory.service.dto.StoykaDTO;

/**
 * Service Interface for managing {@link uz.dynamic.techinventory.domain.Stoyka}.
 */
public interface StoykaService {
    /**
     * Save a stoyka.
     *
     * @param stoykaDTO the entity to save.
     * @return the persisted entity.
     */
    StoykaDTO save(StoykaDTO stoykaDTO);

    /**
     * Updates a stoyka.
     *
     * @param stoykaDTO the entity to update.
     * @return the persisted entity.
     */
    StoykaDTO update(StoykaDTO stoykaDTO);

    /**
     * Partially updates a stoyka.
     *
     * @param stoykaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StoykaDTO> partialUpdate(StoykaDTO stoykaDTO);

    /**
     * Get all the stoykas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StoykaDTO> findAll(Pageable pageable);

    Page<StoykaDTO> findAllByStoykaType(Pageable pageable, Long stoykaTypeId);

    /**
     * Get the "id" stoyka.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StoykaDTO> findOne(Long id);

    /**
     * Delete the "id" stoyka.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
