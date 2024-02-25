package uz.dynamic.techinventory.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.dynamic.techinventory.service.dto.LoyihaDTO;

/**
 * Service Interface for managing {@link uz.dynamic.techinventory.domain.Loyiha}.
 */
public interface LoyihaService {
    /**
     * Save a loyiha.
     *
     * @param loyihaDTO the entity to save.
     * @return the persisted entity.
     */
    LoyihaDTO save(LoyihaDTO loyihaDTO);

    /**
     * Updates a loyiha.
     *
     * @param loyihaDTO the entity to update.
     * @return the persisted entity.
     */
    LoyihaDTO update(LoyihaDTO loyihaDTO);

    /**
     * Partially updates a loyiha.
     *
     * @param loyihaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LoyihaDTO> partialUpdate(LoyihaDTO loyihaDTO);

    /**
     * Get all the loyihas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LoyihaDTO> findAll(Pageable pageable);

    Page<LoyihaDTO> findAllByObjectTasnifi(Pageable pageable, Long objectTasnifiId);

    /**
     * Get the "id" loyiha.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LoyihaDTO> findOne(Long id);

    /**
     * Delete the "id" loyiha.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
