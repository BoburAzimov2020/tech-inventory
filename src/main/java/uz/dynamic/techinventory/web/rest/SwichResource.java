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

import uz.dynamic.techinventory.repository.SwichRepository;
import uz.dynamic.techinventory.service.SwichService;
import uz.dynamic.techinventory.service.dto.SwichDTO;
import uz.dynamic.techinventory.web.rest.errors.BadRequestAlertException;
import uz.dynamic.techinventory.web.rest.utils.HeaderUtil;
import uz.dynamic.techinventory.web.rest.utils.PaginationUtil;
import uz.dynamic.techinventory.web.rest.utils.ResponseUtil;
import org.springdoc.api.annotations.ParameterObject;

/**
 * REST controller for managing {@link uz.dynamic.techinventory.domain.Swich}.
 */
@RestController
@RequestMapping("/api/swiches")
public class SwichResource {

    private final Logger log = LoggerFactory.getLogger(SwichResource.class);

    private static final String ENTITY_NAME = "swich";

    @Value("${spring.application.name}")
    private String applicationName;

    private final SwichService swichService;

    private final SwichRepository swichRepository;

    public SwichResource(SwichService swichService, SwichRepository swichRepository) {
        this.swichService = swichService;
        this.swichRepository = swichRepository;
    }

    /**
     * {@code POST  /swiches} : Create a new swich.
     *
     * @param swichDTO the swichDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new swichDTO, or with status {@code 400 (Bad Request)} if the swich has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SwichDTO> createSwich(@Valid @RequestBody SwichDTO swichDTO) throws URISyntaxException {
        log.debug("REST request to save Swich : {}", swichDTO);
        if (swichDTO.getId() != null) {
            throw new BadRequestAlertException("A new swich cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SwichDTO result = swichService.save(swichDTO);
        return ResponseEntity
            .created(new URI("/api/swiches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /swiches/:id} : Updates an existing swich.
     *
     * @param id the id of the swichDTO to save.
     * @param swichDTO the swichDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated swichDTO,
     * or with status {@code 400 (Bad Request)} if the swichDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the swichDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SwichDTO> updateSwich(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SwichDTO swichDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Swich : {}, {}", id, swichDTO);
        if (swichDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, swichDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!swichRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SwichDTO result = swichService.update(swichDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, swichDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /swiches/:id} : Partial updates given fields of an existing swich, field will ignore if it is null
     *
     * @param id the id of the swichDTO to save.
     * @param swichDTO the swichDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated swichDTO,
     * or with status {@code 400 (Bad Request)} if the swichDTO is not valid,
     * or with status {@code 404 (Not Found)} if the swichDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the swichDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SwichDTO> partialUpdateSwich(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SwichDTO swichDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Swich partially : {}, {}", id, swichDTO);
        if (swichDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, swichDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!swichRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SwichDTO> result = swichService.partialUpdate(swichDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, swichDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /swiches} : get all the swiches.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of swiches in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SwichDTO>> getAllSwiches(@ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Swiches");
        Page<SwichDTO> page = swichService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /swiches/:id} : get the "id" swich.
     *
     * @param id the id of the swichDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the swichDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SwichDTO> getSwich(@PathVariable("id") Long id) {
        log.debug("REST request to get Swich : {}", id);
        Optional<SwichDTO> swichDTO = swichService.findOne(id);
        return ResponseUtil.wrapOrNotFound(swichDTO);
    }

    /**
     * {@code DELETE  /swiches/:id} : delete the "id" swich.
     *
     * @param id the id of the swichDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSwich(@PathVariable("id") Long id) {
        log.debug("REST request to delete Swich : {}", id);
        swichService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
