package uz.dynamic.techinventory.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.dynamic.techinventory.service.dto.SwichTypeDTO;

/**
 * Service Interface for managing {@link uz.dynamic.techinventory.domain.SwichType}.
 */
public interface SwichTypeService {
    /**
     * Save a swichType.
     *
     * @param swichTypeDTO the entity to save.
     * @return the persisted entity.
     */
    SwichTypeDTO save(SwichTypeDTO swichTypeDTO);

    /**
     * Updates a swichType.
     *
     * @param swichTypeDTO the entity to update.
     * @return the persisted entity.
     */
    SwichTypeDTO update(SwichTypeDTO swichTypeDTO);

    /**
     * Partially updates a swichType.
     *
     * @param swichTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SwichTypeDTO> partialUpdate(SwichTypeDTO swichTypeDTO);

    /**
     * Get all the swichTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SwichTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" swichType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SwichTypeDTO> findOne(Long id);

    /**
     * Delete the "id" swichType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
