package uz.dynamic.techinventory.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import uz.dynamic.techinventory.repository.StabilizatorRepository;
import uz.dynamic.techinventory.service.StabilizatorService;
import uz.dynamic.techinventory.service.dto.StabilizatorDTO;
import uz.dynamic.techinventory.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uz.dynamic.techinventory.domain.Stabilizator}.
 */
@RestController
@RequestMapping("/api/stabilizators")
public class StabilizatorResource {

    private final Logger log = LoggerFactory.getLogger(StabilizatorResource.class);

    private static final String ENTITY_NAME = "stabilizator";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StabilizatorService stabilizatorService;

    private final StabilizatorRepository stabilizatorRepository;

    public StabilizatorResource(StabilizatorService stabilizatorService, StabilizatorRepository stabilizatorRepository) {
        this.stabilizatorService = stabilizatorService;
        this.stabilizatorRepository = stabilizatorRepository;
    }

    /**
     * {@code POST  /stabilizators} : Create a new stabilizator.
     *
     * @param stabilizatorDTO the stabilizatorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stabilizatorDTO, or with status {@code 400 (Bad Request)} if the stabilizator has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<StabilizatorDTO> createStabilizator(@Valid @RequestBody StabilizatorDTO stabilizatorDTO)
        throws URISyntaxException {
        log.debug("REST request to save Stabilizator : {}", stabilizatorDTO);
        if (stabilizatorDTO.getId() != null) {
            throw new BadRequestAlertException("A new stabilizator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StabilizatorDTO result = stabilizatorService.save(stabilizatorDTO);
        return ResponseEntity
            .created(new URI("/api/stabilizators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stabilizators/:id} : Updates an existing stabilizator.
     *
     * @param id the id of the stabilizatorDTO to save.
     * @param stabilizatorDTO the stabilizatorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stabilizatorDTO,
     * or with status {@code 400 (Bad Request)} if the stabilizatorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stabilizatorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StabilizatorDTO> updateStabilizator(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StabilizatorDTO stabilizatorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Stabilizator : {}, {}", id, stabilizatorDTO);
        if (stabilizatorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stabilizatorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stabilizatorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StabilizatorDTO result = stabilizatorService.update(stabilizatorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stabilizatorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /stabilizators/:id} : Partial updates given fields of an existing stabilizator, field will ignore if it is null
     *
     * @param id the id of the stabilizatorDTO to save.
     * @param stabilizatorDTO the stabilizatorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stabilizatorDTO,
     * or with status {@code 400 (Bad Request)} if the stabilizatorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the stabilizatorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the stabilizatorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StabilizatorDTO> partialUpdateStabilizator(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StabilizatorDTO stabilizatorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Stabilizator partially : {}, {}", id, stabilizatorDTO);
        if (stabilizatorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stabilizatorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stabilizatorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StabilizatorDTO> result = stabilizatorService.partialUpdate(stabilizatorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stabilizatorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /stabilizators} : get all the stabilizators.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stabilizators in body.
     */
    @GetMapping("")
    public ResponseEntity<List<StabilizatorDTO>> getAllStabilizators(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Stabilizators");
        Page<StabilizatorDTO> page = stabilizatorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /stabilizators/:id} : get the "id" stabilizator.
     *
     * @param id the id of the stabilizatorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stabilizatorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StabilizatorDTO> getStabilizator(@PathVariable("id") Long id) {
        log.debug("REST request to get Stabilizator : {}", id);
        Optional<StabilizatorDTO> stabilizatorDTO = stabilizatorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stabilizatorDTO);
    }

    /**
     * {@code DELETE  /stabilizators/:id} : delete the "id" stabilizator.
     *
     * @param id the id of the stabilizatorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStabilizator(@PathVariable("id") Long id) {
        log.debug("REST request to delete Stabilizator : {}", id);
        stabilizatorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
