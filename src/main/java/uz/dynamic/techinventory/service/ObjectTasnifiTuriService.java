package uz.dynamic.techinventory.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.dynamic.techinventory.service.dto.ObjectTasnifiTuriDTO;

/**
 * Service Interface for managing {@link uz.dynamic.techinventory.domain.ObjectTasnifiTuri}.
 */
public interface ObjectTasnifiTuriService {
    /**
     * Save a objectTasnifiTuri.
     *
     * @param objectTasnifiTuriDTO the entity to save.
     * @return the persisted entity.
     */
    ObjectTasnifiTuriDTO save(ObjectTasnifiTuriDTO objectTasnifiTuriDTO);

    /**
     * Updates a objectTasnifiTuri.
     *
     * @param objectTasnifiTuriDTO the entity to update.
     * @return the persisted entity.
     */
    ObjectTasnifiTuriDTO update(ObjectTasnifiTuriDTO objectTasnifiTuriDTO);

    /**
     * Partially updates a objectTasnifiTuri.
     *
     * @param objectTasnifiTuriDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ObjectTasnifiTuriDTO> partialUpdate(ObjectTasnifiTuriDTO objectTasnifiTuriDTO);

    /**
     * Get all the objectTasnifiTuris.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ObjectTasnifiTuriDTO> findAll(Pageable pageable);

    /**
     * Get the "id" objectTasnifiTuri.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ObjectTasnifiTuriDTO> findOne(Long id);

    /**
     * Delete the "id" objectTasnifiTuri.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

}
