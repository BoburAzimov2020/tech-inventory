package uz.dynamic.techinventory.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.dynamic.techinventory.service.dto.ShelfDTO;

/**
 * Service Interface for managing {@link uz.dynamic.techinventory.domain.Shelf}.
 */
public interface ShelfService {
    /**
     * Save a shelf.
     *
     * @param shelfDTO the entity to save.
     * @return the persisted entity.
     */
    ShelfDTO save(ShelfDTO shelfDTO);

    /**
     * Updates a shelf.
     *
     * @param shelfDTO the entity to update.
     * @return the persisted entity.
     */
    ShelfDTO update(ShelfDTO shelfDTO);

    /**
     * Partially updates a shelf.
     *
     * @param shelfDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ShelfDTO> partialUpdate(ShelfDTO shelfDTO);

    /**
     * Get all the shelves.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ShelfDTO> findAll(Pageable pageable);

    Page<ShelfDTO> findAllByObyekt(Pageable pageable, Long obyektId);

    Page<ShelfDTO> findAllByShelfType(Pageable pageable, Long shelfTypeId);

    /**
     * Get the "id" shelf.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShelfDTO> findOne(Long id);

    /**
     * Delete the "id" shelf.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
