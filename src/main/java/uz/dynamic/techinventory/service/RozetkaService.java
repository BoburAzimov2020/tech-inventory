package uz.dynamic.techinventory.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.dynamic.techinventory.service.dto.RozetkaDTO;

/**
 * Service Interface for managing {@link uz.dynamic.techinventory.domain.Rozetka}.
 */
public interface RozetkaService {
    /**
     * Save a rozetka.
     *
     * @param rozetkaDTO the entity to save.
     * @return the persisted entity.
     */
    RozetkaDTO save(RozetkaDTO rozetkaDTO);

    /**
     * Updates a rozetka.
     *
     * @param rozetkaDTO the entity to update.
     * @return the persisted entity.
     */
    RozetkaDTO update(RozetkaDTO rozetkaDTO);

    /**
     * Partially updates a rozetka.
     *
     * @param rozetkaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RozetkaDTO> partialUpdate(RozetkaDTO rozetkaDTO);

    /**
     * Get all the rozetkas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RozetkaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" rozetka.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RozetkaDTO> findOne(Long id);

    /**
     * Delete the "id" rozetka.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
