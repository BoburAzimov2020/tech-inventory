package uz.dynamic.techinventory.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.dynamic.techinventory.service.dto.AkumulatorDTO;

/**
 * Service Interface for managing {@link uz.dynamic.techinventory.domain.Akumulator}.
 */
public interface AkumulatorService {
    /**
     * Save a akumulator.
     *
     * @param akumulatorDTO the entity to save.
     * @return the persisted entity.
     */
    AkumulatorDTO save(AkumulatorDTO akumulatorDTO);

    /**
     * Updates a akumulator.
     *
     * @param akumulatorDTO the entity to update.
     * @return the persisted entity.
     */
    AkumulatorDTO update(AkumulatorDTO akumulatorDTO);

    /**
     * Partially updates a akumulator.
     *
     * @param akumulatorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AkumulatorDTO> partialUpdate(AkumulatorDTO akumulatorDTO);

    /**
     * Get all the akumulators.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AkumulatorDTO> findAll(Pageable pageable);

    Page<AkumulatorDTO> findAllByObyekt(Pageable pageable, Long obyektId);

    /**
     * Get the "id" akumulator.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AkumulatorDTO> findOne(Long id);

    /**
     * Delete the "id" akumulator.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
