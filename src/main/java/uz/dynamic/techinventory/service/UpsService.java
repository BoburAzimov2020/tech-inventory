package uz.dynamic.techinventory.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.dynamic.techinventory.service.dto.UpsDTO;

/**
 * Service Interface for managing {@link uz.dynamic.techinventory.domain.Ups}.
 */
public interface UpsService {
    /**
     * Save a ups.
     *
     * @param upsDTO the entity to save.
     * @return the persisted entity.
     */
    UpsDTO save(UpsDTO upsDTO);

    /**
     * Updates a ups.
     *
     * @param upsDTO the entity to update.
     * @return the persisted entity.
     */
    UpsDTO update(UpsDTO upsDTO);

    /**
     * Partially updates a ups.
     *
     * @param upsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UpsDTO> partialUpdate(UpsDTO upsDTO);

    /**
     * Get all the ups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UpsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" ups.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UpsDTO> findOne(Long id);

    /**
     * Delete the "id" ups.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
