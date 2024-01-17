package uz.dynamic.techinventory.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.dynamic.techinventory.service.dto.CabelTypeDTO;

/**
 * Service Interface for managing {@link uz.dynamic.techinventory.domain.CabelType}.
 */
public interface CabelTypeService {
    /**
     * Save a cabelType.
     *
     * @param cabelTypeDTO the entity to save.
     * @return the persisted entity.
     */
    CabelTypeDTO save(CabelTypeDTO cabelTypeDTO);

    /**
     * Updates a cabelType.
     *
     * @param cabelTypeDTO the entity to update.
     * @return the persisted entity.
     */
    CabelTypeDTO update(CabelTypeDTO cabelTypeDTO);

    /**
     * Partially updates a cabelType.
     *
     * @param cabelTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CabelTypeDTO> partialUpdate(CabelTypeDTO cabelTypeDTO);

    /**
     * Get all the cabelTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CabelTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cabelType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CabelTypeDTO> findOne(Long id);

    /**
     * Delete the "id" cabelType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
