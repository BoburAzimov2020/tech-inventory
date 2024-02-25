package uz.dynamic.techinventory.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.dynamic.techinventory.service.dto.AvtomatDTO;

/**
 * Service Interface for managing {@link uz.dynamic.techinventory.domain.Avtomat}.
 */
public interface AvtomatService {
    /**
     * Save a avtomat.
     *
     * @param avtomatDTO the entity to save.
     * @return the persisted entity.
     */
    AvtomatDTO save(AvtomatDTO avtomatDTO);

    /**
     * Updates a avtomat.
     *
     * @param avtomatDTO the entity to update.
     * @return the persisted entity.
     */
    AvtomatDTO update(AvtomatDTO avtomatDTO);

    /**
     * Partially updates a avtomat.
     *
     * @param avtomatDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AvtomatDTO> partialUpdate(AvtomatDTO avtomatDTO);

    /**
     * Get all the avtomats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AvtomatDTO> findAll(Pageable pageable);

    Page<AvtomatDTO> findAllByObyekt(Pageable pageable, Long obyektId);

    /**
     * Get the "id" avtomat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AvtomatDTO> findOne(Long id);

    /**
     * Delete the "id" avtomat.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
