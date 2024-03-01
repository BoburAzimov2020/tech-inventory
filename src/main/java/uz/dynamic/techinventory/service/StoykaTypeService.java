package uz.dynamic.techinventory.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.dynamic.techinventory.service.dto.StoykaTypeDTO;

/**
 * Service Interface for managing {@link uz.dynamic.techinventory.domain.StoykaType}.
 */
public interface StoykaTypeService {
    /**
     * Save a stoykaType.
     *
     * @param stoykaTypeDTO the entity to save.
     * @return the persisted entity.
     */
    StoykaTypeDTO save(StoykaTypeDTO stoykaTypeDTO);

    /**
     * Updates a stoykaType.
     *
     * @param stoykaTypeDTO the entity to update.
     * @return the persisted entity.
     */
    StoykaTypeDTO update(StoykaTypeDTO stoykaTypeDTO);

    /**
     * Partially updates a stoykaType.
     *
     * @param stoykaTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StoykaTypeDTO> partialUpdate(StoykaTypeDTO stoykaTypeDTO);

    /**
     * Get all the stoykaTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StoykaTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" stoykaType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StoykaTypeDTO> findOne(Long id);

    /**
     * Delete the "id" stoykaType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
