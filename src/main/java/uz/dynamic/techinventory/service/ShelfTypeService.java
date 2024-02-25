package uz.dynamic.techinventory.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.dynamic.techinventory.service.dto.ShelfTypeDTO;

/**
 * Service Interface for managing {@link uz.dynamic.techinventory.domain.ShelfType}.
 */
public interface ShelfTypeService {
    /**
     * Save a shelfType.
     *
     * @param shelfTypeDTO the entity to save.
     * @return the persisted entity.
     */
    ShelfTypeDTO save(ShelfTypeDTO shelfTypeDTO);

    /**
     * Updates a shelfType.
     *
     * @param shelfTypeDTO the entity to update.
     * @return the persisted entity.
     */
    ShelfTypeDTO update(ShelfTypeDTO shelfTypeDTO);

    /**
     * Partially updates a shelfType.
     *
     * @param shelfTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ShelfTypeDTO> partialUpdate(ShelfTypeDTO shelfTypeDTO);

    /**
     * Get all the shelfTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ShelfTypeDTO> findAll(Pageable pageable);

    Page<ShelfTypeDTO> findAllByObyekt(Pageable pageable, Long obyektId);

    /**
     * Get the "id" shelfType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShelfTypeDTO> findOne(Long id);

    /**
     * Delete the "id" shelfType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
