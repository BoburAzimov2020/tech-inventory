package uz.dynamic.techinventory.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.dynamic.techinventory.service.dto.BuyurtmaRaqamDTO;

/**
 * Service Interface for managing {@link uz.dynamic.techinventory.domain.BuyurtmaRaqam}.
 */
public interface BuyurtmaRaqamService {
    /**
     * Save a buyurtmaRaqam.
     *
     * @param buyurtmaRaqamDTO the entity to save.
     * @return the persisted entity.
     */
    BuyurtmaRaqamDTO save(BuyurtmaRaqamDTO buyurtmaRaqamDTO);

    /**
     * Updates a buyurtmaRaqam.
     *
     * @param buyurtmaRaqamDTO the entity to update.
     * @return the persisted entity.
     */
    BuyurtmaRaqamDTO update(BuyurtmaRaqamDTO buyurtmaRaqamDTO);

    /**
     * Partially updates a buyurtmaRaqam.
     *
     * @param buyurtmaRaqamDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BuyurtmaRaqamDTO> partialUpdate(BuyurtmaRaqamDTO buyurtmaRaqamDTO);

    /**
     * Get all the buyurtmaRaqams.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BuyurtmaRaqamDTO> findAll(Pageable pageable);

    /**
     * Get the "id" buyurtmaRaqam.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BuyurtmaRaqamDTO> findOne(Long id);

    /**
     * Delete the "id" buyurtmaRaqam.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
