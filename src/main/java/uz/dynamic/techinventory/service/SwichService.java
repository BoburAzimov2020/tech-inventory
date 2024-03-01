package uz.dynamic.techinventory.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.dynamic.techinventory.service.dto.SwichDTO;

/**
 * Service Interface for managing {@link uz.dynamic.techinventory.domain.Swich}.
 */
public interface SwichService {
    /**
     * Save a swich.
     *
     * @param swichDTO the entity to save.
     * @return the persisted entity.
     */
    SwichDTO save(SwichDTO swichDTO);

    /**
     * Updates a swich.
     *
     * @param swichDTO the entity to update.
     * @return the persisted entity.
     */
    SwichDTO update(SwichDTO swichDTO);

    /**
     * Partially updates a swich.
     *
     * @param swichDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SwichDTO> partialUpdate(SwichDTO swichDTO);

    /**
     * Get all the swiches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SwichDTO> findAll(Pageable pageable);

    Page<SwichDTO> findAllByObyekt(Pageable pageable, Long obyektId);

    Page<SwichDTO> findAllBySwitchType(Pageable pageable, Long switchTypeId);

    /**
     * Get the "id" swich.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SwichDTO> findOne(Long id);

    /**
     * Delete the "id" swich.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
