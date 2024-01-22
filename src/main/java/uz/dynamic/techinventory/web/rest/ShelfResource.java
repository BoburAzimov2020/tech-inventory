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

import uz.dynamic.techinventory.repository.ShelfRepository;
import uz.dynamic.techinventory.service.ShelfService;
import uz.dynamic.techinventory.service.dto.ShelfDTO;
import uz.dynamic.techinventory.web.rest.errors.BadRequestAlertException;
import uz.dynamic.techinventory.web.rest.utils.HeaderUtil;
import uz.dynamic.techinventory.web.rest.utils.PaginationUtil;
import uz.dynamic.techinventory.web.rest.utils.ResponseUtil;
import org.springdoc.api.annotations.ParameterObject;

/**
 * REST controller for managing {@link uz.dynamic.techinventory.domain.Shelf}.
 */
@RestController
@RequestMapping("/api/shelves")
public class ShelfResource {

    private final Logger log = LoggerFactory.getLogger(ShelfResource.class);

    private static final String ENTITY_NAME = "shelf";

    @Value("${spring.application.name}")
    private String applicationName;

    private final ShelfService shelfService;

    private final ShelfRepository shelfRepository;

    public ShelfResource(ShelfService shelfService, ShelfRepository shelfRepository) {
        this.shelfService = shelfService;
        this.shelfRepository = shelfRepository;
    }

    /**
     * {@code POST  /shelves} : Create a new shelf.
     *
     * @param shelfDTO the shelfDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shelfDTO, or with status {@code 400 (Bad Request)} if the shelf has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ShelfDTO> createShelf(@Valid @RequestBody ShelfDTO shelfDTO) throws URISyntaxException {
        log.debug("REST request to save Shelf : {}", shelfDTO);
        if (shelfDTO.getId() != null) {
            throw new BadRequestAlertException("A new shelf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShelfDTO result = shelfService.save(shelfDTO);
        return ResponseEntity
            .created(new URI("/api/shelves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shelves/:id} : Updates an existing shelf.
     *
     * @param id the id of the shelfDTO to save.
     * @param shelfDTO the shelfDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shelfDTO,
     * or with status {@code 400 (Bad Request)} if the shelfDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shelfDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ShelfDTO> updateShelf(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ShelfDTO shelfDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Shelf : {}, {}", id, shelfDTO);
        if (shelfDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shelfDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shelfRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ShelfDTO result = shelfService.update(shelfDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, shelfDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /shelves/:id} : Partial updates given fields of an existing shelf, field will ignore if it is null
     *
     * @param id the id of the shelfDTO to save.
     * @param shelfDTO the shelfDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shelfDTO,
     * or with status {@code 400 (Bad Request)} if the shelfDTO is not valid,
     * or with status {@code 404 (Not Found)} if the shelfDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the shelfDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ShelfDTO> partialUpdateShelf(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ShelfDTO shelfDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Shelf partially : {}, {}", id, shelfDTO);
        if (shelfDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shelfDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shelfRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ShelfDTO> result = shelfService.partialUpdate(shelfDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, shelfDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /shelves} : get all the shelves.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shelves in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ShelfDTO>> getAllShelves(@ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Shelves");
        Page<ShelfDTO> page = shelfService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /shelves/:id} : get the "id" shelf.
     *
     * @param id the id of the shelfDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shelfDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ShelfDTO> getShelf(@PathVariable("id") Long id) {
        log.debug("REST request to get Shelf : {}", id);
        Optional<ShelfDTO> shelfDTO = shelfService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shelfDTO);
    }

    /**
     * {@code DELETE  /shelves/:id} : delete the "id" shelf.
     *
     * @param id the id of the shelfDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShelf(@PathVariable("id") Long id) {
        log.debug("REST request to delete Shelf : {}", id);
        shelfService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
