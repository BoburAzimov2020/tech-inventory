package uz.dynamic.techinventory.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.dynamic.techinventory.service.dto.TerminalServerDTO;

/**
 * Service Interface for managing {@link uz.dynamic.techinventory.domain.TerminalServer}.
 */
public interface TerminalServerService {
    /**
     * Save a terminalServer.
     *
     * @param terminalServerDTO the entity to save.
     * @return the persisted entity.
     */
    TerminalServerDTO save(TerminalServerDTO terminalServerDTO);

    /**
     * Updates a terminalServer.
     *
     * @param terminalServerDTO the entity to update.
     * @return the persisted entity.
     */
    TerminalServerDTO update(TerminalServerDTO terminalServerDTO);

    /**
     * Partially updates a terminalServer.
     *
     * @param terminalServerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TerminalServerDTO> partialUpdate(TerminalServerDTO terminalServerDTO);

    /**
     * Get all the terminalServers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TerminalServerDTO> findAll(Pageable pageable);

    Page<TerminalServerDTO> findAllByObyekt(Pageable pageable, Long obyektId);

    /**
     * Get the "id" terminalServer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TerminalServerDTO> findOne(Long id);

    /**
     * Delete the "id" terminalServer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
