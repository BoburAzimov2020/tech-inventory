package uz.dynamic.techinventory.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.dynamic.techinventory.service.dto.ObjectTasnifiDTO;

/**
 * Service Interface for managing {@link uz.dynamic.techinventory.domain.ObjectTasnifi}.
 */
public interface ObjectTasnifiService {
    /**
     * Save a objectTasnifi.
     *
     * @param objectTasnifiDTO the entity to save.
     * @return the persisted entity.
     */
    ObjectTasnifiDTO save(ObjectTasnifiDTO objectTasnifiDTO);

    /**
     * Updates a objectTasnifi.
     *
     * @param objectTasnifiDTO the entity to update.
     * @return the persisted entity.
     */
    ObjectTasnifiDTO update(ObjectTasnifiDTO objectTasnifiDTO);

    /**
     * Partially updates a objectTasnifi.
     *
     * @param objectTasnifiDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ObjectTasnifiDTO> partialUpdate(ObjectTasnifiDTO objectTasnifiDTO);

    /**
     * Get all the objectTasnifis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ObjectTasnifiDTO> findAll(Pageable pageable);

    Page<ObjectTasnifiDTO> findAllByObjectTasnifiTuri(Pageable pageable, Long objectTasnifiTuriId);

    /**
     * Get the "id" objectTasnifi.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ObjectTasnifiDTO> findOne(Long id);

    /**
     * Delete the "id" objectTasnifi.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
