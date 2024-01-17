package uz.dynamic.techinventory.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.dynamic.techinventory.service.dto.CameraBrandDTO;

/**
 * Service Interface for managing {@link uz.dynamic.techinventory.domain.CameraBrand}.
 */
public interface CameraBrandService {
    /**
     * Save a cameraBrand.
     *
     * @param cameraBrandDTO the entity to save.
     * @return the persisted entity.
     */
    CameraBrandDTO save(CameraBrandDTO cameraBrandDTO);

    /**
     * Updates a cameraBrand.
     *
     * @param cameraBrandDTO the entity to update.
     * @return the persisted entity.
     */
    CameraBrandDTO update(CameraBrandDTO cameraBrandDTO);

    /**
     * Partially updates a cameraBrand.
     *
     * @param cameraBrandDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CameraBrandDTO> partialUpdate(CameraBrandDTO cameraBrandDTO);

    /**
     * Get all the cameraBrands.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CameraBrandDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cameraBrand.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CameraBrandDTO> findOne(Long id);

    /**
     * Delete the "id" cameraBrand.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
