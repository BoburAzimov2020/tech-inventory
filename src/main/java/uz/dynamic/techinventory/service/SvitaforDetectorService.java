package uz.dynamic.techinventory.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.dynamic.techinventory.service.dto.SvitaforDetectorDTO;

/**
 * Service Interface for managing {@link uz.dynamic.techinventory.domain.SvitaforDetector}.
 */
public interface SvitaforDetectorService {
    /**
     * Save a svitaforDetector.
     *
     * @param svitaforDetectorDTO the entity to save.
     * @return the persisted entity.
     */
    SvitaforDetectorDTO save(SvitaforDetectorDTO svitaforDetectorDTO);

    /**
     * Updates a svitaforDetector.
     *
     * @param svitaforDetectorDTO the entity to update.
     * @return the persisted entity.
     */
    SvitaforDetectorDTO update(SvitaforDetectorDTO svitaforDetectorDTO);

    /**
     * Partially updates a svitaforDetector.
     *
     * @param svitaforDetectorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SvitaforDetectorDTO> partialUpdate(SvitaforDetectorDTO svitaforDetectorDTO);

    /**
     * Get all the svitaforDetectors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SvitaforDetectorDTO> findAll(Pageable pageable);

    /**
     * Get the "id" svitaforDetector.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SvitaforDetectorDTO> findOne(Long id);

    /**
     * Delete the "id" svitaforDetector.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
