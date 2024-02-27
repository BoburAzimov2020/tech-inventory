package uz.dynamic.techinventory.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.dynamic.techinventory.service.dto.ObyektDTO;
import uz.dynamic.techinventory.service.dto.ObyektFilterDTO;

/**
 * Service Interface for managing {@link uz.dynamic.techinventory.domain.Obyekt}.
 */
public interface ObyektService {
    /**
     * Save a obyekt.
     *
     * @param obyektDTO the entity to save.
     * @return the persisted entity.
     */
    ObyektDTO save(ObyektDTO obyektDTO);

    /**
     * Updates a obyekt.
     *
     * @param obyektDTO the entity to update.
     * @return the persisted entity.
     */
    ObyektDTO update(ObyektDTO obyektDTO);

    /**
     * Partially updates a obyekt.
     *
     * @param obyektDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ObyektDTO> partialUpdate(ObyektDTO obyektDTO);

    /**
     * Get all the obyekts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ObyektDTO> findAll(Pageable pageable);

    Page<ObyektDTO> findAllByBuyurtmaRaqamId(Pageable pageable, Long buyurtmaRaqamId);
    Page<ObyektDTO> findAllByFilter(Pageable pageable, ObyektFilterDTO obyektFilterDTO);

    /**
     * Get the "id" obyekt.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ObyektDTO> findOne(Long id);

    /**
     * Delete the "id" obyekt.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
