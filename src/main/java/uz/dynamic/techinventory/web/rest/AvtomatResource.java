package uz.dynamic.techinventory.web.rest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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

import uz.dynamic.techinventory.repository.AvtomatRepository;
import uz.dynamic.techinventory.service.AvtomatService;
import uz.dynamic.techinventory.service.dto.AvtomatDTO;
import uz.dynamic.techinventory.web.rest.errors.BadRequestAlertException;
import uz.dynamic.techinventory.web.rest.utils.HeaderUtil;
import uz.dynamic.techinventory.web.rest.utils.PaginationUtil;
import uz.dynamic.techinventory.web.rest.utils.ResponseUtil;
import org.springdoc.api.annotations.ParameterObject;

/**
 * REST controller for managing {@link uz.dynamic.techinventory.domain.Avtomat}.
 */
@RestController
@RequestMapping("/api/avtomats")
public class AvtomatResource {

    private final Logger log = LoggerFactory.getLogger(AvtomatResource.class);

    private static final String ENTITY_NAME = "avtomat";

    @Value("${spring.application.name}")
    private String applicationName;

    private final AvtomatService avtomatService;

    private final AvtomatRepository avtomatRepository;

    public AvtomatResource(AvtomatService avtomatService, AvtomatRepository avtomatRepository) {
        this.avtomatService = avtomatService;
        this.avtomatRepository = avtomatRepository;
    }

    /**
     * {@code POST  /avtomats} : Create a new avtomat.
     *
     * @param avtomatDTO the avtomatDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new avtomatDTO, or with status {@code 400 (Bad Request)} if the avtomat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AvtomatDTO> createAvtomat(@Valid @RequestBody AvtomatDTO avtomatDTO) throws URISyntaxException {
        log.debug("REST request to save Avtomat : {}", avtomatDTO);
        if (avtomatDTO.getId() != null) {
            throw new BadRequestAlertException("A new avtomat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AvtomatDTO result = avtomatService.save(avtomatDTO);
        return ResponseEntity
            .created(new URI("/api/avtomats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /avtomats/:id} : Updates an existing avtomat.
     *
     * @param id the id of the avtomatDTO to save.
     * @param avtomatDTO the avtomatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avtomatDTO,
     * or with status {@code 400 (Bad Request)} if the avtomatDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the avtomatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AvtomatDTO> updateAvtomat(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AvtomatDTO avtomatDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Avtomat : {}, {}", id, avtomatDTO);
        if (avtomatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, avtomatDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!avtomatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AvtomatDTO result = avtomatService.update(avtomatDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, avtomatDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /avtomats/:id} : Partial updates given fields of an existing avtomat, field will ignore if it is null
     *
     * @param id the id of the avtomatDTO to save.
     * @param avtomatDTO the avtomatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avtomatDTO,
     * or with status {@code 400 (Bad Request)} if the avtomatDTO is not valid,
     * or with status {@code 404 (Not Found)} if the avtomatDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the avtomatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AvtomatDTO> partialUpdateAvtomat(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AvtomatDTO avtomatDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Avtomat partially : {}, {}", id, avtomatDTO);
        if (avtomatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, avtomatDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!avtomatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AvtomatDTO> result = avtomatService.partialUpdate(avtomatDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, avtomatDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /avtomats} : get all the avtomats.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of avtomats in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AvtomatDTO>> getAllAvtomats(@ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Avtomats");
        Page<AvtomatDTO> page = avtomatService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /avtomats/:id} : get the "id" avtomat.
     *
     * @param id the id of the avtomatDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the avtomatDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AvtomatDTO> getAvtomat(@PathVariable("id") Long id) {
        log.debug("REST request to get Avtomat : {}", id);
        Optional<AvtomatDTO> avtomatDTO = avtomatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(avtomatDTO);
    }

    /**
     * {@code DELETE  /avtomats/:id} : delete the "id" avtomat.
     *
     * @param id the id of the avtomatDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvtomat(@PathVariable("id") Long id) {
        log.debug("REST request to delete Avtomat : {}", id);
        avtomatService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
