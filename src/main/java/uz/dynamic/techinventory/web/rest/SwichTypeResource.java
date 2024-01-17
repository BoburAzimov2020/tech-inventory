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
import uz.dynamic.techinventory.repository.SwichTypeRepository;
import uz.dynamic.techinventory.service.SwichTypeService;
import uz.dynamic.techinventory.service.dto.SwichTypeDTO;
import uz.dynamic.techinventory.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uz.dynamic.techinventory.domain.SwichType}.
 */
@RestController
@RequestMapping("/api/swich-types")
public class SwichTypeResource {

    private final Logger log = LoggerFactory.getLogger(SwichTypeResource.class);

    private static final String ENTITY_NAME = "swichType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SwichTypeService swichTypeService;

    private final SwichTypeRepository swichTypeRepository;

    public SwichTypeResource(SwichTypeService swichTypeService, SwichTypeRepository swichTypeRepository) {
        this.swichTypeService = swichTypeService;
        this.swichTypeRepository = swichTypeRepository;
    }

    /**
     * {@code POST  /swich-types} : Create a new swichType.
     *
     * @param swichTypeDTO the swichTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new swichTypeDTO, or with status {@code 400 (Bad Request)} if the swichType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SwichTypeDTO> createSwichType(@Valid @RequestBody SwichTypeDTO swichTypeDTO) throws URISyntaxException {
        log.debug("REST request to save SwichType : {}", swichTypeDTO);
        if (swichTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new swichType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SwichTypeDTO result = swichTypeService.save(swichTypeDTO);
        return ResponseEntity
            .created(new URI("/api/swich-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /swich-types/:id} : Updates an existing swichType.
     *
     * @param id the id of the swichTypeDTO to save.
     * @param swichTypeDTO the swichTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated swichTypeDTO,
     * or with status {@code 400 (Bad Request)} if the swichTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the swichTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SwichTypeDTO> updateSwichType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SwichTypeDTO swichTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SwichType : {}, {}", id, swichTypeDTO);
        if (swichTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, swichTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!swichTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SwichTypeDTO result = swichTypeService.update(swichTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, swichTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /swich-types/:id} : Partial updates given fields of an existing swichType, field will ignore if it is null
     *
     * @param id the id of the swichTypeDTO to save.
     * @param swichTypeDTO the swichTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated swichTypeDTO,
     * or with status {@code 400 (Bad Request)} if the swichTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the swichTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the swichTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SwichTypeDTO> partialUpdateSwichType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SwichTypeDTO swichTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SwichType partially : {}, {}", id, swichTypeDTO);
        if (swichTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, swichTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!swichTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SwichTypeDTO> result = swichTypeService.partialUpdate(swichTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, swichTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /swich-types} : get all the swichTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of swichTypes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SwichTypeDTO>> getAllSwichTypes(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of SwichTypes");
        Page<SwichTypeDTO> page = swichTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /swich-types/:id} : get the "id" swichType.
     *
     * @param id the id of the swichTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the swichTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SwichTypeDTO> getSwichType(@PathVariable("id") Long id) {
        log.debug("REST request to get SwichType : {}", id);
        Optional<SwichTypeDTO> swichTypeDTO = swichTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(swichTypeDTO);
    }

    /**
     * {@code DELETE  /swich-types/:id} : delete the "id" swichType.
     *
     * @param id the id of the swichTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSwichType(@PathVariable("id") Long id) {
        log.debug("REST request to delete SwichType : {}", id);
        swichTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
