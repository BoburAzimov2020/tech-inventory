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
import uz.dynamic.techinventory.repository.ShelfTypeRepository;
import uz.dynamic.techinventory.service.ShelfTypeService;
import uz.dynamic.techinventory.service.dto.ShelfTypeDTO;
import uz.dynamic.techinventory.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uz.dynamic.techinventory.domain.ShelfType}.
 */
@RestController
@RequestMapping("/api/shelf-types")
public class ShelfTypeResource {

    private final Logger log = LoggerFactory.getLogger(ShelfTypeResource.class);

    private static final String ENTITY_NAME = "shelfType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShelfTypeService shelfTypeService;

    private final ShelfTypeRepository shelfTypeRepository;

    public ShelfTypeResource(ShelfTypeService shelfTypeService, ShelfTypeRepository shelfTypeRepository) {
        this.shelfTypeService = shelfTypeService;
        this.shelfTypeRepository = shelfTypeRepository;
    }

    /**
     * {@code POST  /shelf-types} : Create a new shelfType.
     *
     * @param shelfTypeDTO the shelfTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shelfTypeDTO, or with status {@code 400 (Bad Request)} if the shelfType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ShelfTypeDTO> createShelfType(@Valid @RequestBody ShelfTypeDTO shelfTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ShelfType : {}", shelfTypeDTO);
        if (shelfTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new shelfType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShelfTypeDTO result = shelfTypeService.save(shelfTypeDTO);
        return ResponseEntity
            .created(new URI("/api/shelf-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shelf-types/:id} : Updates an existing shelfType.
     *
     * @param id the id of the shelfTypeDTO to save.
     * @param shelfTypeDTO the shelfTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shelfTypeDTO,
     * or with status {@code 400 (Bad Request)} if the shelfTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shelfTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ShelfTypeDTO> updateShelfType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ShelfTypeDTO shelfTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ShelfType : {}, {}", id, shelfTypeDTO);
        if (shelfTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shelfTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shelfTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ShelfTypeDTO result = shelfTypeService.update(shelfTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, shelfTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /shelf-types/:id} : Partial updates given fields of an existing shelfType, field will ignore if it is null
     *
     * @param id the id of the shelfTypeDTO to save.
     * @param shelfTypeDTO the shelfTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shelfTypeDTO,
     * or with status {@code 400 (Bad Request)} if the shelfTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the shelfTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the shelfTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ShelfTypeDTO> partialUpdateShelfType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ShelfTypeDTO shelfTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ShelfType partially : {}, {}", id, shelfTypeDTO);
        if (shelfTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shelfTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shelfTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ShelfTypeDTO> result = shelfTypeService.partialUpdate(shelfTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, shelfTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /shelf-types} : get all the shelfTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shelfTypes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ShelfTypeDTO>> getAllShelfTypes(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ShelfTypes");
        Page<ShelfTypeDTO> page = shelfTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /shelf-types/:id} : get the "id" shelfType.
     *
     * @param id the id of the shelfTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shelfTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ShelfTypeDTO> getShelfType(@PathVariable("id") Long id) {
        log.debug("REST request to get ShelfType : {}", id);
        Optional<ShelfTypeDTO> shelfTypeDTO = shelfTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shelfTypeDTO);
    }

    /**
     * {@code DELETE  /shelf-types/:id} : delete the "id" shelfType.
     *
     * @param id the id of the shelfTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShelfType(@PathVariable("id") Long id) {
        log.debug("REST request to delete ShelfType : {}", id);
        shelfTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
