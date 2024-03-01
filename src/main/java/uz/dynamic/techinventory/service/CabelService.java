package uz.dynamic.techinventory.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.dynamic.techinventory.service.dto.CabelDTO;

/**
 * Service Interface for managing {@link uz.dynamic.techinventory.domain.Cabel}.
 */
public interface CabelService {
    /**
     * Save a cabel.
     *
     * @param cabelDTO the entity to save.
     * @return the persisted entity.
     */
    CabelDTO save(CabelDTO cabelDTO);

    /**
     * Updates a cabel.
     *
     * @param cabelDTO the entity to update.
     * @return the persisted entity.
     */
    CabelDTO update(CabelDTO cabelDTO);

    /**
     * Partially updates a cabel.
     *
     * @param cabelDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CabelDTO> partialUpdate(CabelDTO cabelDTO);

    Page<CabelDTO> findAllByObyekt(Pageable pageable, Long obyektId);

    /**
     * Get all the cabels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CabelDTO> findAll(Pageable pageable);

    Page<CabelDTO> findAllByCabelType(Pageable pageable, Long cabelTypeId);

    /**
     * Get the "id" cabel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CabelDTO> findOne(Long id);

    /**
     * Delete the "id" cabel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
